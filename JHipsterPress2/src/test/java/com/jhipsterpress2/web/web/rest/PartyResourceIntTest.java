package com.jhipsterpress2.web.web.rest;

import com.jhipsterpress2.web.JHipsterPress2App;

import com.jhipsterpress2.web.domain.Party;
import com.jhipsterpress2.web.domain.Blog;
import com.jhipsterpress2.web.domain.Comment;
import com.jhipsterpress2.web.domain.Message;
import com.jhipsterpress2.web.domain.Follow;
import com.jhipsterpress2.web.domain.Follow;
import com.jhipsterpress2.web.domain.Blockeduser;
import com.jhipsterpress2.web.domain.Blockeduser;
import com.jhipsterpress2.web.domain.User;
import com.jhipsterpress2.web.domain.Album;
import com.jhipsterpress2.web.domain.Interest;
import com.jhipsterpress2.web.domain.Activity;
import com.jhipsterpress2.web.domain.Celeb;
import com.jhipsterpress2.web.repository.PartyRepository;
import com.jhipsterpress2.web.service.PartyService;
import com.jhipsterpress2.web.service.dto.PartyDTO;
import com.jhipsterpress2.web.service.mapper.PartyMapper;
import com.jhipsterpress2.web.web.rest.errors.ExceptionTranslator;
import com.jhipsterpress2.web.service.dto.PartyCriteria;
import com.jhipsterpress2.web.service.PartyQueryService;

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
import org.springframework.util.Base64Utils;

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
 * Test class for the PartyResource REST controller.
 *
 * @see PartyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JHipsterPress2App.class)
public class PartyResourceIntTest {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PARTYNAME = "AAAAAAAAAA";
    private static final String UPDATED_PARTYNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARTYDESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PARTYDESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private PartyRepository partyRepository;


    @Autowired
    private PartyMapper partyMapper;
    

    @Autowired
    private PartyService partyService;

    @Autowired
    private PartyQueryService partyQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartyMockMvc;

    private Party party;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartyResource partyResource = new PartyResource(partyService, partyQueryService);
        this.restPartyMockMvc = MockMvcBuilders.standaloneSetup(partyResource)
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
    public static Party createEntity(EntityManager em) {
        Party party = new Party()
            .creationDate(DEFAULT_CREATION_DATE)
            .partyname(DEFAULT_PARTYNAME)
            .partydescription(DEFAULT_PARTYDESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .isActive(DEFAULT_IS_ACTIVE);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        party.setUser(user);
        return party;
    }

    @Before
    public void initTest() {
        party = createEntity(em);
    }

    @Test
    @Transactional
    public void createParty() throws Exception {
        int databaseSizeBeforeCreate = partyRepository.findAll().size();

        // Create the Party
        PartyDTO partyDTO = partyMapper.toDto(party);
        restPartyMockMvc.perform(post("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyDTO)))
            .andExpect(status().isCreated());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeCreate + 1);
        Party testParty = partyList.get(partyList.size() - 1);
        assertThat(testParty.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testParty.getPartyname()).isEqualTo(DEFAULT_PARTYNAME);
        assertThat(testParty.getPartydescription()).isEqualTo(DEFAULT_PARTYDESCRIPTION);
        assertThat(testParty.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testParty.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testParty.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createPartyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyRepository.findAll().size();

        // Create the Party with an existing ID
        party.setId(1L);
        PartyDTO partyDTO = partyMapper.toDto(party);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyMockMvc.perform(post("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyRepository.findAll().size();
        // set the field null
        party.setCreationDate(null);

        // Create the Party, which fails.
        PartyDTO partyDTO = partyMapper.toDto(party);

        restPartyMockMvc.perform(post("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyDTO)))
            .andExpect(status().isBadRequest());

        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartynameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyRepository.findAll().size();
        // set the field null
        party.setPartyname(null);

        // Create the Party, which fails.
        PartyDTO partyDTO = partyMapper.toDto(party);

        restPartyMockMvc.perform(post("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyDTO)))
            .andExpect(status().isBadRequest());

        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartydescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyRepository.findAll().size();
        // set the field null
        party.setPartydescription(null);

        // Create the Party, which fails.
        PartyDTO partyDTO = partyMapper.toDto(party);

        restPartyMockMvc.perform(post("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyDTO)))
            .andExpect(status().isBadRequest());

        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParties() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList
        restPartyMockMvc.perform(get("/api/parties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(party.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].partyname").value(hasItem(DEFAULT_PARTYNAME.toString())))
            .andExpect(jsonPath("$.[*].partydescription").value(hasItem(DEFAULT_PARTYDESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get the party
        restPartyMockMvc.perform(get("/api/parties/{id}", party.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(party.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.partyname").value(DEFAULT_PARTYNAME.toString()))
            .andExpect(jsonPath("$.partydescription").value(DEFAULT_PARTYDESCRIPTION.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllPartiesByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where creationDate equals to DEFAULT_CREATION_DATE
        defaultPartyShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the partyList where creationDate equals to UPDATED_CREATION_DATE
        defaultPartyShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllPartiesByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultPartyShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the partyList where creationDate equals to UPDATED_CREATION_DATE
        defaultPartyShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllPartiesByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where creationDate is not null
        defaultPartyShouldBeFound("creationDate.specified=true");

        // Get all the partyList where creationDate is null
        defaultPartyShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByPartynameIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where partyname equals to DEFAULT_PARTYNAME
        defaultPartyShouldBeFound("partyname.equals=" + DEFAULT_PARTYNAME);

        // Get all the partyList where partyname equals to UPDATED_PARTYNAME
        defaultPartyShouldNotBeFound("partyname.equals=" + UPDATED_PARTYNAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByPartynameIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where partyname in DEFAULT_PARTYNAME or UPDATED_PARTYNAME
        defaultPartyShouldBeFound("partyname.in=" + DEFAULT_PARTYNAME + "," + UPDATED_PARTYNAME);

        // Get all the partyList where partyname equals to UPDATED_PARTYNAME
        defaultPartyShouldNotBeFound("partyname.in=" + UPDATED_PARTYNAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByPartynameIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where partyname is not null
        defaultPartyShouldBeFound("partyname.specified=true");

        // Get all the partyList where partyname is null
        defaultPartyShouldNotBeFound("partyname.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByPartydescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where partydescription equals to DEFAULT_PARTYDESCRIPTION
        defaultPartyShouldBeFound("partydescription.equals=" + DEFAULT_PARTYDESCRIPTION);

        // Get all the partyList where partydescription equals to UPDATED_PARTYDESCRIPTION
        defaultPartyShouldNotBeFound("partydescription.equals=" + UPDATED_PARTYDESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPartiesByPartydescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where partydescription in DEFAULT_PARTYDESCRIPTION or UPDATED_PARTYDESCRIPTION
        defaultPartyShouldBeFound("partydescription.in=" + DEFAULT_PARTYDESCRIPTION + "," + UPDATED_PARTYDESCRIPTION);

        // Get all the partyList where partydescription equals to UPDATED_PARTYDESCRIPTION
        defaultPartyShouldNotBeFound("partydescription.in=" + UPDATED_PARTYDESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPartiesByPartydescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where partydescription is not null
        defaultPartyShouldBeFound("partydescription.specified=true");

        // Get all the partyList where partydescription is null
        defaultPartyShouldNotBeFound("partydescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where isActive equals to DEFAULT_IS_ACTIVE
        defaultPartyShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the partyList where isActive equals to UPDATED_IS_ACTIVE
        defaultPartyShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPartiesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultPartyShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the partyList where isActive equals to UPDATED_IS_ACTIVE
        defaultPartyShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPartiesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where isActive is not null
        defaultPartyShouldBeFound("isActive.specified=true");

        // Get all the partyList where isActive is null
        defaultPartyShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByBlogIsEqualToSomething() throws Exception {
        // Initialize the database
        Blog blog = BlogResourceIntTest.createEntity(em);
        em.persist(blog);
        em.flush();
        party.addBlog(blog);
        partyRepository.saveAndFlush(party);
        Long blogId = blog.getId();

        // Get all the partyList where blog equals to blogId
        defaultPartyShouldBeFound("blogId.equals=" + blogId);

        // Get all the partyList where blog equals to blogId + 1
        defaultPartyShouldNotBeFound("blogId.equals=" + (blogId + 1));
    }


    @Test
    @Transactional
    public void getAllPartiesByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        Comment comment = CommentResourceIntTest.createEntity(em);
        em.persist(comment);
        em.flush();
        party.addComment(comment);
        partyRepository.saveAndFlush(party);
        Long commentId = comment.getId();

        // Get all the partyList where comment equals to commentId
        defaultPartyShouldBeFound("commentId.equals=" + commentId);

        // Get all the partyList where comment equals to commentId + 1
        defaultPartyShouldNotBeFound("commentId.equals=" + (commentId + 1));
    }


    @Test
    @Transactional
    public void getAllPartiesByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        Message message = MessageResourceIntTest.createEntity(em);
        em.persist(message);
        em.flush();
        party.addMessage(message);
        partyRepository.saveAndFlush(party);
        Long messageId = message.getId();

        // Get all the partyList where message equals to messageId
        defaultPartyShouldBeFound("messageId.equals=" + messageId);

        // Get all the partyList where message equals to messageId + 1
        defaultPartyShouldNotBeFound("messageId.equals=" + (messageId + 1));
    }


    @Test
    @Transactional
    public void getAllPartiesByFollowedIsEqualToSomething() throws Exception {
        // Initialize the database
        Follow followed = FollowResourceIntTest.createEntity(em);
        em.persist(followed);
        em.flush();
        party.addFollowed(followed);
        partyRepository.saveAndFlush(party);
        Long followedId = followed.getId();

        // Get all the partyList where followed equals to followedId
        defaultPartyShouldBeFound("followedId.equals=" + followedId);

        // Get all the partyList where followed equals to followedId + 1
        defaultPartyShouldNotBeFound("followedId.equals=" + (followedId + 1));
    }


    @Test
    @Transactional
    public void getAllPartiesByFollowingIsEqualToSomething() throws Exception {
        // Initialize the database
        Follow following = FollowResourceIntTest.createEntity(em);
        em.persist(following);
        em.flush();
        party.addFollowing(following);
        partyRepository.saveAndFlush(party);
        Long followingId = following.getId();

        // Get all the partyList where following equals to followingId
        defaultPartyShouldBeFound("followingId.equals=" + followingId);

        // Get all the partyList where following equals to followingId + 1
        defaultPartyShouldNotBeFound("followingId.equals=" + (followingId + 1));
    }


    @Test
    @Transactional
    public void getAllPartiesByBlockinguserIsEqualToSomething() throws Exception {
        // Initialize the database
        Blockeduser blockinguser = BlockeduserResourceIntTest.createEntity(em);
        em.persist(blockinguser);
        em.flush();
        party.addBlockinguser(blockinguser);
        partyRepository.saveAndFlush(party);
        Long blockinguserId = blockinguser.getId();

        // Get all the partyList where blockinguser equals to blockinguserId
        defaultPartyShouldBeFound("blockinguserId.equals=" + blockinguserId);

        // Get all the partyList where blockinguser equals to blockinguserId + 1
        defaultPartyShouldNotBeFound("blockinguserId.equals=" + (blockinguserId + 1));
    }


    @Test
    @Transactional
    public void getAllPartiesByBlockeduserIsEqualToSomething() throws Exception {
        // Initialize the database
        Blockeduser blockeduser = BlockeduserResourceIntTest.createEntity(em);
        em.persist(blockeduser);
        em.flush();
        party.addBlockeduser(blockeduser);
        partyRepository.saveAndFlush(party);
        Long blockeduserId = blockeduser.getId();

        // Get all the partyList where blockeduser equals to blockeduserId
        defaultPartyShouldBeFound("blockeduserId.equals=" + blockeduserId);

        // Get all the partyList where blockeduser equals to blockeduserId + 1
        defaultPartyShouldNotBeFound("blockeduserId.equals=" + (blockeduserId + 1));
    }


    @Test
    @Transactional
    public void getAllPartiesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        party.setUser(user);
        partyRepository.saveAndFlush(party);
        Long userId = user.getId();

        // Get all the partyList where user equals to userId
        defaultPartyShouldBeFound("userId.equals=" + userId);

        // Get all the partyList where user equals to userId + 1
        defaultPartyShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllPartiesByAlbumIsEqualToSomething() throws Exception {
        // Initialize the database
        Album album = AlbumResourceIntTest.createEntity(em);
        em.persist(album);
        em.flush();
        party.setAlbum(album);
        album.setParty(party);
        partyRepository.saveAndFlush(party);
        Long albumId = album.getId();

        // Get all the partyList where album equals to albumId
        defaultPartyShouldBeFound("albumId.equals=" + albumId);

        // Get all the partyList where album equals to albumId + 1
        defaultPartyShouldNotBeFound("albumId.equals=" + (albumId + 1));
    }


    @Test
    @Transactional
    public void getAllPartiesByInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        Interest interest = InterestResourceIntTest.createEntity(em);
        em.persist(interest);
        em.flush();
        party.addInterest(interest);
        partyRepository.saveAndFlush(party);
        Long interestId = interest.getId();

        // Get all the partyList where interest equals to interestId
        defaultPartyShouldBeFound("interestId.equals=" + interestId);

        // Get all the partyList where interest equals to interestId + 1
        defaultPartyShouldNotBeFound("interestId.equals=" + (interestId + 1));
    }


    @Test
    @Transactional
    public void getAllPartiesByActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        Activity activity = ActivityResourceIntTest.createEntity(em);
        em.persist(activity);
        em.flush();
        party.addActivity(activity);
        partyRepository.saveAndFlush(party);
        Long activityId = activity.getId();

        // Get all the partyList where activity equals to activityId
        defaultPartyShouldBeFound("activityId.equals=" + activityId);

        // Get all the partyList where activity equals to activityId + 1
        defaultPartyShouldNotBeFound("activityId.equals=" + (activityId + 1));
    }


    @Test
    @Transactional
    public void getAllPartiesByCelebIsEqualToSomething() throws Exception {
        // Initialize the database
        Celeb celeb = CelebResourceIntTest.createEntity(em);
        em.persist(celeb);
        em.flush();
        party.addCeleb(celeb);
        partyRepository.saveAndFlush(party);
        Long celebId = celeb.getId();

        // Get all the partyList where celeb equals to celebId
        defaultPartyShouldBeFound("celebId.equals=" + celebId);

        // Get all the partyList where celeb equals to celebId + 1
        defaultPartyShouldNotBeFound("celebId.equals=" + (celebId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPartyShouldBeFound(String filter) throws Exception {
        restPartyMockMvc.perform(get("/api/parties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(party.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].partyname").value(hasItem(DEFAULT_PARTYNAME.toString())))
            .andExpect(jsonPath("$.[*].partydescription").value(hasItem(DEFAULT_PARTYDESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPartyShouldNotBeFound(String filter) throws Exception {
        restPartyMockMvc.perform(get("/api/parties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingParty() throws Exception {
        // Get the party
        restPartyMockMvc.perform(get("/api/parties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        int databaseSizeBeforeUpdate = partyRepository.findAll().size();

        // Update the party
        Party updatedParty = partyRepository.findById(party.getId()).get();
        // Disconnect from session so that the updates on updatedParty are not directly saved in db
        em.detach(updatedParty);
        updatedParty
            .creationDate(UPDATED_CREATION_DATE)
            .partyname(UPDATED_PARTYNAME)
            .partydescription(UPDATED_PARTYDESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isActive(UPDATED_IS_ACTIVE);
        PartyDTO partyDTO = partyMapper.toDto(updatedParty);

        restPartyMockMvc.perform(put("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyDTO)))
            .andExpect(status().isOk());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeUpdate);
        Party testParty = partyList.get(partyList.size() - 1);
        assertThat(testParty.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testParty.getPartyname()).isEqualTo(UPDATED_PARTYNAME);
        assertThat(testParty.getPartydescription()).isEqualTo(UPDATED_PARTYDESCRIPTION);
        assertThat(testParty.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testParty.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testParty.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingParty() throws Exception {
        int databaseSizeBeforeUpdate = partyRepository.findAll().size();

        // Create the Party
        PartyDTO partyDTO = partyMapper.toDto(party);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPartyMockMvc.perform(put("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        int databaseSizeBeforeDelete = partyRepository.findAll().size();

        // Get the party
        restPartyMockMvc.perform(delete("/api/parties/{id}", party.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Party.class);
        Party party1 = new Party();
        party1.setId(1L);
        Party party2 = new Party();
        party2.setId(party1.getId());
        assertThat(party1).isEqualTo(party2);
        party2.setId(2L);
        assertThat(party1).isNotEqualTo(party2);
        party1.setId(null);
        assertThat(party1).isNotEqualTo(party2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyDTO.class);
        PartyDTO partyDTO1 = new PartyDTO();
        partyDTO1.setId(1L);
        PartyDTO partyDTO2 = new PartyDTO();
        assertThat(partyDTO1).isNotEqualTo(partyDTO2);
        partyDTO2.setId(partyDTO1.getId());
        assertThat(partyDTO1).isEqualTo(partyDTO2);
        partyDTO2.setId(2L);
        assertThat(partyDTO1).isNotEqualTo(partyDTO2);
        partyDTO1.setId(null);
        assertThat(partyDTO1).isNotEqualTo(partyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(partyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(partyMapper.fromId(null)).isNull();
    }
}
