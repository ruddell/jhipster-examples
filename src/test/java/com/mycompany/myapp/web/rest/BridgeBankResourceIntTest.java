package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FinaltestApp;

import com.mycompany.myapp.domain.BridgeBank;
import com.mycompany.myapp.repository.BridgeBankRepository;
import com.mycompany.myapp.service.BridgeBankService;
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
 * Test class for the BridgeBankResource REST controller.
 *
 * @see BridgeBankResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinaltestApp.class)
public class BridgeBankResourceIntTest {

    private static final Long DEFAULT_ID_BRIDGE = 1L;
    private static final Long UPDATED_ID_BRIDGE = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    @Autowired
    private BridgeBankRepository bridgeBankRepository;

    

    @Autowired
    private BridgeBankService bridgeBankService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBridgeBankMockMvc;

    private BridgeBank bridgeBank;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BridgeBankResource bridgeBankResource = new BridgeBankResource(bridgeBankService);
        this.restBridgeBankMockMvc = MockMvcBuilders.standaloneSetup(bridgeBankResource)
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
    public static BridgeBank createEntity(EntityManager em) {
        BridgeBank bridgeBank = new BridgeBank()
            .idBridge(DEFAULT_ID_BRIDGE)
            .name(DEFAULT_NAME)
            .countryCode(DEFAULT_COUNTRY_CODE);
        return bridgeBank;
    }

    @Before
    public void initTest() {
        bridgeBank = createEntity(em);
    }

    @Test
    @Transactional
    public void createBridgeBank() throws Exception {
        int databaseSizeBeforeCreate = bridgeBankRepository.findAll().size();

        // Create the BridgeBank
        restBridgeBankMockMvc.perform(post("/api/bridge-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeBank)))
            .andExpect(status().isCreated());

        // Validate the BridgeBank in the database
        List<BridgeBank> bridgeBankList = bridgeBankRepository.findAll();
        assertThat(bridgeBankList).hasSize(databaseSizeBeforeCreate + 1);
        BridgeBank testBridgeBank = bridgeBankList.get(bridgeBankList.size() - 1);
        assertThat(testBridgeBank.getIdBridge()).isEqualTo(DEFAULT_ID_BRIDGE);
        assertThat(testBridgeBank.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBridgeBank.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
    }

    @Test
    @Transactional
    public void createBridgeBankWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bridgeBankRepository.findAll().size();

        // Create the BridgeBank with an existing ID
        bridgeBank.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBridgeBankMockMvc.perform(post("/api/bridge-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeBank)))
            .andExpect(status().isBadRequest());

        // Validate the BridgeBank in the database
        List<BridgeBank> bridgeBankList = bridgeBankRepository.findAll();
        assertThat(bridgeBankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBridgeBanks() throws Exception {
        // Initialize the database
        bridgeBankRepository.saveAndFlush(bridgeBank);

        // Get all the bridgeBankList
        restBridgeBankMockMvc.perform(get("/api/bridge-banks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bridgeBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].idBridge").value(hasItem(DEFAULT_ID_BRIDGE.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())));
    }
    

    @Test
    @Transactional
    public void getBridgeBank() throws Exception {
        // Initialize the database
        bridgeBankRepository.saveAndFlush(bridgeBank);

        // Get the bridgeBank
        restBridgeBankMockMvc.perform(get("/api/bridge-banks/{id}", bridgeBank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bridgeBank.getId().intValue()))
            .andExpect(jsonPath("$.idBridge").value(DEFAULT_ID_BRIDGE.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBridgeBank() throws Exception {
        // Get the bridgeBank
        restBridgeBankMockMvc.perform(get("/api/bridge-banks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBridgeBank() throws Exception {
        // Initialize the database
        bridgeBankService.save(bridgeBank);

        int databaseSizeBeforeUpdate = bridgeBankRepository.findAll().size();

        // Update the bridgeBank
        BridgeBank updatedBridgeBank = bridgeBankRepository.findById(bridgeBank.getId()).get();
        // Disconnect from session so that the updates on updatedBridgeBank are not directly saved in db
        em.detach(updatedBridgeBank);
        updatedBridgeBank
            .idBridge(UPDATED_ID_BRIDGE)
            .name(UPDATED_NAME)
            .countryCode(UPDATED_COUNTRY_CODE);

        restBridgeBankMockMvc.perform(put("/api/bridge-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBridgeBank)))
            .andExpect(status().isOk());

        // Validate the BridgeBank in the database
        List<BridgeBank> bridgeBankList = bridgeBankRepository.findAll();
        assertThat(bridgeBankList).hasSize(databaseSizeBeforeUpdate);
        BridgeBank testBridgeBank = bridgeBankList.get(bridgeBankList.size() - 1);
        assertThat(testBridgeBank.getIdBridge()).isEqualTo(UPDATED_ID_BRIDGE);
        assertThat(testBridgeBank.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBridgeBank.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingBridgeBank() throws Exception {
        int databaseSizeBeforeUpdate = bridgeBankRepository.findAll().size();

        // Create the BridgeBank

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBridgeBankMockMvc.perform(put("/api/bridge-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeBank)))
            .andExpect(status().isBadRequest());

        // Validate the BridgeBank in the database
        List<BridgeBank> bridgeBankList = bridgeBankRepository.findAll();
        assertThat(bridgeBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBridgeBank() throws Exception {
        // Initialize the database
        bridgeBankService.save(bridgeBank);

        int databaseSizeBeforeDelete = bridgeBankRepository.findAll().size();

        // Get the bridgeBank
        restBridgeBankMockMvc.perform(delete("/api/bridge-banks/{id}", bridgeBank.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BridgeBank> bridgeBankList = bridgeBankRepository.findAll();
        assertThat(bridgeBankList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeBank.class);
        BridgeBank bridgeBank1 = new BridgeBank();
        bridgeBank1.setId(1L);
        BridgeBank bridgeBank2 = new BridgeBank();
        bridgeBank2.setId(bridgeBank1.getId());
        assertThat(bridgeBank1).isEqualTo(bridgeBank2);
        bridgeBank2.setId(2L);
        assertThat(bridgeBank1).isNotEqualTo(bridgeBank2);
        bridgeBank1.setId(null);
        assertThat(bridgeBank1).isNotEqualTo(bridgeBank2);
    }
}
