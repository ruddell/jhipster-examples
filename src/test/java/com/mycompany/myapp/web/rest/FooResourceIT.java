package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.AjskdlfjaskldfasApp;
import com.mycompany.myapp.GeneratedByJHipster;
import com.mycompany.myapp.domain.Foo;
import com.mycompany.myapp.repository.FooRepository;
import com.mycompany.myapp.service.FooQueryService;
import com.mycompany.myapp.service.FooService;
import com.mycompany.myapp.service.dto.FooCriteria;
import com.mycompany.myapp.service.dto.FooDTO;
import com.mycompany.myapp.service.mapper.FooMapper;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FooResource} REST controller.
 */
@SpringBootTest(classes = AjskdlfjaskldfasApp.class)
@AutoConfigureMockMvc
@WithMockUser
@GeneratedByJHipster
class FooResourceIT {
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
    private EntityManager em;

    @Autowired
    private MockMvc restFooMockMvc;

    private Foo foo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Foo createEntity(EntityManager em) {
        Foo foo = new Foo().name(DEFAULT_NAME);
        return foo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Foo createUpdatedEntity(EntityManager em) {
        Foo foo = new Foo().name(UPDATED_NAME);
        return foo;
    }

    @BeforeEach
    public void initTest() {
        foo = createEntity(em);
    }

    @Test
    @Transactional
    void createFoo() throws Exception {
        int databaseSizeBeforeCreate = fooRepository.findAll().size();
        // Create the Foo
        FooDTO fooDTO = fooMapper.toDto(foo);
        restFooMockMvc
            .perform(post("/api/foos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fooDTO)))
            .andExpect(status().isCreated());

        // Validate the Foo in the database
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeCreate + 1);
        Foo testFoo = fooList.get(fooList.size() - 1);
        assertThat(testFoo.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createFooWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fooRepository.findAll().size();

        // Create the Foo with an existing ID
        foo.setId(1L);
        FooDTO fooDTO = fooMapper.toDto(foo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFooMockMvc
            .perform(post("/api/foos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fooDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Foo in the database
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFoos() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get all the fooList
        restFooMockMvc
            .perform(get("/api/foos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getFoo() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get the foo
        restFooMockMvc
            .perform(get("/api/foos/{id}", foo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(foo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getFoosByIdFiltering() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        Long id = foo.getId();

        defaultFooShouldBeFound("id.equals=" + id);
        defaultFooShouldNotBeFound("id.notEquals=" + id);

        defaultFooShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFooShouldNotBeFound("id.greaterThan=" + id);

        defaultFooShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFooShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFoosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get all the fooList where name equals to DEFAULT_NAME
        defaultFooShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the fooList where name equals to UPDATED_NAME
        defaultFooShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFoosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get all the fooList where name not equals to DEFAULT_NAME
        defaultFooShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the fooList where name not equals to UPDATED_NAME
        defaultFooShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFoosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get all the fooList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFooShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the fooList where name equals to UPDATED_NAME
        defaultFooShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFoosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get all the fooList where name is not null
        defaultFooShouldBeFound("name.specified=true");

        // Get all the fooList where name is null
        defaultFooShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllFoosByNameContainsSomething() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get all the fooList where name contains DEFAULT_NAME
        defaultFooShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the fooList where name contains UPDATED_NAME
        defaultFooShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFoosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get all the fooList where name does not contain DEFAULT_NAME
        defaultFooShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the fooList where name does not contain UPDATED_NAME
        defaultFooShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFooShouldBeFound(String filter) throws Exception {
        restFooMockMvc
            .perform(get("/api/foos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restFooMockMvc
            .perform(get("/api/foos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFooShouldNotBeFound(String filter) throws Exception {
        restFooMockMvc
            .perform(get("/api/foos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFooMockMvc
            .perform(get("/api/foos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFoo() throws Exception {
        // Get the foo
        restFooMockMvc.perform(get("/api/foos/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateFoo() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        int databaseSizeBeforeUpdate = fooRepository.findAll().size();

        // Update the foo
        Foo updatedFoo = fooRepository.findById(foo.getId()).get();
        // Disconnect from session so that the updates on updatedFoo are not directly saved in db
        em.detach(updatedFoo);
        updatedFoo.name(UPDATED_NAME);
        FooDTO fooDTO = fooMapper.toDto(updatedFoo);

        restFooMockMvc
            .perform(put("/api/foos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fooDTO)))
            .andExpect(status().isOk());

        // Validate the Foo in the database
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeUpdate);
        Foo testFoo = fooList.get(fooList.size() - 1);
        assertThat(testFoo.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void updateNonExistingFoo() throws Exception {
        int databaseSizeBeforeUpdate = fooRepository.findAll().size();

        // Create the Foo
        FooDTO fooDTO = fooMapper.toDto(foo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFooMockMvc
            .perform(put("/api/foos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fooDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Foo in the database
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFooWithPatch() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        int databaseSizeBeforeUpdate = fooRepository.findAll().size();

        // Update the foo using partial update
        Foo partialUpdatedFoo = new Foo();
        partialUpdatedFoo.setId(foo.getId());

        partialUpdatedFoo.name(UPDATED_NAME);

        restFooMockMvc
            .perform(
                patch("/api/foos").contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoo))
            )
            .andExpect(status().isOk());

        // Validate the Foo in the database
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeUpdate);
        Foo testFoo = fooList.get(fooList.size() - 1);
        assertThat(testFoo.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateFooWithPatch() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        int databaseSizeBeforeUpdate = fooRepository.findAll().size();

        // Update the foo using partial update
        Foo partialUpdatedFoo = new Foo();
        partialUpdatedFoo.setId(foo.getId());

        partialUpdatedFoo.name(UPDATED_NAME);

        restFooMockMvc
            .perform(
                patch("/api/foos").contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoo))
            )
            .andExpect(status().isOk());

        // Validate the Foo in the database
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeUpdate);
        Foo testFoo = fooList.get(fooList.size() - 1);
        assertThat(testFoo.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void partialUpdateFooShouldThrown() throws Exception {
        // Update the foo without id should throw
        Foo partialUpdatedFoo = new Foo();

        restFooMockMvc
            .perform(
                patch("/api/foos").contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoo))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteFoo() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        int databaseSizeBeforeDelete = fooRepository.findAll().size();

        // Delete the foo
        restFooMockMvc.perform(delete("/api/foos/{id}", foo.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
