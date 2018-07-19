package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FinaltestApp;

import com.mycompany.myapp.domain.BridgeUser;
import com.mycompany.myapp.repository.BridgeUserRepository;
import com.mycompany.myapp.service.BridgeUserService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BridgeUserResource REST controller.
 *
 * @see BridgeUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinaltestApp.class)
public class BridgeUserResourceIntTest {

    private static final String DEFAULT_UUID_BRIDGE = "AAAAAAAAAA";
    private static final String UPDATED_UUID_BRIDGE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private BridgeUserRepository bridgeUserRepository;

    

    @Autowired
    private BridgeUserService bridgeUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBridgeUserMockMvc;

    private BridgeUser bridgeUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BridgeUserResource bridgeUserResource = new BridgeUserResource(bridgeUserService);
        this.restBridgeUserMockMvc = MockMvcBuilders.standaloneSetup(bridgeUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BridgeUser createEntity(EntityManager em) {
        BridgeUser bridgeUser = new BridgeUser()
            .uuidBridge(DEFAULT_UUID_BRIDGE)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD);
        return bridgeUser;
    }

    @Before
    public void initTest() {
        bridgeUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createBridgeUser() throws Exception {
        int databaseSizeBeforeCreate = bridgeUserRepository.findAll().size();

        // Create the BridgeUser
        restBridgeUserMockMvc.perform(post("/api/bridge-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeUser)))
            .andExpect(status().isCreated());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeCreate + 1);
        BridgeUser testBridgeUser = bridgeUserList.get(bridgeUserList.size() - 1);
        assertThat(testBridgeUser.getUuidBridge()).isEqualTo(DEFAULT_UUID_BRIDGE);
        assertThat(testBridgeUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBridgeUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createBridgeUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bridgeUserRepository.findAll().size();

        // Create the BridgeUser with an existing ID
        bridgeUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBridgeUserMockMvc.perform(post("/api/bridge-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeUser)))
            .andExpect(status().isBadRequest());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBridgeUsers() throws Exception {
        // Initialize the database
        bridgeUserRepository.saveAndFlush(bridgeUser);

        // Get all the bridgeUserList
        restBridgeUserMockMvc.perform(get("/api/bridge-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bridgeUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuidBridge").value(hasItem(DEFAULT_UUID_BRIDGE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }
    

    @Test
    @Transactional
    public void getBridgeUser() throws Exception {
        // Initialize the database
        bridgeUserRepository.saveAndFlush(bridgeUser);

        // Get the bridgeUser
        restBridgeUserMockMvc.perform(get("/api/bridge-users/{id}", bridgeUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bridgeUser.getId().intValue()))
            .andExpect(jsonPath("$.uuidBridge").value(DEFAULT_UUID_BRIDGE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBridgeUser() throws Exception {
        // Get the bridgeUser
        restBridgeUserMockMvc.perform(get("/api/bridge-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBridgeUser() throws Exception {
        // Initialize the database
        bridgeUserService.save(bridgeUser);

        int databaseSizeBeforeUpdate = bridgeUserRepository.findAll().size();

        // Update the bridgeUser
        BridgeUser updatedBridgeUser = bridgeUserRepository.findById(bridgeUser.getId()).get();
        // Disconnect from session so that the updates on updatedBridgeUser are not directly saved in db
        em.detach(updatedBridgeUser);
        updatedBridgeUser
            .uuidBridge(UPDATED_UUID_BRIDGE)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD);

        restBridgeUserMockMvc.perform(put("/api/bridge-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBridgeUser)))
            .andExpect(status().isOk());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeUpdate);
        BridgeUser testBridgeUser = bridgeUserList.get(bridgeUserList.size() - 1);
        assertThat(testBridgeUser.getUuidBridge()).isEqualTo(UPDATED_UUID_BRIDGE);
        assertThat(testBridgeUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBridgeUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingBridgeUser() throws Exception {
        int databaseSizeBeforeUpdate = bridgeUserRepository.findAll().size();

        // Create the BridgeUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBridgeUserMockMvc.perform(put("/api/bridge-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeUser)))
            .andExpect(status().isBadRequest());

        // Validate the BridgeUser in the database
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBridgeUser() throws Exception {
        // Initialize the database
        bridgeUserService.save(bridgeUser);

        int databaseSizeBeforeDelete = bridgeUserRepository.findAll().size();

        // Get the bridgeUser
        restBridgeUserMockMvc.perform(delete("/api/bridge-users/{id}", bridgeUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BridgeUser> bridgeUserList = bridgeUserRepository.findAll();
        assertThat(bridgeUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeUser.class);
        BridgeUser bridgeUser1 = new BridgeUser();
        bridgeUser1.setId(1L);
        BridgeUser bridgeUser2 = new BridgeUser();
        bridgeUser2.setId(bridgeUser1.getId());
        assertThat(bridgeUser1).isEqualTo(bridgeUser2);
        bridgeUser2.setId(2L);
        assertThat(bridgeUser1).isNotEqualTo(bridgeUser2);
        bridgeUser1.setId(null);
        assertThat(bridgeUser1).isNotEqualTo(bridgeUser2);
    }
}
