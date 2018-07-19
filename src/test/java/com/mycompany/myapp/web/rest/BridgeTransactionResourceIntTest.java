package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FinaltestApp;

import com.mycompany.myapp.domain.BridgeTransaction;
import com.mycompany.myapp.repository.BridgeTransactionRepository;
import com.mycompany.myapp.service.BridgeTransactionService;
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
import java.time.LocalDate;
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
 * Test class for the BridgeTransactionResource REST controller.
 *
 * @see BridgeTransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinaltestApp.class)
public class BridgeTransactionResourceIntTest {

    private static final Long DEFAULT_ID_BRIDGE = 1L;
    private static final Long UPDATED_ID_BRIDGE = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_RAW_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_RAW_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    @Autowired
    private BridgeTransactionRepository bridgeTransactionRepository;

    

    @Autowired
    private BridgeTransactionService bridgeTransactionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBridgeTransactionMockMvc;

    private BridgeTransaction bridgeTransaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BridgeTransactionResource bridgeTransactionResource = new BridgeTransactionResource(bridgeTransactionService);
        this.restBridgeTransactionMockMvc = MockMvcBuilders.standaloneSetup(bridgeTransactionResource)
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
    public static BridgeTransaction createEntity(EntityManager em) {
        BridgeTransaction bridgeTransaction = new BridgeTransaction()
            .idBridge(DEFAULT_ID_BRIDGE)
            .description(DEFAULT_DESCRIPTION)
            .rawDescription(DEFAULT_RAW_DESCRIPTION)
            .date(DEFAULT_DATE)
            .updatedAt(DEFAULT_UPDATED_AT)
            .isDeleted(DEFAULT_IS_DELETED);
        return bridgeTransaction;
    }

    @Before
    public void initTest() {
        bridgeTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createBridgeTransaction() throws Exception {
        int databaseSizeBeforeCreate = bridgeTransactionRepository.findAll().size();

        // Create the BridgeTransaction
        restBridgeTransactionMockMvc.perform(post("/api/bridge-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeTransaction)))
            .andExpect(status().isCreated());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        BridgeTransaction testBridgeTransaction = bridgeTransactionList.get(bridgeTransactionList.size() - 1);
        assertThat(testBridgeTransaction.getIdBridge()).isEqualTo(DEFAULT_ID_BRIDGE);
        assertThat(testBridgeTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBridgeTransaction.getRawDescription()).isEqualTo(DEFAULT_RAW_DESCRIPTION);
        assertThat(testBridgeTransaction.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBridgeTransaction.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testBridgeTransaction.isIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    public void createBridgeTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bridgeTransactionRepository.findAll().size();

        // Create the BridgeTransaction with an existing ID
        bridgeTransaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBridgeTransactionMockMvc.perform(post("/api/bridge-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBridgeTransactions() throws Exception {
        // Initialize the database
        bridgeTransactionRepository.saveAndFlush(bridgeTransaction);

        // Get all the bridgeTransactionList
        restBridgeTransactionMockMvc.perform(get("/api/bridge-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bridgeTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].idBridge").value(hasItem(DEFAULT_ID_BRIDGE.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].rawDescription").value(hasItem(DEFAULT_RAW_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getBridgeTransaction() throws Exception {
        // Initialize the database
        bridgeTransactionRepository.saveAndFlush(bridgeTransaction);

        // Get the bridgeTransaction
        restBridgeTransactionMockMvc.perform(get("/api/bridge-transactions/{id}", bridgeTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bridgeTransaction.getId().intValue()))
            .andExpect(jsonPath("$.idBridge").value(DEFAULT_ID_BRIDGE.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.rawDescription").value(DEFAULT_RAW_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingBridgeTransaction() throws Exception {
        // Get the bridgeTransaction
        restBridgeTransactionMockMvc.perform(get("/api/bridge-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBridgeTransaction() throws Exception {
        // Initialize the database
        bridgeTransactionService.save(bridgeTransaction);

        int databaseSizeBeforeUpdate = bridgeTransactionRepository.findAll().size();

        // Update the bridgeTransaction
        BridgeTransaction updatedBridgeTransaction = bridgeTransactionRepository.findById(bridgeTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedBridgeTransaction are not directly saved in db
        em.detach(updatedBridgeTransaction);
        updatedBridgeTransaction
            .idBridge(UPDATED_ID_BRIDGE)
            .description(UPDATED_DESCRIPTION)
            .rawDescription(UPDATED_RAW_DESCRIPTION)
            .date(UPDATED_DATE)
            .updatedAt(UPDATED_UPDATED_AT)
            .isDeleted(UPDATED_IS_DELETED);

        restBridgeTransactionMockMvc.perform(put("/api/bridge-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBridgeTransaction)))
            .andExpect(status().isOk());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeUpdate);
        BridgeTransaction testBridgeTransaction = bridgeTransactionList.get(bridgeTransactionList.size() - 1);
        assertThat(testBridgeTransaction.getIdBridge()).isEqualTo(UPDATED_ID_BRIDGE);
        assertThat(testBridgeTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBridgeTransaction.getRawDescription()).isEqualTo(UPDATED_RAW_DESCRIPTION);
        assertThat(testBridgeTransaction.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBridgeTransaction.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testBridgeTransaction.isIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingBridgeTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bridgeTransactionRepository.findAll().size();

        // Create the BridgeTransaction

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBridgeTransactionMockMvc.perform(put("/api/bridge-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the BridgeTransaction in the database
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBridgeTransaction() throws Exception {
        // Initialize the database
        bridgeTransactionService.save(bridgeTransaction);

        int databaseSizeBeforeDelete = bridgeTransactionRepository.findAll().size();

        // Get the bridgeTransaction
        restBridgeTransactionMockMvc.perform(delete("/api/bridge-transactions/{id}", bridgeTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BridgeTransaction> bridgeTransactionList = bridgeTransactionRepository.findAll();
        assertThat(bridgeTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeTransaction.class);
        BridgeTransaction bridgeTransaction1 = new BridgeTransaction();
        bridgeTransaction1.setId(1L);
        BridgeTransaction bridgeTransaction2 = new BridgeTransaction();
        bridgeTransaction2.setId(bridgeTransaction1.getId());
        assertThat(bridgeTransaction1).isEqualTo(bridgeTransaction2);
        bridgeTransaction2.setId(2L);
        assertThat(bridgeTransaction1).isNotEqualTo(bridgeTransaction2);
        bridgeTransaction1.setId(null);
        assertThat(bridgeTransaction1).isNotEqualTo(bridgeTransaction2);
    }
}
