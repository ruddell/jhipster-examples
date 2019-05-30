package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MonoApp;
import com.mycompany.myapp.domain.Pledge;
import com.mycompany.myapp.repository.PledgeRepository;
import com.mycompany.myapp.service.PledgeService;
import com.mycompany.myapp.service.dto.PledgeDTO;
import com.mycompany.myapp.service.mapper.PledgeMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.PledgeCriteria;
import com.mycompany.myapp.service.PledgeQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PledgeResource} REST controller.
 */
@SpringBootTest(classes = MonoApp.class)
public class PledgeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PledgeRepository pledgeRepository;

    @Autowired
    private PledgeMapper pledgeMapper;

    @Autowired
    private PledgeService pledgeService;

    @Autowired
    private PledgeQueryService pledgeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPledgeMockMvc;

    private Pledge pledge;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PledgeResource pledgeResource = new PledgeResource(pledgeService, pledgeQueryService);
        this.restPledgeMockMvc = MockMvcBuilders.standaloneSetup(pledgeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pledge createEntity(EntityManager em) {
        Pledge pledge = new Pledge()
            .name(DEFAULT_NAME);
        return pledge;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pledge createUpdatedEntity(EntityManager em) {
        Pledge pledge = new Pledge()
            .name(UPDATED_NAME);
        return pledge;
    }

    @BeforeEach
    public void initTest() {
        pledge = createEntity(em);
    }

    @Test
    @Transactional
    public void createPledge() throws Exception {
        int databaseSizeBeforeCreate = pledgeRepository.findAll().size();

        // Create the Pledge
        PledgeDTO pledgeDTO = pledgeMapper.toDto(pledge);
        restPledgeMockMvc.perform(post("/api/pledges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pledgeDTO)))
            .andExpect(status().isCreated());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeCreate + 1);
        Pledge testPledge = pledgeList.get(pledgeList.size() - 1);
        assertThat(testPledge.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPledgeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pledgeRepository.findAll().size();

        // Create the Pledge with an existing ID
        pledge.setId(1L);
        PledgeDTO pledgeDTO = pledgeMapper.toDto(pledge);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPledgeMockMvc.perform(post("/api/pledges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pledgeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPledges() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        // Get all the pledgeList
        restPledgeMockMvc.perform(get("/api/pledges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pledge.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getPledge() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        // Get the pledge
        restPledgeMockMvc.perform(get("/api/pledges/{id}", pledge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pledge.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllPledgesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        // Get all the pledgeList where name equals to DEFAULT_NAME
        defaultPledgeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the pledgeList where name equals to UPDATED_NAME
        defaultPledgeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPledgesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        // Get all the pledgeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPledgeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the pledgeList where name equals to UPDATED_NAME
        defaultPledgeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPledgesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        // Get all the pledgeList where name is not null
        defaultPledgeShouldBeFound("name.specified=true");

        // Get all the pledgeList where name is null
        defaultPledgeShouldNotBeFound("name.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPledgeShouldBeFound(String filter) throws Exception {
        restPledgeMockMvc.perform(get("/api/pledges?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pledge.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restPledgeMockMvc.perform(get("/api/pledges/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPledgeShouldNotBeFound(String filter) throws Exception {
        restPledgeMockMvc.perform(get("/api/pledges?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPledgeMockMvc.perform(get("/api/pledges/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPledge() throws Exception {
        // Get the pledge
        restPledgeMockMvc.perform(get("/api/pledges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePledge() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        int databaseSizeBeforeUpdate = pledgeRepository.findAll().size();

        // Update the pledge
        Pledge updatedPledge = pledgeRepository.findById(pledge.getId()).get();
        // Disconnect from session so that the updates on updatedPledge are not directly saved in db
        em.detach(updatedPledge);
        updatedPledge
            .name(UPDATED_NAME);
        PledgeDTO pledgeDTO = pledgeMapper.toDto(updatedPledge);

        restPledgeMockMvc.perform(put("/api/pledges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pledgeDTO)))
            .andExpect(status().isOk());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeUpdate);
        Pledge testPledge = pledgeList.get(pledgeList.size() - 1);
        assertThat(testPledge.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPledge() throws Exception {
        int databaseSizeBeforeUpdate = pledgeRepository.findAll().size();

        // Create the Pledge
        PledgeDTO pledgeDTO = pledgeMapper.toDto(pledge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPledgeMockMvc.perform(put("/api/pledges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pledgeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePledge() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        int databaseSizeBeforeDelete = pledgeRepository.findAll().size();

        // Delete the pledge
        restPledgeMockMvc.perform(delete("/api/pledges/{id}", pledge.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pledge.class);
        Pledge pledge1 = new Pledge();
        pledge1.setId(1L);
        Pledge pledge2 = new Pledge();
        pledge2.setId(pledge1.getId());
        assertThat(pledge1).isEqualTo(pledge2);
        pledge2.setId(2L);
        assertThat(pledge1).isNotEqualTo(pledge2);
        pledge1.setId(null);
        assertThat(pledge1).isNotEqualTo(pledge2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PledgeDTO.class);
        PledgeDTO pledgeDTO1 = new PledgeDTO();
        pledgeDTO1.setId(1L);
        PledgeDTO pledgeDTO2 = new PledgeDTO();
        assertThat(pledgeDTO1).isNotEqualTo(pledgeDTO2);
        pledgeDTO2.setId(pledgeDTO1.getId());
        assertThat(pledgeDTO1).isEqualTo(pledgeDTO2);
        pledgeDTO2.setId(2L);
        assertThat(pledgeDTO1).isNotEqualTo(pledgeDTO2);
        pledgeDTO1.setId(null);
        assertThat(pledgeDTO1).isNotEqualTo(pledgeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pledgeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pledgeMapper.fromId(null)).isNull();
    }
}
