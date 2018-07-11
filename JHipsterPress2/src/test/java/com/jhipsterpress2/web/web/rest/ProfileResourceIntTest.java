package com.jhipsterpress2.web.web.rest;

import com.jhipsterpress2.web.JHipsterPress2App;

import com.jhipsterpress2.web.domain.Profile;
import com.jhipsterpress2.web.domain.User;
import com.jhipsterpress2.web.domain.Interest;
import com.jhipsterpress2.web.domain.Activity;
import com.jhipsterpress2.web.domain.Celeb;
import com.jhipsterpress2.web.repository.ProfileRepository;
import com.jhipsterpress2.web.service.ProfileService;
import com.jhipsterpress2.web.service.dto.ProfileDTO;
import com.jhipsterpress2.web.service.mapper.ProfileMapper;
import com.jhipsterpress2.web.web.rest.errors.ExceptionTranslator;
import com.jhipsterpress2.web.service.dto.ProfileCriteria;
import com.jhipsterpress2.web.service.ProfileQueryService;

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

import com.jhipsterpress2.web.domain.enumeration.Gender;
import com.jhipsterpress2.web.domain.enumeration.CivilStatus;
import com.jhipsterpress2.web.domain.enumeration.Gender;
import com.jhipsterpress2.web.domain.enumeration.Purpose;
import com.jhipsterpress2.web.domain.enumeration.Physical;
import com.jhipsterpress2.web.domain.enumeration.Religion;
import com.jhipsterpress2.web.domain.enumeration.EthnicGroup;
import com.jhipsterpress2.web.domain.enumeration.Studies;
import com.jhipsterpress2.web.domain.enumeration.Eyes;
import com.jhipsterpress2.web.domain.enumeration.Smoker;
import com.jhipsterpress2.web.domain.enumeration.Children;
import com.jhipsterpress2.web.domain.enumeration.FutureChildren;
/**
 * Test class for the ProfileResource REST controller.
 *
 * @see ProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JHipsterPress2App.class)
public class ProfileResourceIntTest {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_BIO = "AAAAAAAAAA";
    private static final String UPDATED_BIO = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final CivilStatus DEFAULT_CIVIL_STATUS = CivilStatus.SINGLE;
    private static final CivilStatus UPDATED_CIVIL_STATUS = CivilStatus.MARRIED;

    private static final Gender DEFAULT_LOOKING_FOR = Gender.MALE;
    private static final Gender UPDATED_LOOKING_FOR = Gender.FEMALE;

    private static final Purpose DEFAULT_PURPOSE = Purpose.NOT_INTERESTED;
    private static final Purpose UPDATED_PURPOSE = Purpose.FRIENDSHIP;

    private static final Physical DEFAULT_PHYSICAL = Physical.NA;
    private static final Physical UPDATED_PHYSICAL = Physical.THIN;

    private static final Religion DEFAULT_RELIGION = Religion.NA;
    private static final Religion UPDATED_RELIGION = Religion.ATHEIST;

    private static final EthnicGroup DEFAULT_ETHNIC_GROUP = EthnicGroup.NA;
    private static final EthnicGroup UPDATED_ETHNIC_GROUP = EthnicGroup.MIXED;

    private static final Studies DEFAULT_STUDIES = Studies.NA;
    private static final Studies UPDATED_STUDIES = Studies.PRIMARY;

    private static final Integer DEFAULT_SIBBLINGS = -1;
    private static final Integer UPDATED_SIBBLINGS = 0;

    private static final Eyes DEFAULT_EYES = Eyes.NA;
    private static final Eyes UPDATED_EYES = Eyes.BLUE;

    private static final Smoker DEFAULT_SMOKER = Smoker.NA;
    private static final Smoker UPDATED_SMOKER = Smoker.YES;

    private static final Children DEFAULT_CHILDREN = Children.NA;
    private static final Children UPDATED_CHILDREN = Children.YES;

    private static final FutureChildren DEFAULT_FUTURE_CHILDREN = FutureChildren.NA;
    private static final FutureChildren UPDATED_FUTURE_CHILDREN = FutureChildren.YES;

    private static final Boolean DEFAULT_PET = false;
    private static final Boolean UPDATED_PET = true;

    @Autowired
    private ProfileRepository profileRepository;


    @Autowired
    private ProfileMapper profileMapper;
    

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileQueryService profileQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfileMockMvc;

    private Profile profile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileResource profileResource = new ProfileResource(profileService, profileQueryService);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
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
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .creationDate(DEFAULT_CREATION_DATE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .gender(DEFAULT_GENDER)
            .phone(DEFAULT_PHONE)
            .bio(DEFAULT_BIO)
            .birthdate(DEFAULT_BIRTHDATE)
            .civilStatus(DEFAULT_CIVIL_STATUS)
            .lookingFor(DEFAULT_LOOKING_FOR)
            .purpose(DEFAULT_PURPOSE)
            .physical(DEFAULT_PHYSICAL)
            .religion(DEFAULT_RELIGION)
            .ethnicGroup(DEFAULT_ETHNIC_GROUP)
            .studies(DEFAULT_STUDIES)
            .sibblings(DEFAULT_SIBBLINGS)
            .eyes(DEFAULT_EYES)
            .smoker(DEFAULT_SMOKER)
            .children(DEFAULT_CHILDREN)
            .futureChildren(DEFAULT_FUTURE_CHILDREN)
            .pet(DEFAULT_PET);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        profile.setUser(user);
        return profile;
    }

    @Before
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testProfile.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProfile.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testProfile.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testProfile.getBio()).isEqualTo(DEFAULT_BIO);
        assertThat(testProfile.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testProfile.getCivilStatus()).isEqualTo(DEFAULT_CIVIL_STATUS);
        assertThat(testProfile.getLookingFor()).isEqualTo(DEFAULT_LOOKING_FOR);
        assertThat(testProfile.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testProfile.getPhysical()).isEqualTo(DEFAULT_PHYSICAL);
        assertThat(testProfile.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testProfile.getEthnicGroup()).isEqualTo(DEFAULT_ETHNIC_GROUP);
        assertThat(testProfile.getStudies()).isEqualTo(DEFAULT_STUDIES);
        assertThat(testProfile.getSibblings()).isEqualTo(DEFAULT_SIBBLINGS);
        assertThat(testProfile.getEyes()).isEqualTo(DEFAULT_EYES);
        assertThat(testProfile.getSmoker()).isEqualTo(DEFAULT_SMOKER);
        assertThat(testProfile.getChildren()).isEqualTo(DEFAULT_CHILDREN);
        assertThat(testProfile.getFutureChildren()).isEqualTo(DEFAULT_FUTURE_CHILDREN);
        assertThat(testProfile.isPet()).isEqualTo(DEFAULT_PET);
    }

    @Test
    @Transactional
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId(1L);
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setCreationDate(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setGender(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setPhone(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO.toString())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].civilStatus").value(hasItem(DEFAULT_CIVIL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lookingFor").value(hasItem(DEFAULT_LOOKING_FOR.toString())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE.toString())))
            .andExpect(jsonPath("$.[*].physical").value(hasItem(DEFAULT_PHYSICAL.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].ethnicGroup").value(hasItem(DEFAULT_ETHNIC_GROUP.toString())))
            .andExpect(jsonPath("$.[*].studies").value(hasItem(DEFAULT_STUDIES.toString())))
            .andExpect(jsonPath("$.[*].sibblings").value(hasItem(DEFAULT_SIBBLINGS)))
            .andExpect(jsonPath("$.[*].eyes").value(hasItem(DEFAULT_EYES.toString())))
            .andExpect(jsonPath("$.[*].smoker").value(hasItem(DEFAULT_SMOKER.toString())))
            .andExpect(jsonPath("$.[*].children").value(hasItem(DEFAULT_CHILDREN.toString())))
            .andExpect(jsonPath("$.[*].futureChildren").value(hasItem(DEFAULT_FUTURE_CHILDREN.toString())))
            .andExpect(jsonPath("$.[*].pet").value(hasItem(DEFAULT_PET.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO.toString()))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()))
            .andExpect(jsonPath("$.civilStatus").value(DEFAULT_CIVIL_STATUS.toString()))
            .andExpect(jsonPath("$.lookingFor").value(DEFAULT_LOOKING_FOR.toString()))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE.toString()))
            .andExpect(jsonPath("$.physical").value(DEFAULT_PHYSICAL.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION.toString()))
            .andExpect(jsonPath("$.ethnicGroup").value(DEFAULT_ETHNIC_GROUP.toString()))
            .andExpect(jsonPath("$.studies").value(DEFAULT_STUDIES.toString()))
            .andExpect(jsonPath("$.sibblings").value(DEFAULT_SIBBLINGS))
            .andExpect(jsonPath("$.eyes").value(DEFAULT_EYES.toString()))
            .andExpect(jsonPath("$.smoker").value(DEFAULT_SMOKER.toString()))
            .andExpect(jsonPath("$.children").value(DEFAULT_CHILDREN.toString()))
            .andExpect(jsonPath("$.futureChildren").value(DEFAULT_FUTURE_CHILDREN.toString()))
            .andExpect(jsonPath("$.pet").value(DEFAULT_PET.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllProfilesByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where creationDate equals to DEFAULT_CREATION_DATE
        defaultProfileShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the profileList where creationDate equals to UPDATED_CREATION_DATE
        defaultProfileShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllProfilesByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultProfileShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the profileList where creationDate equals to UPDATED_CREATION_DATE
        defaultProfileShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllProfilesByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where creationDate is not null
        defaultProfileShouldBeFound("creationDate.specified=true");

        // Get all the profileList where creationDate is null
        defaultProfileShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender equals to DEFAULT_GENDER
        defaultProfileShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the profileList where gender equals to UPDATED_GENDER
        defaultProfileShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllProfilesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultProfileShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the profileList where gender equals to UPDATED_GENDER
        defaultProfileShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllProfilesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender is not null
        defaultProfileShouldBeFound("gender.specified=true");

        // Get all the profileList where gender is null
        defaultProfileShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone equals to DEFAULT_PHONE
        defaultProfileShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the profileList where phone equals to UPDATED_PHONE
        defaultProfileShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultProfileShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the profileList where phone equals to UPDATED_PHONE
        defaultProfileShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone is not null
        defaultProfileShouldBeFound("phone.specified=true");

        // Get all the profileList where phone is null
        defaultProfileShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByBioIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where bio equals to DEFAULT_BIO
        defaultProfileShouldBeFound("bio.equals=" + DEFAULT_BIO);

        // Get all the profileList where bio equals to UPDATED_BIO
        defaultProfileShouldNotBeFound("bio.equals=" + UPDATED_BIO);
    }

    @Test
    @Transactional
    public void getAllProfilesByBioIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where bio in DEFAULT_BIO or UPDATED_BIO
        defaultProfileShouldBeFound("bio.in=" + DEFAULT_BIO + "," + UPDATED_BIO);

        // Get all the profileList where bio equals to UPDATED_BIO
        defaultProfileShouldNotBeFound("bio.in=" + UPDATED_BIO);
    }

    @Test
    @Transactional
    public void getAllProfilesByBioIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where bio is not null
        defaultProfileShouldBeFound("bio.specified=true");

        // Get all the profileList where bio is null
        defaultProfileShouldNotBeFound("bio.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByBirthdateIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where birthdate equals to DEFAULT_BIRTHDATE
        defaultProfileShouldBeFound("birthdate.equals=" + DEFAULT_BIRTHDATE);

        // Get all the profileList where birthdate equals to UPDATED_BIRTHDATE
        defaultProfileShouldNotBeFound("birthdate.equals=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllProfilesByBirthdateIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where birthdate in DEFAULT_BIRTHDATE or UPDATED_BIRTHDATE
        defaultProfileShouldBeFound("birthdate.in=" + DEFAULT_BIRTHDATE + "," + UPDATED_BIRTHDATE);

        // Get all the profileList where birthdate equals to UPDATED_BIRTHDATE
        defaultProfileShouldNotBeFound("birthdate.in=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllProfilesByBirthdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where birthdate is not null
        defaultProfileShouldBeFound("birthdate.specified=true");

        // Get all the profileList where birthdate is null
        defaultProfileShouldNotBeFound("birthdate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByCivilStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where civilStatus equals to DEFAULT_CIVIL_STATUS
        defaultProfileShouldBeFound("civilStatus.equals=" + DEFAULT_CIVIL_STATUS);

        // Get all the profileList where civilStatus equals to UPDATED_CIVIL_STATUS
        defaultProfileShouldNotBeFound("civilStatus.equals=" + UPDATED_CIVIL_STATUS);
    }

    @Test
    @Transactional
    public void getAllProfilesByCivilStatusIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where civilStatus in DEFAULT_CIVIL_STATUS or UPDATED_CIVIL_STATUS
        defaultProfileShouldBeFound("civilStatus.in=" + DEFAULT_CIVIL_STATUS + "," + UPDATED_CIVIL_STATUS);

        // Get all the profileList where civilStatus equals to UPDATED_CIVIL_STATUS
        defaultProfileShouldNotBeFound("civilStatus.in=" + UPDATED_CIVIL_STATUS);
    }

    @Test
    @Transactional
    public void getAllProfilesByCivilStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where civilStatus is not null
        defaultProfileShouldBeFound("civilStatus.specified=true");

        // Get all the profileList where civilStatus is null
        defaultProfileShouldNotBeFound("civilStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByLookingForIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lookingFor equals to DEFAULT_LOOKING_FOR
        defaultProfileShouldBeFound("lookingFor.equals=" + DEFAULT_LOOKING_FOR);

        // Get all the profileList where lookingFor equals to UPDATED_LOOKING_FOR
        defaultProfileShouldNotBeFound("lookingFor.equals=" + UPDATED_LOOKING_FOR);
    }

    @Test
    @Transactional
    public void getAllProfilesByLookingForIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lookingFor in DEFAULT_LOOKING_FOR or UPDATED_LOOKING_FOR
        defaultProfileShouldBeFound("lookingFor.in=" + DEFAULT_LOOKING_FOR + "," + UPDATED_LOOKING_FOR);

        // Get all the profileList where lookingFor equals to UPDATED_LOOKING_FOR
        defaultProfileShouldNotBeFound("lookingFor.in=" + UPDATED_LOOKING_FOR);
    }

    @Test
    @Transactional
    public void getAllProfilesByLookingForIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lookingFor is not null
        defaultProfileShouldBeFound("lookingFor.specified=true");

        // Get all the profileList where lookingFor is null
        defaultProfileShouldNotBeFound("lookingFor.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where purpose equals to DEFAULT_PURPOSE
        defaultProfileShouldBeFound("purpose.equals=" + DEFAULT_PURPOSE);

        // Get all the profileList where purpose equals to UPDATED_PURPOSE
        defaultProfileShouldNotBeFound("purpose.equals=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPurposeIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where purpose in DEFAULT_PURPOSE or UPDATED_PURPOSE
        defaultProfileShouldBeFound("purpose.in=" + DEFAULT_PURPOSE + "," + UPDATED_PURPOSE);

        // Get all the profileList where purpose equals to UPDATED_PURPOSE
        defaultProfileShouldNotBeFound("purpose.in=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPurposeIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where purpose is not null
        defaultProfileShouldBeFound("purpose.specified=true");

        // Get all the profileList where purpose is null
        defaultProfileShouldNotBeFound("purpose.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByPhysicalIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where physical equals to DEFAULT_PHYSICAL
        defaultProfileShouldBeFound("physical.equals=" + DEFAULT_PHYSICAL);

        // Get all the profileList where physical equals to UPDATED_PHYSICAL
        defaultProfileShouldNotBeFound("physical.equals=" + UPDATED_PHYSICAL);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhysicalIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where physical in DEFAULT_PHYSICAL or UPDATED_PHYSICAL
        defaultProfileShouldBeFound("physical.in=" + DEFAULT_PHYSICAL + "," + UPDATED_PHYSICAL);

        // Get all the profileList where physical equals to UPDATED_PHYSICAL
        defaultProfileShouldNotBeFound("physical.in=" + UPDATED_PHYSICAL);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhysicalIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where physical is not null
        defaultProfileShouldBeFound("physical.specified=true");

        // Get all the profileList where physical is null
        defaultProfileShouldNotBeFound("physical.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByReligionIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where religion equals to DEFAULT_RELIGION
        defaultProfileShouldBeFound("religion.equals=" + DEFAULT_RELIGION);

        // Get all the profileList where religion equals to UPDATED_RELIGION
        defaultProfileShouldNotBeFound("religion.equals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllProfilesByReligionIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where religion in DEFAULT_RELIGION or UPDATED_RELIGION
        defaultProfileShouldBeFound("religion.in=" + DEFAULT_RELIGION + "," + UPDATED_RELIGION);

        // Get all the profileList where religion equals to UPDATED_RELIGION
        defaultProfileShouldNotBeFound("religion.in=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllProfilesByReligionIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where religion is not null
        defaultProfileShouldBeFound("religion.specified=true");

        // Get all the profileList where religion is null
        defaultProfileShouldNotBeFound("religion.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByEthnicGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where ethnicGroup equals to DEFAULT_ETHNIC_GROUP
        defaultProfileShouldBeFound("ethnicGroup.equals=" + DEFAULT_ETHNIC_GROUP);

        // Get all the profileList where ethnicGroup equals to UPDATED_ETHNIC_GROUP
        defaultProfileShouldNotBeFound("ethnicGroup.equals=" + UPDATED_ETHNIC_GROUP);
    }

    @Test
    @Transactional
    public void getAllProfilesByEthnicGroupIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where ethnicGroup in DEFAULT_ETHNIC_GROUP or UPDATED_ETHNIC_GROUP
        defaultProfileShouldBeFound("ethnicGroup.in=" + DEFAULT_ETHNIC_GROUP + "," + UPDATED_ETHNIC_GROUP);

        // Get all the profileList where ethnicGroup equals to UPDATED_ETHNIC_GROUP
        defaultProfileShouldNotBeFound("ethnicGroup.in=" + UPDATED_ETHNIC_GROUP);
    }

    @Test
    @Transactional
    public void getAllProfilesByEthnicGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where ethnicGroup is not null
        defaultProfileShouldBeFound("ethnicGroup.specified=true");

        // Get all the profileList where ethnicGroup is null
        defaultProfileShouldNotBeFound("ethnicGroup.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByStudiesIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where studies equals to DEFAULT_STUDIES
        defaultProfileShouldBeFound("studies.equals=" + DEFAULT_STUDIES);

        // Get all the profileList where studies equals to UPDATED_STUDIES
        defaultProfileShouldNotBeFound("studies.equals=" + UPDATED_STUDIES);
    }

    @Test
    @Transactional
    public void getAllProfilesByStudiesIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where studies in DEFAULT_STUDIES or UPDATED_STUDIES
        defaultProfileShouldBeFound("studies.in=" + DEFAULT_STUDIES + "," + UPDATED_STUDIES);

        // Get all the profileList where studies equals to UPDATED_STUDIES
        defaultProfileShouldNotBeFound("studies.in=" + UPDATED_STUDIES);
    }

    @Test
    @Transactional
    public void getAllProfilesByStudiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where studies is not null
        defaultProfileShouldBeFound("studies.specified=true");

        // Get all the profileList where studies is null
        defaultProfileShouldNotBeFound("studies.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesBySibblingsIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where sibblings equals to DEFAULT_SIBBLINGS
        defaultProfileShouldBeFound("sibblings.equals=" + DEFAULT_SIBBLINGS);

        // Get all the profileList where sibblings equals to UPDATED_SIBBLINGS
        defaultProfileShouldNotBeFound("sibblings.equals=" + UPDATED_SIBBLINGS);
    }

    @Test
    @Transactional
    public void getAllProfilesBySibblingsIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where sibblings in DEFAULT_SIBBLINGS or UPDATED_SIBBLINGS
        defaultProfileShouldBeFound("sibblings.in=" + DEFAULT_SIBBLINGS + "," + UPDATED_SIBBLINGS);

        // Get all the profileList where sibblings equals to UPDATED_SIBBLINGS
        defaultProfileShouldNotBeFound("sibblings.in=" + UPDATED_SIBBLINGS);
    }

    @Test
    @Transactional
    public void getAllProfilesBySibblingsIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where sibblings is not null
        defaultProfileShouldBeFound("sibblings.specified=true");

        // Get all the profileList where sibblings is null
        defaultProfileShouldNotBeFound("sibblings.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesBySibblingsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where sibblings greater than or equals to DEFAULT_SIBBLINGS
        defaultProfileShouldBeFound("sibblings.greaterOrEqualThan=" + DEFAULT_SIBBLINGS);

        // Get all the profileList where sibblings greater than or equals to (DEFAULT_SIBBLINGS + 1)
        defaultProfileShouldNotBeFound("sibblings.greaterOrEqualThan=" + (DEFAULT_SIBBLINGS + 1));
    }

    @Test
    @Transactional
    public void getAllProfilesBySibblingsIsLessThanSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where sibblings less than or equals to DEFAULT_SIBBLINGS
        defaultProfileShouldNotBeFound("sibblings.lessThan=" + DEFAULT_SIBBLINGS);

        // Get all the profileList where sibblings less than or equals to (DEFAULT_SIBBLINGS + 1)
        defaultProfileShouldBeFound("sibblings.lessThan=" + (DEFAULT_SIBBLINGS + 1));
    }


    @Test
    @Transactional
    public void getAllProfilesByEyesIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where eyes equals to DEFAULT_EYES
        defaultProfileShouldBeFound("eyes.equals=" + DEFAULT_EYES);

        // Get all the profileList where eyes equals to UPDATED_EYES
        defaultProfileShouldNotBeFound("eyes.equals=" + UPDATED_EYES);
    }

    @Test
    @Transactional
    public void getAllProfilesByEyesIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where eyes in DEFAULT_EYES or UPDATED_EYES
        defaultProfileShouldBeFound("eyes.in=" + DEFAULT_EYES + "," + UPDATED_EYES);

        // Get all the profileList where eyes equals to UPDATED_EYES
        defaultProfileShouldNotBeFound("eyes.in=" + UPDATED_EYES);
    }

    @Test
    @Transactional
    public void getAllProfilesByEyesIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where eyes is not null
        defaultProfileShouldBeFound("eyes.specified=true");

        // Get all the profileList where eyes is null
        defaultProfileShouldNotBeFound("eyes.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesBySmokerIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where smoker equals to DEFAULT_SMOKER
        defaultProfileShouldBeFound("smoker.equals=" + DEFAULT_SMOKER);

        // Get all the profileList where smoker equals to UPDATED_SMOKER
        defaultProfileShouldNotBeFound("smoker.equals=" + UPDATED_SMOKER);
    }

    @Test
    @Transactional
    public void getAllProfilesBySmokerIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where smoker in DEFAULT_SMOKER or UPDATED_SMOKER
        defaultProfileShouldBeFound("smoker.in=" + DEFAULT_SMOKER + "," + UPDATED_SMOKER);

        // Get all the profileList where smoker equals to UPDATED_SMOKER
        defaultProfileShouldNotBeFound("smoker.in=" + UPDATED_SMOKER);
    }

    @Test
    @Transactional
    public void getAllProfilesBySmokerIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where smoker is not null
        defaultProfileShouldBeFound("smoker.specified=true");

        // Get all the profileList where smoker is null
        defaultProfileShouldNotBeFound("smoker.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByChildrenIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where children equals to DEFAULT_CHILDREN
        defaultProfileShouldBeFound("children.equals=" + DEFAULT_CHILDREN);

        // Get all the profileList where children equals to UPDATED_CHILDREN
        defaultProfileShouldNotBeFound("children.equals=" + UPDATED_CHILDREN);
    }

    @Test
    @Transactional
    public void getAllProfilesByChildrenIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where children in DEFAULT_CHILDREN or UPDATED_CHILDREN
        defaultProfileShouldBeFound("children.in=" + DEFAULT_CHILDREN + "," + UPDATED_CHILDREN);

        // Get all the profileList where children equals to UPDATED_CHILDREN
        defaultProfileShouldNotBeFound("children.in=" + UPDATED_CHILDREN);
    }

    @Test
    @Transactional
    public void getAllProfilesByChildrenIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where children is not null
        defaultProfileShouldBeFound("children.specified=true");

        // Get all the profileList where children is null
        defaultProfileShouldNotBeFound("children.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByFutureChildrenIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where futureChildren equals to DEFAULT_FUTURE_CHILDREN
        defaultProfileShouldBeFound("futureChildren.equals=" + DEFAULT_FUTURE_CHILDREN);

        // Get all the profileList where futureChildren equals to UPDATED_FUTURE_CHILDREN
        defaultProfileShouldNotBeFound("futureChildren.equals=" + UPDATED_FUTURE_CHILDREN);
    }

    @Test
    @Transactional
    public void getAllProfilesByFutureChildrenIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where futureChildren in DEFAULT_FUTURE_CHILDREN or UPDATED_FUTURE_CHILDREN
        defaultProfileShouldBeFound("futureChildren.in=" + DEFAULT_FUTURE_CHILDREN + "," + UPDATED_FUTURE_CHILDREN);

        // Get all the profileList where futureChildren equals to UPDATED_FUTURE_CHILDREN
        defaultProfileShouldNotBeFound("futureChildren.in=" + UPDATED_FUTURE_CHILDREN);
    }

    @Test
    @Transactional
    public void getAllProfilesByFutureChildrenIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where futureChildren is not null
        defaultProfileShouldBeFound("futureChildren.specified=true");

        // Get all the profileList where futureChildren is null
        defaultProfileShouldNotBeFound("futureChildren.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByPetIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where pet equals to DEFAULT_PET
        defaultProfileShouldBeFound("pet.equals=" + DEFAULT_PET);

        // Get all the profileList where pet equals to UPDATED_PET
        defaultProfileShouldNotBeFound("pet.equals=" + UPDATED_PET);
    }

    @Test
    @Transactional
    public void getAllProfilesByPetIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where pet in DEFAULT_PET or UPDATED_PET
        defaultProfileShouldBeFound("pet.in=" + DEFAULT_PET + "," + UPDATED_PET);

        // Get all the profileList where pet equals to UPDATED_PET
        defaultProfileShouldNotBeFound("pet.in=" + UPDATED_PET);
    }

    @Test
    @Transactional
    public void getAllProfilesByPetIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where pet is not null
        defaultProfileShouldBeFound("pet.specified=true");

        // Get all the profileList where pet is null
        defaultProfileShouldNotBeFound("pet.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        profile.setUser(user);
        profileRepository.saveAndFlush(profile);
        Long userId = user.getId();

        // Get all the profileList where user equals to userId
        defaultProfileShouldBeFound("userId.equals=" + userId);

        // Get all the profileList where user equals to userId + 1
        defaultProfileShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllProfilesByInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        Interest interest = InterestResourceIntTest.createEntity(em);
        em.persist(interest);
        em.flush();
        profile.addInterest(interest);
        profileRepository.saveAndFlush(profile);
        Long interestId = interest.getId();

        // Get all the profileList where interest equals to interestId
        defaultProfileShouldBeFound("interestId.equals=" + interestId);

        // Get all the profileList where interest equals to interestId + 1
        defaultProfileShouldNotBeFound("interestId.equals=" + (interestId + 1));
    }


    @Test
    @Transactional
    public void getAllProfilesByActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        Activity activity = ActivityResourceIntTest.createEntity(em);
        em.persist(activity);
        em.flush();
        profile.addActivity(activity);
        profileRepository.saveAndFlush(profile);
        Long activityId = activity.getId();

        // Get all the profileList where activity equals to activityId
        defaultProfileShouldBeFound("activityId.equals=" + activityId);

        // Get all the profileList where activity equals to activityId + 1
        defaultProfileShouldNotBeFound("activityId.equals=" + (activityId + 1));
    }


    @Test
    @Transactional
    public void getAllProfilesByCelebIsEqualToSomething() throws Exception {
        // Initialize the database
        Celeb celeb = CelebResourceIntTest.createEntity(em);
        em.persist(celeb);
        em.flush();
        profile.addCeleb(celeb);
        profileRepository.saveAndFlush(profile);
        Long celebId = celeb.getId();

        // Get all the profileList where celeb equals to celebId
        defaultProfileShouldBeFound("celebId.equals=" + celebId);

        // Get all the profileList where celeb equals to celebId + 1
        defaultProfileShouldNotBeFound("celebId.equals=" + (celebId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProfileShouldBeFound(String filter) throws Exception {
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO.toString())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].civilStatus").value(hasItem(DEFAULT_CIVIL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lookingFor").value(hasItem(DEFAULT_LOOKING_FOR.toString())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE.toString())))
            .andExpect(jsonPath("$.[*].physical").value(hasItem(DEFAULT_PHYSICAL.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].ethnicGroup").value(hasItem(DEFAULT_ETHNIC_GROUP.toString())))
            .andExpect(jsonPath("$.[*].studies").value(hasItem(DEFAULT_STUDIES.toString())))
            .andExpect(jsonPath("$.[*].sibblings").value(hasItem(DEFAULT_SIBBLINGS)))
            .andExpect(jsonPath("$.[*].eyes").value(hasItem(DEFAULT_EYES.toString())))
            .andExpect(jsonPath("$.[*].smoker").value(hasItem(DEFAULT_SMOKER.toString())))
            .andExpect(jsonPath("$.[*].children").value(hasItem(DEFAULT_CHILDREN.toString())))
            .andExpect(jsonPath("$.[*].futureChildren").value(hasItem(DEFAULT_FUTURE_CHILDREN.toString())))
            .andExpect(jsonPath("$.[*].pet").value(hasItem(DEFAULT_PET.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProfileShouldNotBeFound(String filter) throws Exception {
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .creationDate(UPDATED_CREATION_DATE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .gender(UPDATED_GENDER)
            .phone(UPDATED_PHONE)
            .bio(UPDATED_BIO)
            .birthdate(UPDATED_BIRTHDATE)
            .civilStatus(UPDATED_CIVIL_STATUS)
            .lookingFor(UPDATED_LOOKING_FOR)
            .purpose(UPDATED_PURPOSE)
            .physical(UPDATED_PHYSICAL)
            .religion(UPDATED_RELIGION)
            .ethnicGroup(UPDATED_ETHNIC_GROUP)
            .studies(UPDATED_STUDIES)
            .sibblings(UPDATED_SIBBLINGS)
            .eyes(UPDATED_EYES)
            .smoker(UPDATED_SMOKER)
            .children(UPDATED_CHILDREN)
            .futureChildren(UPDATED_FUTURE_CHILDREN)
            .pet(UPDATED_PET);
        ProfileDTO profileDTO = profileMapper.toDto(updatedProfile);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testProfile.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProfile.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testProfile.getBio()).isEqualTo(UPDATED_BIO);
        assertThat(testProfile.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testProfile.getCivilStatus()).isEqualTo(UPDATED_CIVIL_STATUS);
        assertThat(testProfile.getLookingFor()).isEqualTo(UPDATED_LOOKING_FOR);
        assertThat(testProfile.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testProfile.getPhysical()).isEqualTo(UPDATED_PHYSICAL);
        assertThat(testProfile.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testProfile.getEthnicGroup()).isEqualTo(UPDATED_ETHNIC_GROUP);
        assertThat(testProfile.getStudies()).isEqualTo(UPDATED_STUDIES);
        assertThat(testProfile.getSibblings()).isEqualTo(UPDATED_SIBBLINGS);
        assertThat(testProfile.getEyes()).isEqualTo(UPDATED_EYES);
        assertThat(testProfile.getSmoker()).isEqualTo(UPDATED_SMOKER);
        assertThat(testProfile.getChildren()).isEqualTo(UPDATED_CHILDREN);
        assertThat(testProfile.getFutureChildren()).isEqualTo(UPDATED_FUTURE_CHILDREN);
        assertThat(testProfile.isPet()).isEqualTo(UPDATED_PET);
    }

    @Test
    @Transactional
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Get the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profile.class);
        Profile profile1 = new Profile();
        profile1.setId(1L);
        Profile profile2 = new Profile();
        profile2.setId(profile1.getId());
        assertThat(profile1).isEqualTo(profile2);
        profile2.setId(2L);
        assertThat(profile1).isNotEqualTo(profile2);
        profile1.setId(null);
        assertThat(profile1).isNotEqualTo(profile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileDTO.class);
        ProfileDTO profileDTO1 = new ProfileDTO();
        profileDTO1.setId(1L);
        ProfileDTO profileDTO2 = new ProfileDTO();
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
        profileDTO2.setId(profileDTO1.getId());
        assertThat(profileDTO1).isEqualTo(profileDTO2);
        profileDTO2.setId(2L);
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
        profileDTO1.setId(null);
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(profileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(profileMapper.fromId(null)).isNull();
    }
}
