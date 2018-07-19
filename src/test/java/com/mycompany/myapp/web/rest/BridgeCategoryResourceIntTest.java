package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FinaltestApp;

import com.mycompany.myapp.domain.BridgeCategory;
import com.mycompany.myapp.repository.BridgeCategoryRepository;
import com.mycompany.myapp.service.BridgeCategoryService;
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
 * Test class for the BridgeCategoryResource REST controller.
 *
 * @see BridgeCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinaltestApp.class)
public class BridgeCategoryResourceIntTest {

    private static final Long DEFAULT_ID_BRIDGE = 1L;
    private static final Long UPDATED_ID_BRIDGE = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BridgeCategoryRepository bridgeCategoryRepository;

    

    @Autowired
    private BridgeCategoryService bridgeCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBridgeCategoryMockMvc;

    private BridgeCategory bridgeCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BridgeCategoryResource bridgeCategoryResource = new BridgeCategoryResource(bridgeCategoryService);
        this.restBridgeCategoryMockMvc = MockMvcBuilders.standaloneSetup(bridgeCategoryResource)
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
    public static BridgeCategory createEntity(EntityManager em) {
        BridgeCategory bridgeCategory = new BridgeCategory()
            .idBridge(DEFAULT_ID_BRIDGE)
            .name(DEFAULT_NAME);
        return bridgeCategory;
    }

    @Before
    public void initTest() {
        bridgeCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createBridgeCategory() throws Exception {
        int databaseSizeBeforeCreate = bridgeCategoryRepository.findAll().size();

        // Create the BridgeCategory
        restBridgeCategoryMockMvc.perform(post("/api/bridge-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeCategory)))
            .andExpect(status().isCreated());

        // Validate the BridgeCategory in the database
        List<BridgeCategory> bridgeCategoryList = bridgeCategoryRepository.findAll();
        assertThat(bridgeCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        BridgeCategory testBridgeCategory = bridgeCategoryList.get(bridgeCategoryList.size() - 1);
        assertThat(testBridgeCategory.getIdBridge()).isEqualTo(DEFAULT_ID_BRIDGE);
        assertThat(testBridgeCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBridgeCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bridgeCategoryRepository.findAll().size();

        // Create the BridgeCategory with an existing ID
        bridgeCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBridgeCategoryMockMvc.perform(post("/api/bridge-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeCategory)))
            .andExpect(status().isBadRequest());

        // Validate the BridgeCategory in the database
        List<BridgeCategory> bridgeCategoryList = bridgeCategoryRepository.findAll();
        assertThat(bridgeCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBridgeCategories() throws Exception {
        // Initialize the database
        bridgeCategoryRepository.saveAndFlush(bridgeCategory);

        // Get all the bridgeCategoryList
        restBridgeCategoryMockMvc.perform(get("/api/bridge-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bridgeCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].idBridge").value(hasItem(DEFAULT_ID_BRIDGE.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getBridgeCategory() throws Exception {
        // Initialize the database
        bridgeCategoryRepository.saveAndFlush(bridgeCategory);

        // Get the bridgeCategory
        restBridgeCategoryMockMvc.perform(get("/api/bridge-categories/{id}", bridgeCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bridgeCategory.getId().intValue()))
            .andExpect(jsonPath("$.idBridge").value(DEFAULT_ID_BRIDGE.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBridgeCategory() throws Exception {
        // Get the bridgeCategory
        restBridgeCategoryMockMvc.perform(get("/api/bridge-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBridgeCategory() throws Exception {
        // Initialize the database
        bridgeCategoryService.save(bridgeCategory);

        int databaseSizeBeforeUpdate = bridgeCategoryRepository.findAll().size();

        // Update the bridgeCategory
        BridgeCategory updatedBridgeCategory = bridgeCategoryRepository.findById(bridgeCategory.getId()).get();
        // Disconnect from session so that the updates on updatedBridgeCategory are not directly saved in db
        em.detach(updatedBridgeCategory);
        updatedBridgeCategory
            .idBridge(UPDATED_ID_BRIDGE)
            .name(UPDATED_NAME);

        restBridgeCategoryMockMvc.perform(put("/api/bridge-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBridgeCategory)))
            .andExpect(status().isOk());

        // Validate the BridgeCategory in the database
        List<BridgeCategory> bridgeCategoryList = bridgeCategoryRepository.findAll();
        assertThat(bridgeCategoryList).hasSize(databaseSizeBeforeUpdate);
        BridgeCategory testBridgeCategory = bridgeCategoryList.get(bridgeCategoryList.size() - 1);
        assertThat(testBridgeCategory.getIdBridge()).isEqualTo(UPDATED_ID_BRIDGE);
        assertThat(testBridgeCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBridgeCategory() throws Exception {
        int databaseSizeBeforeUpdate = bridgeCategoryRepository.findAll().size();

        // Create the BridgeCategory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBridgeCategoryMockMvc.perform(put("/api/bridge-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridgeCategory)))
            .andExpect(status().isBadRequest());

        // Validate the BridgeCategory in the database
        List<BridgeCategory> bridgeCategoryList = bridgeCategoryRepository.findAll();
        assertThat(bridgeCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBridgeCategory() throws Exception {
        // Initialize the database
        bridgeCategoryService.save(bridgeCategory);

        int databaseSizeBeforeDelete = bridgeCategoryRepository.findAll().size();

        // Get the bridgeCategory
        restBridgeCategoryMockMvc.perform(delete("/api/bridge-categories/{id}", bridgeCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BridgeCategory> bridgeCategoryList = bridgeCategoryRepository.findAll();
        assertThat(bridgeCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeCategory.class);
        BridgeCategory bridgeCategory1 = new BridgeCategory();
        bridgeCategory1.setId(1L);
        BridgeCategory bridgeCategory2 = new BridgeCategory();
        bridgeCategory2.setId(bridgeCategory1.getId());
        assertThat(bridgeCategory1).isEqualTo(bridgeCategory2);
        bridgeCategory2.setId(2L);
        assertThat(bridgeCategory1).isNotEqualTo(bridgeCategory2);
        bridgeCategory1.setId(null);
        assertThat(bridgeCategory1).isNotEqualTo(bridgeCategory2);
    }
}
