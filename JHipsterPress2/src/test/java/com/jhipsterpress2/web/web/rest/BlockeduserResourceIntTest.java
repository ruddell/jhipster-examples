package com.jhipsterpress2.web.web.rest;

import com.jhipsterpress2.web.JHipsterPress2App;

import com.jhipsterpress2.web.domain.Blockeduser;
import com.jhipsterpress2.web.domain.Party;
import com.jhipsterpress2.web.domain.Party;
import com.jhipsterpress2.web.repository.BlockeduserRepository;
import com.jhipsterpress2.web.service.BlockeduserService;
import com.jhipsterpress2.web.service.dto.BlockeduserDTO;
import com.jhipsterpress2.web.service.mapper.BlockeduserMapper;
import com.jhipsterpress2.web.web.rest.errors.ExceptionTranslator;
import com.jhipsterpress2.web.service.dto.BlockeduserCriteria;
import com.jhipsterpress2.web.service.BlockeduserQueryService;

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


import static com.jhipsterpress2.web.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BlockeduserResource REST controller.
 *
 * @see BlockeduserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JHipsterPress2App.class)
public class BlockeduserResourceIntTest {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BlockeduserRepository blockeduserRepository;


    @Autowired
    private BlockeduserMapper blockeduserMapper;
    

    @Autowired
    private BlockeduserService blockeduserService;

    @Autowired
    private BlockeduserQueryService blockeduserQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBlockeduserMockMvc;

    private Blockeduser blockeduser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlockeduserResource blockeduserResource = new BlockeduserResource(blockeduserService, blockeduserQueryService);
        this.restBlockeduserMockMvc = MockMvcBuilders.standaloneSetup(blockeduserResource)
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
    public static Blockeduser createEntity(EntityManager em) {
        Blockeduser blockeduser = new Blockeduser()
            .creationDate(DEFAULT_CREATION_DATE);
        return blockeduser;
    }

    @Before
    public void initTest() {
        blockeduser = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlockeduser() throws Exception {
        int databaseSizeBeforeCreate = blockeduserRepository.findAll().size();

        // Create the Blockeduser
        BlockeduserDTO blockeduserDTO = blockeduserMapper.toDto(blockeduser);
        restBlockeduserMockMvc.perform(post("/api/blockedusers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockeduserDTO)))
            .andExpect(status().isCreated());

        // Validate the Blockeduser in the database
        List<Blockeduser> blockeduserList = blockeduserRepository.findAll();
        assertThat(blockeduserList).hasSize(databaseSizeBeforeCreate + 1);
        Blockeduser testBlockeduser = blockeduserList.get(blockeduserList.size() - 1);
        assertThat(testBlockeduser.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createBlockeduserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blockeduserRepository.findAll().size();

        // Create the Blockeduser with an existing ID
        blockeduser.setId(1L);
        BlockeduserDTO blockeduserDTO = blockeduserMapper.toDto(blockeduser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlockeduserMockMvc.perform(post("/api/blockedusers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockeduserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Blockeduser in the database
        List<Blockeduser> blockeduserList = blockeduserRepository.findAll();
        assertThat(blockeduserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBlockedusers() throws Exception {
        // Initialize the database
        blockeduserRepository.saveAndFlush(blockeduser);

        // Get all the blockeduserList
        restBlockeduserMockMvc.perform(get("/api/blockedusers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blockeduser.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getBlockeduser() throws Exception {
        // Initialize the database
        blockeduserRepository.saveAndFlush(blockeduser);

        // Get the blockeduser
        restBlockeduserMockMvc.perform(get("/api/blockedusers/{id}", blockeduser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blockeduser.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllBlockedusersByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        blockeduserRepository.saveAndFlush(blockeduser);

        // Get all the blockeduserList where creationDate equals to DEFAULT_CREATION_DATE
        defaultBlockeduserShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the blockeduserList where creationDate equals to UPDATED_CREATION_DATE
        defaultBlockeduserShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllBlockedusersByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        blockeduserRepository.saveAndFlush(blockeduser);

        // Get all the blockeduserList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultBlockeduserShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the blockeduserList where creationDate equals to UPDATED_CREATION_DATE
        defaultBlockeduserShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllBlockedusersByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        blockeduserRepository.saveAndFlush(blockeduser);

        // Get all the blockeduserList where creationDate is not null
        defaultBlockeduserShouldBeFound("creationDate.specified=true");

        // Get all the blockeduserList where creationDate is null
        defaultBlockeduserShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllBlockedusersByBlockinguserIsEqualToSomething() throws Exception {
        // Initialize the database
        Party blockinguser = PartyResourceIntTest.createEntity(em);
        em.persist(blockinguser);
        em.flush();
        blockeduser.setBlockinguser(blockinguser);
        blockeduserRepository.saveAndFlush(blockeduser);
        Long blockinguserId = blockinguser.getId();

        // Get all the blockeduserList where blockinguser equals to blockinguserId
        defaultBlockeduserShouldBeFound("blockinguserId.equals=" + blockinguserId);

        // Get all the blockeduserList where blockinguser equals to blockinguserId + 1
        defaultBlockeduserShouldNotBeFound("blockinguserId.equals=" + (blockinguserId + 1));
    }


    @Test
    @Transactional
    public void getAllBlockedusersByBlockeduserIsEqualToSomething() throws Exception {
        // Initialize the database
        Party blockeduser = PartyResourceIntTest.createEntity(em);
        em.persist(blockeduser);
        em.flush();
        blockeduser.setBlockeduser(blockeduser);
        blockeduserRepository.saveAndFlush(blockeduser);
        Long blockeduserId = blockeduser.getId();

        // Get all the blockeduserList where blockeduser equals to blockeduserId
        defaultBlockeduserShouldBeFound("blockeduserId.equals=" + blockeduserId);

        // Get all the blockeduserList where blockeduser equals to blockeduserId + 1
        defaultBlockeduserShouldNotBeFound("blockeduserId.equals=" + (blockeduserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBlockeduserShouldBeFound(String filter) throws Exception {
        restBlockeduserMockMvc.perform(get("/api/blockedusers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blockeduser.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBlockeduserShouldNotBeFound(String filter) throws Exception {
        restBlockeduserMockMvc.perform(get("/api/blockedusers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingBlockeduser() throws Exception {
        // Get the blockeduser
        restBlockeduserMockMvc.perform(get("/api/blockedusers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlockeduser() throws Exception {
        // Initialize the database
        blockeduserRepository.saveAndFlush(blockeduser);

        int databaseSizeBeforeUpdate = blockeduserRepository.findAll().size();

        // Update the blockeduser
        Blockeduser updatedBlockeduser = blockeduserRepository.findById(blockeduser.getId()).get();
        // Disconnect from session so that the updates on updatedBlockeduser are not directly saved in db
        em.detach(updatedBlockeduser);
        updatedBlockeduser
            .creationDate(UPDATED_CREATION_DATE);
        BlockeduserDTO blockeduserDTO = blockeduserMapper.toDto(updatedBlockeduser);

        restBlockeduserMockMvc.perform(put("/api/blockedusers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockeduserDTO)))
            .andExpect(status().isOk());

        // Validate the Blockeduser in the database
        List<Blockeduser> blockeduserList = blockeduserRepository.findAll();
        assertThat(blockeduserList).hasSize(databaseSizeBeforeUpdate);
        Blockeduser testBlockeduser = blockeduserList.get(blockeduserList.size() - 1);
        assertThat(testBlockeduser.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBlockeduser() throws Exception {
        int databaseSizeBeforeUpdate = blockeduserRepository.findAll().size();

        // Create the Blockeduser
        BlockeduserDTO blockeduserDTO = blockeduserMapper.toDto(blockeduser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBlockeduserMockMvc.perform(put("/api/blockedusers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockeduserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Blockeduser in the database
        List<Blockeduser> blockeduserList = blockeduserRepository.findAll();
        assertThat(blockeduserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBlockeduser() throws Exception {
        // Initialize the database
        blockeduserRepository.saveAndFlush(blockeduser);

        int databaseSizeBeforeDelete = blockeduserRepository.findAll().size();

        // Get the blockeduser
        restBlockeduserMockMvc.perform(delete("/api/blockedusers/{id}", blockeduser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Blockeduser> blockeduserList = blockeduserRepository.findAll();
        assertThat(blockeduserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Blockeduser.class);
        Blockeduser blockeduser1 = new Blockeduser();
        blockeduser1.setId(1L);
        Blockeduser blockeduser2 = new Blockeduser();
        blockeduser2.setId(blockeduser1.getId());
        assertThat(blockeduser1).isEqualTo(blockeduser2);
        blockeduser2.setId(2L);
        assertThat(blockeduser1).isNotEqualTo(blockeduser2);
        blockeduser1.setId(null);
        assertThat(blockeduser1).isNotEqualTo(blockeduser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlockeduserDTO.class);
        BlockeduserDTO blockeduserDTO1 = new BlockeduserDTO();
        blockeduserDTO1.setId(1L);
        BlockeduserDTO blockeduserDTO2 = new BlockeduserDTO();
        assertThat(blockeduserDTO1).isNotEqualTo(blockeduserDTO2);
        blockeduserDTO2.setId(blockeduserDTO1.getId());
        assertThat(blockeduserDTO1).isEqualTo(blockeduserDTO2);
        blockeduserDTO2.setId(2L);
        assertThat(blockeduserDTO1).isNotEqualTo(blockeduserDTO2);
        blockeduserDTO1.setId(null);
        assertThat(blockeduserDTO1).isNotEqualTo(blockeduserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(blockeduserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(blockeduserMapper.fromId(null)).isNull();
    }
}
