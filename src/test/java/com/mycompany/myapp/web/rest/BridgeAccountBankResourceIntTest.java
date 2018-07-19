package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FinaltestApp;

import com.mycompany.myapp.domain.BridgeAccountBank;
import com.mycompany.myapp.repository.BridgeAccountBankRepository;
import com.mycompany.myapp.service.BridgeAccountBankService;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BridgeAccountBankResource REST controller.
 *
 * @see BridgeAccountBankResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinaltestApp.class)
public class BridgeAccountBankResourceIntTest {

    private static final Long DEFAULT_ID_BRIDGE = 1L;
    private static final Long UPDATED_ID_BRIDGE = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_STATUS = 1L;
    private static final Long UPDATED_ITEM_STATUS = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BridgeAccountBankRepository bridgeAccountBankRepository;

    

    @Autowired
    private BridgeAccountBankService bridgeAccountBankService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBridgeAccountBankMockMvc;

    private BridgeAccountBank bridgeAccountBank;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BridgeAccountBankResource bridgeAccountBankResource = new BridgeAccountBankResource(bridgeAccountBankService);
        this.restBridgeAccountBankMockMvc = MockMvcBuilders.standaloneSetup(bridgeAccountBankResource)
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
    public static BridgeAccountBank createEntity(EntityManager em) {
        BridgeAccountBank bridgeAccountBank = new BridgeAccountBank()
            .idBridge(DEFAULT_ID_BRIDGE)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .itemStatus(DEFAULT_ITEM_STATUS)
            .updatedAt(DEFAULT_UPDATED_AT);
        return bridgeAccountBank;
    }

    @Before
    public void initTest() {
        bridgeAccountBank = createEntity(em);
    }

    @Test
    @Transactional
    public void createBridgeAccountBank() throws Exception {
        int databaseSizeBeforeCreate = bridgeAccountBankRepository.findAll().size();

        // Create the BridgeAccountBank
        restBridgeAccountBankMockMvc.perform(post("/api/bridge-account-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeAccountBank)))
            .andExpect(status().isCreated());

        // Validate the BridgeAccountBank in the database
        List<BridgeAccountBank> bridgeAccountBankList = bridgeAccountBankRepository.findAll();
        assertThat(bridgeAccountBankList).hasSize(databaseSizeBeforeCreate + 1);
        BridgeAccountBank testBridgeAccountBank = bridgeAccountBankList.get(bridgeAccountBankList.size() - 1);
        assertThat(testBridgeAccountBank.getIdBridge()).isEqualTo(DEFAULT_ID_BRIDGE);
        assertThat(testBridgeAccountBank.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBridgeAccountBank.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBridgeAccountBank.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testBridgeAccountBank.getItemStatus()).isEqualTo(DEFAULT_ITEM_STATUS);
        assertThat(testBridgeAccountBank.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createBridgeAccountBankWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bridgeAccountBankRepository.findAll().size();

        // Create the BridgeAccountBank with an existing ID
        bridgeAccountBank.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBridgeAccountBankMockMvc.perform(post("/api/bridge-account-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeAccountBank)))
            .andExpect(status().isBadRequest());

        // Validate the BridgeAccountBank in the database
        List<BridgeAccountBank> bridgeAccountBankList = bridgeAccountBankRepository.findAll();
        assertThat(bridgeAccountBankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBridgeAccountBanks() throws Exception {
        // Initialize the database
        bridgeAccountBankRepository.saveAndFlush(bridgeAccountBank);

        // Get all the bridgeAccountBankList
        restBridgeAccountBankMockMvc.perform(get("/api/bridge-account-banks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bridgeAccountBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].idBridge").value(hasItem(DEFAULT_ID_BRIDGE.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].itemStatus").value(hasItem(DEFAULT_ITEM_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }
    

    @Test
    @Transactional
    public void getBridgeAccountBank() throws Exception {
        // Initialize the database
        bridgeAccountBankRepository.saveAndFlush(bridgeAccountBank);

        // Get the bridgeAccountBank
        restBridgeAccountBankMockMvc.perform(get("/api/bridge-account-banks/{id}", bridgeAccountBank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bridgeAccountBank.getId().intValue()))
            .andExpect(jsonPath("$.idBridge").value(DEFAULT_ID_BRIDGE.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.itemStatus").value(DEFAULT_ITEM_STATUS.intValue()))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }
    @Test
    @Transactional
    public void getNonExistingBridgeAccountBank() throws Exception {
        // Get the bridgeAccountBank
        restBridgeAccountBankMockMvc.perform(get("/api/bridge-account-banks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBridgeAccountBank() throws Exception {
        // Initialize the database
        bridgeAccountBankService.save(bridgeAccountBank);

        int databaseSizeBeforeUpdate = bridgeAccountBankRepository.findAll().size();

        // Update the bridgeAccountBank
        BridgeAccountBank updatedBridgeAccountBank = bridgeAccountBankRepository.findById(bridgeAccountBank.getId()).get();
        // Disconnect from session so that the updates on updatedBridgeAccountBank are not directly saved in db
        em.detach(updatedBridgeAccountBank);
        updatedBridgeAccountBank
            .idBridge(UPDATED_ID_BRIDGE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .itemStatus(UPDATED_ITEM_STATUS)
            .updatedAt(UPDATED_UPDATED_AT);

        restBridgeAccountBankMockMvc.perform(put("/api/bridge-account-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBridgeAccountBank)))
            .andExpect(status().isOk());

        // Validate the BridgeAccountBank in the database
        List<BridgeAccountBank> bridgeAccountBankList = bridgeAccountBankRepository.findAll();
        assertThat(bridgeAccountBankList).hasSize(databaseSizeBeforeUpdate);
        BridgeAccountBank testBridgeAccountBank = bridgeAccountBankList.get(bridgeAccountBankList.size() - 1);
        assertThat(testBridgeAccountBank.getIdBridge()).isEqualTo(UPDATED_ID_BRIDGE);
        assertThat(testBridgeAccountBank.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBridgeAccountBank.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBridgeAccountBank.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testBridgeAccountBank.getItemStatus()).isEqualTo(UPDATED_ITEM_STATUS);
        assertThat(testBridgeAccountBank.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingBridgeAccountBank() throws Exception {
        int databaseSizeBeforeUpdate = bridgeAccountBankRepository.findAll().size();

        // Create the BridgeAccountBank

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBridgeAccountBankMockMvc.perform(put("/api/bridge-account-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeAccountBank)))
            .andExpect(status().isBadRequest());

        // Validate the BridgeAccountBank in the database
        List<BridgeAccountBank> bridgeAccountBankList = bridgeAccountBankRepository.findAll();
        assertThat(bridgeAccountBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBridgeAccountBank() throws Exception {
        // Initialize the database
        bridgeAccountBankService.save(bridgeAccountBank);

        int databaseSizeBeforeDelete = bridgeAccountBankRepository.findAll().size();

        // Get the bridgeAccountBank
        restBridgeAccountBankMockMvc.perform(delete("/api/bridge-account-banks/{id}", bridgeAccountBank.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BridgeAccountBank> bridgeAccountBankList = bridgeAccountBankRepository.findAll();
        assertThat(bridgeAccountBankList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeAccountBank.class);
        BridgeAccountBank bridgeAccountBank1 = new BridgeAccountBank();
        bridgeAccountBank1.setId(1L);
        BridgeAccountBank bridgeAccountBank2 = new BridgeAccountBank();
        bridgeAccountBank2.setId(bridgeAccountBank1.getId());
        assertThat(bridgeAccountBank1).isEqualTo(bridgeAccountBank2);
        bridgeAccountBank2.setId(2L);
        assertThat(bridgeAccountBank1).isNotEqualTo(bridgeAccountBank2);
        bridgeAccountBank1.setId(null);
        assertThat(bridgeAccountBank1).isNotEqualTo(bridgeAccountBank2);
    }
}
