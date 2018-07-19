package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FinaltestApp;

import com.mycompany.myapp.domain.AccountBankStat;
import com.mycompany.myapp.repository.AccountBankStatRepository;
import com.mycompany.myapp.service.AccountBankStatService;
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
 * Test class for the AccountBankStatResource REST controller.
 *
 * @see AccountBankStatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinaltestApp.class)
public class AccountBankStatResourceIntTest {

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;

    private static final ZonedDateTime DEFAULT_LAST_REFRESH = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_REFRESH = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AccountBankStatRepository accountBankStatRepository;

    

    @Autowired
    private AccountBankStatService accountBankStatService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAccountBankStatMockMvc;

    private AccountBankStat accountBankStat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountBankStatResource accountBankStatResource = new AccountBankStatResource(accountBankStatService);
        this.restAccountBankStatMockMvc = MockMvcBuilders.standaloneSetup(accountBankStatResource)
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
    public static AccountBankStat createEntity(EntityManager em) {
        AccountBankStat accountBankStat = new AccountBankStat()
            .balance(DEFAULT_BALANCE)
            .lastRefresh(DEFAULT_LAST_REFRESH);
        return accountBankStat;
    }

    @Before
    public void initTest() {
        accountBankStat = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountBankStat() throws Exception {
        int databaseSizeBeforeCreate = accountBankStatRepository.findAll().size();

        // Create the AccountBankStat
        restAccountBankStatMockMvc.perform(post("/api/account-bank-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountBankStat)))
            .andExpect(status().isCreated());

        // Validate the AccountBankStat in the database
        List<AccountBankStat> accountBankStatList = accountBankStatRepository.findAll();
        assertThat(accountBankStatList).hasSize(databaseSizeBeforeCreate + 1);
        AccountBankStat testAccountBankStat = accountBankStatList.get(accountBankStatList.size() - 1);
        assertThat(testAccountBankStat.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testAccountBankStat.getLastRefresh()).isEqualTo(DEFAULT_LAST_REFRESH);
    }

    @Test
    @Transactional
    public void createAccountBankStatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountBankStatRepository.findAll().size();

        // Create the AccountBankStat with an existing ID
        accountBankStat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountBankStatMockMvc.perform(post("/api/account-bank-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountBankStat)))
            .andExpect(status().isBadRequest());

        // Validate the AccountBankStat in the database
        List<AccountBankStat> accountBankStatList = accountBankStatRepository.findAll();
        assertThat(accountBankStatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAccountBankStats() throws Exception {
        // Initialize the database
        accountBankStatRepository.saveAndFlush(accountBankStat);

        // Get all the accountBankStatList
        restAccountBankStatMockMvc.perform(get("/api/account-bank-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountBankStat.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastRefresh").value(hasItem(sameInstant(DEFAULT_LAST_REFRESH))));
    }
    

    @Test
    @Transactional
    public void getAccountBankStat() throws Exception {
        // Initialize the database
        accountBankStatRepository.saveAndFlush(accountBankStat);

        // Get the accountBankStat
        restAccountBankStatMockMvc.perform(get("/api/account-bank-stats/{id}", accountBankStat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountBankStat.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.lastRefresh").value(sameInstant(DEFAULT_LAST_REFRESH)));
    }
    @Test
    @Transactional
    public void getNonExistingAccountBankStat() throws Exception {
        // Get the accountBankStat
        restAccountBankStatMockMvc.perform(get("/api/account-bank-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountBankStat() throws Exception {
        // Initialize the database
        accountBankStatService.save(accountBankStat);

        int databaseSizeBeforeUpdate = accountBankStatRepository.findAll().size();

        // Update the accountBankStat
        AccountBankStat updatedAccountBankStat = accountBankStatRepository.findById(accountBankStat.getId()).get();
        // Disconnect from session so that the updates on updatedAccountBankStat are not directly saved in db
        em.detach(updatedAccountBankStat);
        updatedAccountBankStat
            .balance(UPDATED_BALANCE)
            .lastRefresh(UPDATED_LAST_REFRESH);

        restAccountBankStatMockMvc.perform(put("/api/account-bank-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccountBankStat)))
            .andExpect(status().isOk());

        // Validate the AccountBankStat in the database
        List<AccountBankStat> accountBankStatList = accountBankStatRepository.findAll();
        assertThat(accountBankStatList).hasSize(databaseSizeBeforeUpdate);
        AccountBankStat testAccountBankStat = accountBankStatList.get(accountBankStatList.size() - 1);
        assertThat(testAccountBankStat.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testAccountBankStat.getLastRefresh()).isEqualTo(UPDATED_LAST_REFRESH);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountBankStat() throws Exception {
        int databaseSizeBeforeUpdate = accountBankStatRepository.findAll().size();

        // Create the AccountBankStat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAccountBankStatMockMvc.perform(put("/api/account-bank-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountBankStat)))
            .andExpect(status().isBadRequest());

        // Validate the AccountBankStat in the database
        List<AccountBankStat> accountBankStatList = accountBankStatRepository.findAll();
        assertThat(accountBankStatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountBankStat() throws Exception {
        // Initialize the database
        accountBankStatService.save(accountBankStat);

        int databaseSizeBeforeDelete = accountBankStatRepository.findAll().size();

        // Get the accountBankStat
        restAccountBankStatMockMvc.perform(delete("/api/account-bank-stats/{id}", accountBankStat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AccountBankStat> accountBankStatList = accountBankStatRepository.findAll();
        assertThat(accountBankStatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountBankStat.class);
        AccountBankStat accountBankStat1 = new AccountBankStat();
        accountBankStat1.setId(1L);
        AccountBankStat accountBankStat2 = new AccountBankStat();
        accountBankStat2.setId(accountBankStat1.getId());
        assertThat(accountBankStat1).isEqualTo(accountBankStat2);
        accountBankStat2.setId(2L);
        assertThat(accountBankStat1).isNotEqualTo(accountBankStat2);
        accountBankStat1.setId(null);
        assertThat(accountBankStat1).isNotEqualTo(accountBankStat2);
    }
}
