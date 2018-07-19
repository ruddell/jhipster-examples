package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FinaltestApp;

import com.mycompany.myapp.domain.BridgeAccountOnlineBank;
import com.mycompany.myapp.repository.BridgeAccountOnlineBankRepository;
import com.mycompany.myapp.service.BridgeAccountOnlineBankService;
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
 * Test class for the BridgeAccountOnlineBankResource REST controller.
 *
 * @see BridgeAccountOnlineBankResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinaltestApp.class)
public class BridgeAccountOnlineBankResourceIntTest {

    private static final Long DEFAULT_ID_BRIDGE = 1L;
    private static final Long UPDATED_ID_BRIDGE = 2L;

    private static final Long DEFAULT_STATUS = 1L;
    private static final Long UPDATED_STATUS = 2L;

    @Autowired
    private BridgeAccountOnlineBankRepository bridgeAccountOnlineBankRepository;

    

    @Autowired
    private BridgeAccountOnlineBankService bridgeAccountOnlineBankService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBridgeAccountOnlineBankMockMvc;

    private BridgeAccountOnlineBank bridgeAccountOnlineBank;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BridgeAccountOnlineBankResource bridgeAccountOnlineBankResource = new BridgeAccountOnlineBankResource(bridgeAccountOnlineBankService);
        this.restBridgeAccountOnlineBankMockMvc = MockMvcBuilders.standaloneSetup(bridgeAccountOnlineBankResource)
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
    public static BridgeAccountOnlineBank createEntity(EntityManager em) {
        BridgeAccountOnlineBank bridgeAccountOnlineBank = new BridgeAccountOnlineBank()
            .idBridge(DEFAULT_ID_BRIDGE)
            .status(DEFAULT_STATUS);
        return bridgeAccountOnlineBank;
    }

    @Before
    public void initTest() {
        bridgeAccountOnlineBank = createEntity(em);
    }

    @Test
    @Transactional
    public void createBridgeAccountOnlineBank() throws Exception {
        int databaseSizeBeforeCreate = bridgeAccountOnlineBankRepository.findAll().size();

        // Create the BridgeAccountOnlineBank
        restBridgeAccountOnlineBankMockMvc.perform(post("/api/bridge-account-online-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeAccountOnlineBank)))
            .andExpect(status().isCreated());

        // Validate the BridgeAccountOnlineBank in the database
        List<BridgeAccountOnlineBank> bridgeAccountOnlineBankList = bridgeAccountOnlineBankRepository.findAll();
        assertThat(bridgeAccountOnlineBankList).hasSize(databaseSizeBeforeCreate + 1);
        BridgeAccountOnlineBank testBridgeAccountOnlineBank = bridgeAccountOnlineBankList.get(bridgeAccountOnlineBankList.size() - 1);
        assertThat(testBridgeAccountOnlineBank.getIdBridge()).isEqualTo(DEFAULT_ID_BRIDGE);
        assertThat(testBridgeAccountOnlineBank.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createBridgeAccountOnlineBankWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bridgeAccountOnlineBankRepository.findAll().size();

        // Create the BridgeAccountOnlineBank with an existing ID
        bridgeAccountOnlineBank.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBridgeAccountOnlineBankMockMvc.perform(post("/api/bridge-account-online-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeAccountOnlineBank)))
            .andExpect(status().isBadRequest());

        // Validate the BridgeAccountOnlineBank in the database
        List<BridgeAccountOnlineBank> bridgeAccountOnlineBankList = bridgeAccountOnlineBankRepository.findAll();
        assertThat(bridgeAccountOnlineBankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBridgeAccountOnlineBanks() throws Exception {
        // Initialize the database
        bridgeAccountOnlineBankRepository.saveAndFlush(bridgeAccountOnlineBank);

        // Get all the bridgeAccountOnlineBankList
        restBridgeAccountOnlineBankMockMvc.perform(get("/api/bridge-account-online-banks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bridgeAccountOnlineBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].idBridge").value(hasItem(DEFAULT_ID_BRIDGE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.intValue())));
    }
    

    @Test
    @Transactional
    public void getBridgeAccountOnlineBank() throws Exception {
        // Initialize the database
        bridgeAccountOnlineBankRepository.saveAndFlush(bridgeAccountOnlineBank);

        // Get the bridgeAccountOnlineBank
        restBridgeAccountOnlineBankMockMvc.perform(get("/api/bridge-account-online-banks/{id}", bridgeAccountOnlineBank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bridgeAccountOnlineBank.getId().intValue()))
            .andExpect(jsonPath("$.idBridge").value(DEFAULT_ID_BRIDGE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingBridgeAccountOnlineBank() throws Exception {
        // Get the bridgeAccountOnlineBank
        restBridgeAccountOnlineBankMockMvc.perform(get("/api/bridge-account-online-banks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBridgeAccountOnlineBank() throws Exception {
        // Initialize the database
        bridgeAccountOnlineBankService.save(bridgeAccountOnlineBank);

        int databaseSizeBeforeUpdate = bridgeAccountOnlineBankRepository.findAll().size();

        // Update the bridgeAccountOnlineBank
        BridgeAccountOnlineBank updatedBridgeAccountOnlineBank = bridgeAccountOnlineBankRepository.findById(bridgeAccountOnlineBank.getId()).get();
        // Disconnect from session so that the updates on updatedBridgeAccountOnlineBank are not directly saved in db
        em.detach(updatedBridgeAccountOnlineBank);
        updatedBridgeAccountOnlineBank
            .idBridge(UPDATED_ID_BRIDGE)
            .status(UPDATED_STATUS);

        restBridgeAccountOnlineBankMockMvc.perform(put("/api/bridge-account-online-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBridgeAccountOnlineBank)))
            .andExpect(status().isOk());

        // Validate the BridgeAccountOnlineBank in the database
        List<BridgeAccountOnlineBank> bridgeAccountOnlineBankList = bridgeAccountOnlineBankRepository.findAll();
        assertThat(bridgeAccountOnlineBankList).hasSize(databaseSizeBeforeUpdate);
        BridgeAccountOnlineBank testBridgeAccountOnlineBank = bridgeAccountOnlineBankList.get(bridgeAccountOnlineBankList.size() - 1);
        assertThat(testBridgeAccountOnlineBank.getIdBridge()).isEqualTo(UPDATED_ID_BRIDGE);
        assertThat(testBridgeAccountOnlineBank.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBridgeAccountOnlineBank() throws Exception {
        int databaseSizeBeforeUpdate = bridgeAccountOnlineBankRepository.findAll().size();

        // Create the BridgeAccountOnlineBank

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBridgeAccountOnlineBankMockMvc.perform(put("/api/bridge-account-online-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeAccountOnlineBank)))
            .andExpect(status().isBadRequest());

        // Validate the BridgeAccountOnlineBank in the database
        List<BridgeAccountOnlineBank> bridgeAccountOnlineBankList = bridgeAccountOnlineBankRepository.findAll();
        assertThat(bridgeAccountOnlineBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBridgeAccountOnlineBank() throws Exception {
        // Initialize the database
        bridgeAccountOnlineBankService.save(bridgeAccountOnlineBank);

        int databaseSizeBeforeDelete = bridgeAccountOnlineBankRepository.findAll().size();

        // Get the bridgeAccountOnlineBank
        restBridgeAccountOnlineBankMockMvc.perform(delete("/api/bridge-account-online-banks/{id}", bridgeAccountOnlineBank.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BridgeAccountOnlineBank> bridgeAccountOnlineBankList = bridgeAccountOnlineBankRepository.findAll();
        assertThat(bridgeAccountOnlineBankList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeAccountOnlineBank.class);
        BridgeAccountOnlineBank bridgeAccountOnlineBank1 = new BridgeAccountOnlineBank();
        bridgeAccountOnlineBank1.setId(1L);
        BridgeAccountOnlineBank bridgeAccountOnlineBank2 = new BridgeAccountOnlineBank();
        bridgeAccountOnlineBank2.setId(bridgeAccountOnlineBank1.getId());
        assertThat(bridgeAccountOnlineBank1).isEqualTo(bridgeAccountOnlineBank2);
        bridgeAccountOnlineBank2.setId(2L);
        assertThat(bridgeAccountOnlineBank1).isNotEqualTo(bridgeAccountOnlineBank2);
        bridgeAccountOnlineBank1.setId(null);
        assertThat(bridgeAccountOnlineBank1).isNotEqualTo(bridgeAccountOnlineBank2);
    }
}
