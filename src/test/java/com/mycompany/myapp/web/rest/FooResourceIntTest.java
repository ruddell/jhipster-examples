package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;

import com.mycompany.myapp.domain.Foo;
import com.mycompany.myapp.repository.FooRepository;
import com.mycompany.myapp.service.FooService;
import com.mycompany.myapp.service.dto.FooDTO;
import com.mycompany.myapp.service.mapper.FooMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.FooCriteria;
import com.mycompany.myapp.service.FooQueryService;

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
 * Test class for the FooResource REST controller.
 *
 * @see FooResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class FooResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FooRepository fooRepository;

    @Autowired
    private FooMapper fooMapper;

    @Autowired
    private FooService fooService;

    @Autowired
    private FooQueryService fooQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFooMockMvc;

    private Foo foo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FooResource fooResource = new FooResource(fooService, fooQueryService);
        this.restFooMockMvc = MockMvcBuilders.standaloneSetup(fooResource)
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
    public static Foo createEntity(EntityManager em) {
        Foo foo = new Foo()
            .name(DEFAULT_NAME);
        return foo;
    }

    @Before
    public void initTest() {
        foo = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoo() throws Exception {
        int databaseSizeBeforeCreate = fooRepository.findAll().size();

        // Create the Foo
        FooDTO fooDTO = fooMapper.toDto(foo);
        restFooMockMvc.perform(post("/api/foos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fooDTO)))
            .andExpect(status().isCreated());

        // Validate the Foo in the database
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeCreate + 1);
        Foo testFoo = fooList.get(fooList.size() - 1);
        assertThat(testFoo.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFooWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fooRepository.findAll().size();

        // Create the Foo with an existing ID
        foo.setId(1L);
        FooDTO fooDTO = fooMapper.toDto(foo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFooMockMvc.perform(post("/api/foos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fooDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Foo in the database
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFoos() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get all the fooList
        restFooMockMvc.perform(get("/api/foos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFoo() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get the foo
        restFooMockMvc.perform(get("/api/foos/{id}", foo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllFoosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get all the fooList where name equals to DEFAULT_NAME
        defaultFooShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the fooList where name equals to UPDATED_NAME
        defaultFooShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFoosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get all the fooList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFooShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the fooList where name equals to UPDATED_NAME
        defaultFooShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFoosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get all the fooList where name is not null
        defaultFooShouldBeFound("name.specified=true");

        // Get all the fooList where name is null
        defaultFooShouldNotBeFound("name.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFooShouldBeFound(String filter) throws Exception {
        restFooMockMvc.perform(get("/api/foos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFooShouldNotBeFound(String filter) throws Exception {
        restFooMockMvc.perform(get("/api/foos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingFoo() throws Exception {
        // Get the foo
        restFooMockMvc.perform(get("/api/foos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoo() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);
        int databaseSizeBeforeUpdate = fooRepository.findAll().size();

        // Update the foo
        Foo updatedFoo = fooRepository.findOne(foo.getId());
        // Disconnect from session so that the updates on updatedFoo are not directly saved in db
        em.detach(updatedFoo);
        updatedFoo
            .name(UPDATED_NAME);
        FooDTO fooDTO = fooMapper.toDto(updatedFoo);

        restFooMockMvc.perform(put("/api/foos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fooDTO)))
            .andExpect(status().isOk());

        // Validate the Foo in the database
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeUpdate);
        Foo testFoo = fooList.get(fooList.size() - 1);
        assertThat(testFoo.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFoo() throws Exception {
        int databaseSizeBeforeUpdate = fooRepository.findAll().size();

        // Create the Foo
        FooDTO fooDTO = fooMapper.toDto(foo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFooMockMvc.perform(put("/api/foos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fooDTO)))
            .andExpect(status().isCreated());

        // Validate the Foo in the database
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFoo() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);
        int databaseSizeBeforeDelete = fooRepository.findAll().size();

        // Get the foo
        restFooMockMvc.perform(delete("/api/foos/{id}", foo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Foo.class);
        Foo foo1 = new Foo();
        foo1.setId(1L);
        Foo foo2 = new Foo();
        foo2.setId(foo1.getId());
        assertThat(foo1).isEqualTo(foo2);
        foo2.setId(2L);
        assertThat(foo1).isNotEqualTo(foo2);
        foo1.setId(null);
        assertThat(foo1).isNotEqualTo(foo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FooDTO.class);
        FooDTO fooDTO1 = new FooDTO();
        fooDTO1.setId(1L);
        FooDTO fooDTO2 = new FooDTO();
        assertThat(fooDTO1).isNotEqualTo(fooDTO2);
        fooDTO2.setId(fooDTO1.getId());
        assertThat(fooDTO1).isEqualTo(fooDTO2);
        fooDTO2.setId(2L);
        assertThat(fooDTO1).isNotEqualTo(fooDTO2);
        fooDTO1.setId(null);
        assertThat(fooDTO1).isNotEqualTo(fooDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fooMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fooMapper.fromId(null)).isNull();
    }
}
