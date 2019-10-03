package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;

import com.mycompany.myapp.domain.CertificateRequestBatch;
import com.mycompany.myapp.repository.CertificateRequestBatchRepository;
import com.mycompany.myapp.service.CertificateRequestBatchService;
import com.mycompany.myapp.service.dto.CertificateRequestBatchDTO;
import com.mycompany.myapp.service.mapper.CertificateRequestBatchMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.CertificateRequestBatchCriteria;
import com.mycompany.myapp.service.CertificateRequestBatchQueryService;

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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CertificateRequestBatchResource REST controller.
 *
 * @see CertificateRequestBatchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class CertificateRequestBatchResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CertificateRequestBatchRepository certificateRequestBatchRepository;

    @Autowired
    private CertificateRequestBatchMapper certificateRequestBatchMapper;

    @Autowired
    private CertificateRequestBatchService certificateRequestBatchService;

    @Autowired
    private CertificateRequestBatchQueryService certificateRequestBatchQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCertificateRequestBatchMockMvc;

    private CertificateRequestBatch certificateRequestBatch;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CertificateRequestBatchResource certificateRequestBatchResource = new CertificateRequestBatchResource(certificateRequestBatchService, certificateRequestBatchQueryService);
        this.restCertificateRequestBatchMockMvc = MockMvcBuilders.standaloneSetup(certificateRequestBatchResource)
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
    public static CertificateRequestBatch createEntity(EntityManager em) {
        CertificateRequestBatch certificateRequestBatch = new CertificateRequestBatch()
            .name(DEFAULT_NAME)
            .creationDate(DEFAULT_CREATION_DATE);
        return certificateRequestBatch;
    }

    @Before
    public void initTest() {
        certificateRequestBatch = createEntity(em);
    }

    @Test
    @Transactional
    public void createCertificateRequestBatch() throws Exception {
        int databaseSizeBeforeCreate = certificateRequestBatchRepository.findAll().size();

        // Create the CertificateRequestBatch
        CertificateRequestBatchDTO certificateRequestBatchDTO = certificateRequestBatchMapper.toDto(certificateRequestBatch);
        restCertificateRequestBatchMockMvc.perform(post("/api/certificate-request-batches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateRequestBatchDTO)))
            .andExpect(status().isCreated());

        // Validate the CertificateRequestBatch in the database
        List<CertificateRequestBatch> certificateRequestBatchList = certificateRequestBatchRepository.findAll();
        assertThat(certificateRequestBatchList).hasSize(databaseSizeBeforeCreate + 1);
        CertificateRequestBatch testCertificateRequestBatch = certificateRequestBatchList.get(certificateRequestBatchList.size() - 1);
        assertThat(testCertificateRequestBatch.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCertificateRequestBatch.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createCertificateRequestBatchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = certificateRequestBatchRepository.findAll().size();

        // Create the CertificateRequestBatch with an existing ID
        certificateRequestBatch.setId(1L);
        CertificateRequestBatchDTO certificateRequestBatchDTO = certificateRequestBatchMapper.toDto(certificateRequestBatch);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertificateRequestBatchMockMvc.perform(post("/api/certificate-request-batches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateRequestBatchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CertificateRequestBatch in the database
        List<CertificateRequestBatch> certificateRequestBatchList = certificateRequestBatchRepository.findAll();
        assertThat(certificateRequestBatchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCertificateRequestBatches() throws Exception {
        // Initialize the database
        certificateRequestBatchRepository.saveAndFlush(certificateRequestBatch);

        // Get all the certificateRequestBatchList
        restCertificateRequestBatchMockMvc.perform(get("/api/certificate-request-batches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificateRequestBatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }

    @Test
    @Transactional
    public void getCertificateRequestBatch() throws Exception {
        // Initialize the database
        certificateRequestBatchRepository.saveAndFlush(certificateRequestBatch);

        // Get the certificateRequestBatch
        restCertificateRequestBatchMockMvc.perform(get("/api/certificate-request-batches/{id}", certificateRequestBatch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(certificateRequestBatch.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllCertificateRequestBatchesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        certificateRequestBatchRepository.saveAndFlush(certificateRequestBatch);

        // Get all the certificateRequestBatchList where name equals to DEFAULT_NAME
        defaultCertificateRequestBatchShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the certificateRequestBatchList where name equals to UPDATED_NAME
        defaultCertificateRequestBatchShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCertificateRequestBatchesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        certificateRequestBatchRepository.saveAndFlush(certificateRequestBatch);

        // Get all the certificateRequestBatchList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCertificateRequestBatchShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the certificateRequestBatchList where name equals to UPDATED_NAME
        defaultCertificateRequestBatchShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCertificateRequestBatchesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        certificateRequestBatchRepository.saveAndFlush(certificateRequestBatch);

        // Get all the certificateRequestBatchList where name is not null
        defaultCertificateRequestBatchShouldBeFound("name.specified=true");

        // Get all the certificateRequestBatchList where name is null
        defaultCertificateRequestBatchShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCertificateRequestBatchesByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        certificateRequestBatchRepository.saveAndFlush(certificateRequestBatch);

        // Get all the certificateRequestBatchList where creationDate equals to DEFAULT_CREATION_DATE
        defaultCertificateRequestBatchShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the certificateRequestBatchList where creationDate equals to UPDATED_CREATION_DATE
        defaultCertificateRequestBatchShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCertificateRequestBatchesByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        certificateRequestBatchRepository.saveAndFlush(certificateRequestBatch);

        // Get all the certificateRequestBatchList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultCertificateRequestBatchShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the certificateRequestBatchList where creationDate equals to UPDATED_CREATION_DATE
        defaultCertificateRequestBatchShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCertificateRequestBatchesByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        certificateRequestBatchRepository.saveAndFlush(certificateRequestBatch);

        // Get all the certificateRequestBatchList where creationDate is not null
        defaultCertificateRequestBatchShouldBeFound("creationDate.specified=true");

        // Get all the certificateRequestBatchList where creationDate is null
        defaultCertificateRequestBatchShouldNotBeFound("creationDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCertificateRequestBatchShouldBeFound(String filter) throws Exception {
        restCertificateRequestBatchMockMvc.perform(get("/api/certificate-request-batches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificateRequestBatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCertificateRequestBatchShouldNotBeFound(String filter) throws Exception {
        restCertificateRequestBatchMockMvc.perform(get("/api/certificate-request-batches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCertificateRequestBatch() throws Exception {
        // Get the certificateRequestBatch
        restCertificateRequestBatchMockMvc.perform(get("/api/certificate-request-batches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCertificateRequestBatch() throws Exception {
        // Initialize the database
        certificateRequestBatchRepository.saveAndFlush(certificateRequestBatch);
        int databaseSizeBeforeUpdate = certificateRequestBatchRepository.findAll().size();

        // Update the certificateRequestBatch
        CertificateRequestBatch updatedCertificateRequestBatch = certificateRequestBatchRepository.findOne(certificateRequestBatch.getId());
        // Disconnect from session so that the updates on updatedCertificateRequestBatch are not directly saved in db
        em.detach(updatedCertificateRequestBatch);
        updatedCertificateRequestBatch
            .name(UPDATED_NAME)
            .creationDate(UPDATED_CREATION_DATE);
        CertificateRequestBatchDTO certificateRequestBatchDTO = certificateRequestBatchMapper.toDto(updatedCertificateRequestBatch);

        restCertificateRequestBatchMockMvc.perform(put("/api/certificate-request-batches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateRequestBatchDTO)))
            .andExpect(status().isOk());

        // Validate the CertificateRequestBatch in the database
        List<CertificateRequestBatch> certificateRequestBatchList = certificateRequestBatchRepository.findAll();
        assertThat(certificateRequestBatchList).hasSize(databaseSizeBeforeUpdate);
        CertificateRequestBatch testCertificateRequestBatch = certificateRequestBatchList.get(certificateRequestBatchList.size() - 1);
        assertThat(testCertificateRequestBatch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCertificateRequestBatch.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCertificateRequestBatch() throws Exception {
        int databaseSizeBeforeUpdate = certificateRequestBatchRepository.findAll().size();

        // Create the CertificateRequestBatch
        CertificateRequestBatchDTO certificateRequestBatchDTO = certificateRequestBatchMapper.toDto(certificateRequestBatch);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCertificateRequestBatchMockMvc.perform(put("/api/certificate-request-batches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateRequestBatchDTO)))
            .andExpect(status().isCreated());

        // Validate the CertificateRequestBatch in the database
        List<CertificateRequestBatch> certificateRequestBatchList = certificateRequestBatchRepository.findAll();
        assertThat(certificateRequestBatchList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCertificateRequestBatch() throws Exception {
        // Initialize the database
        certificateRequestBatchRepository.saveAndFlush(certificateRequestBatch);
        int databaseSizeBeforeDelete = certificateRequestBatchRepository.findAll().size();

        // Get the certificateRequestBatch
        restCertificateRequestBatchMockMvc.perform(delete("/api/certificate-request-batches/{id}", certificateRequestBatch.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CertificateRequestBatch> certificateRequestBatchList = certificateRequestBatchRepository.findAll();
        assertThat(certificateRequestBatchList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificateRequestBatch.class);
        CertificateRequestBatch certificateRequestBatch1 = new CertificateRequestBatch();
        certificateRequestBatch1.setId(1L);
        CertificateRequestBatch certificateRequestBatch2 = new CertificateRequestBatch();
        certificateRequestBatch2.setId(certificateRequestBatch1.getId());
        assertThat(certificateRequestBatch1).isEqualTo(certificateRequestBatch2);
        certificateRequestBatch2.setId(2L);
        assertThat(certificateRequestBatch1).isNotEqualTo(certificateRequestBatch2);
        certificateRequestBatch1.setId(null);
        assertThat(certificateRequestBatch1).isNotEqualTo(certificateRequestBatch2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificateRequestBatchDTO.class);
        CertificateRequestBatchDTO certificateRequestBatchDTO1 = new CertificateRequestBatchDTO();
        certificateRequestBatchDTO1.setId(1L);
        CertificateRequestBatchDTO certificateRequestBatchDTO2 = new CertificateRequestBatchDTO();
        assertThat(certificateRequestBatchDTO1).isNotEqualTo(certificateRequestBatchDTO2);
        certificateRequestBatchDTO2.setId(certificateRequestBatchDTO1.getId());
        assertThat(certificateRequestBatchDTO1).isEqualTo(certificateRequestBatchDTO2);
        certificateRequestBatchDTO2.setId(2L);
        assertThat(certificateRequestBatchDTO1).isNotEqualTo(certificateRequestBatchDTO2);
        certificateRequestBatchDTO1.setId(null);
        assertThat(certificateRequestBatchDTO1).isNotEqualTo(certificateRequestBatchDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(certificateRequestBatchMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(certificateRequestBatchMapper.fromId(null)).isNull();
    }
}
