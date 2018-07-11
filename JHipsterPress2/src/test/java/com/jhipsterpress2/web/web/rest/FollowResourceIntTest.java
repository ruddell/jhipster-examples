package com.jhipsterpress2.web.web.rest;

import com.jhipsterpress2.web.JHipsterPress2App;

import com.jhipsterpress2.web.domain.Follow;
import com.jhipsterpress2.web.domain.Party;
import com.jhipsterpress2.web.domain.Party;
import com.jhipsterpress2.web.repository.FollowRepository;
import com.jhipsterpress2.web.service.FollowService;
import com.jhipsterpress2.web.service.dto.FollowDTO;
import com.jhipsterpress2.web.service.mapper.FollowMapper;
import com.jhipsterpress2.web.web.rest.errors.ExceptionTranslator;
import com.jhipsterpress2.web.service.dto.FollowCriteria;
import com.jhipsterpress2.web.service.FollowQueryService;

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
 * Test class for the FollowResource REST controller.
 *
 * @see FollowResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JHipsterPress2App.class)
public class FollowResourceIntTest {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private FollowRepository followRepository;


    @Autowired
    private FollowMapper followMapper;
    

    @Autowired
    private FollowService followService;

    @Autowired
    private FollowQueryService followQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFollowMockMvc;

    private Follow follow;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FollowResource followResource = new FollowResource(followService, followQueryService);
        this.restFollowMockMvc = MockMvcBuilders.standaloneSetup(followResource)
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
    public static Follow createEntity(EntityManager em) {
        Follow follow = new Follow()
            .creationDate(DEFAULT_CREATION_DATE);
        return follow;
    }

    @Before
    public void initTest() {
        follow = createEntity(em);
    }

    @Test
    @Transactional
    public void createFollow() throws Exception {
        int databaseSizeBeforeCreate = followRepository.findAll().size();

        // Create the Follow
        FollowDTO followDTO = followMapper.toDto(follow);
        restFollowMockMvc.perform(post("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followDTO)))
            .andExpect(status().isCreated());

        // Validate the Follow in the database
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeCreate + 1);
        Follow testFollow = followList.get(followList.size() - 1);
        assertThat(testFollow.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createFollowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = followRepository.findAll().size();

        // Create the Follow with an existing ID
        follow.setId(1L);
        FollowDTO followDTO = followMapper.toDto(follow);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFollowMockMvc.perform(post("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Follow in the database
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFollows() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        // Get all the followList
        restFollowMockMvc.perform(get("/api/follows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(follow.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getFollow() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        // Get the follow
        restFollowMockMvc.perform(get("/api/follows/{id}", follow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(follow.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllFollowsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        // Get all the followList where creationDate equals to DEFAULT_CREATION_DATE
        defaultFollowShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the followList where creationDate equals to UPDATED_CREATION_DATE
        defaultFollowShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllFollowsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        // Get all the followList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultFollowShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the followList where creationDate equals to UPDATED_CREATION_DATE
        defaultFollowShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllFollowsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        // Get all the followList where creationDate is not null
        defaultFollowShouldBeFound("creationDate.specified=true");

        // Get all the followList where creationDate is null
        defaultFollowShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFollowsByFollowedIsEqualToSomething() throws Exception {
        // Initialize the database
        Party followed = PartyResourceIntTest.createEntity(em);
        em.persist(followed);
        em.flush();
        follow.setFollowed(followed);
        followRepository.saveAndFlush(follow);
        Long followedId = followed.getId();

        // Get all the followList where followed equals to followedId
        defaultFollowShouldBeFound("followedId.equals=" + followedId);

        // Get all the followList where followed equals to followedId + 1
        defaultFollowShouldNotBeFound("followedId.equals=" + (followedId + 1));
    }


    @Test
    @Transactional
    public void getAllFollowsByFollowingIsEqualToSomething() throws Exception {
        // Initialize the database
        Party following = PartyResourceIntTest.createEntity(em);
        em.persist(following);
        em.flush();
        follow.setFollowing(following);
        followRepository.saveAndFlush(follow);
        Long followingId = following.getId();

        // Get all the followList where following equals to followingId
        defaultFollowShouldBeFound("followingId.equals=" + followingId);

        // Get all the followList where following equals to followingId + 1
        defaultFollowShouldNotBeFound("followingId.equals=" + (followingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFollowShouldBeFound(String filter) throws Exception {
        restFollowMockMvc.perform(get("/api/follows?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(follow.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFollowShouldNotBeFound(String filter) throws Exception {
        restFollowMockMvc.perform(get("/api/follows?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingFollow() throws Exception {
        // Get the follow
        restFollowMockMvc.perform(get("/api/follows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFollow() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        int databaseSizeBeforeUpdate = followRepository.findAll().size();

        // Update the follow
        Follow updatedFollow = followRepository.findById(follow.getId()).get();
        // Disconnect from session so that the updates on updatedFollow are not directly saved in db
        em.detach(updatedFollow);
        updatedFollow
            .creationDate(UPDATED_CREATION_DATE);
        FollowDTO followDTO = followMapper.toDto(updatedFollow);

        restFollowMockMvc.perform(put("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followDTO)))
            .andExpect(status().isOk());

        // Validate the Follow in the database
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeUpdate);
        Follow testFollow = followList.get(followList.size() - 1);
        assertThat(testFollow.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFollow() throws Exception {
        int databaseSizeBeforeUpdate = followRepository.findAll().size();

        // Create the Follow
        FollowDTO followDTO = followMapper.toDto(follow);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFollowMockMvc.perform(put("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Follow in the database
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFollow() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        int databaseSizeBeforeDelete = followRepository.findAll().size();

        // Get the follow
        restFollowMockMvc.perform(delete("/api/follows/{id}", follow.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Follow.class);
        Follow follow1 = new Follow();
        follow1.setId(1L);
        Follow follow2 = new Follow();
        follow2.setId(follow1.getId());
        assertThat(follow1).isEqualTo(follow2);
        follow2.setId(2L);
        assertThat(follow1).isNotEqualTo(follow2);
        follow1.setId(null);
        assertThat(follow1).isNotEqualTo(follow2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FollowDTO.class);
        FollowDTO followDTO1 = new FollowDTO();
        followDTO1.setId(1L);
        FollowDTO followDTO2 = new FollowDTO();
        assertThat(followDTO1).isNotEqualTo(followDTO2);
        followDTO2.setId(followDTO1.getId());
        assertThat(followDTO1).isEqualTo(followDTO2);
        followDTO2.setId(2L);
        assertThat(followDTO1).isNotEqualTo(followDTO2);
        followDTO1.setId(null);
        assertThat(followDTO1).isNotEqualTo(followDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(followMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(followMapper.fromId(null)).isNull();
    }
}
