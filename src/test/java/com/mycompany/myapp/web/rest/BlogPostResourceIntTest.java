package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;

import com.mycompany.myapp.domain.BlogPost;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.BlogPostRepository;
import com.mycompany.myapp.service.BlogPostService;
import com.mycompany.myapp.service.dto.BlogPostDTO;
import com.mycompany.myapp.service.mapper.BlogPostMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.BlogPostCriteria;
import com.mycompany.myapp.service.BlogPostQueryService;

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
 * Test class for the BlogPostResource REST controller.
 *
 * @see BlogPostResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class BlogPostResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private BlogPostMapper blogPostMapper;

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private BlogPostQueryService blogPostQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBlogPostMockMvc;

    private BlogPost blogPost;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlogPostResource blogPostResource = new BlogPostResource(blogPostService, blogPostQueryService);
        this.restBlogPostMockMvc = MockMvcBuilders.standaloneSetup(blogPostResource)
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
    public static BlogPost createEntity(EntityManager em) {
        BlogPost blogPost = new BlogPost()
            .name(DEFAULT_NAME);
        return blogPost;
    }

    @Before
    public void initTest() {
        blogPost = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlogPost() throws Exception {
        int databaseSizeBeforeCreate = blogPostRepository.findAll().size();

        // Create the BlogPost
        BlogPostDTO blogPostDTO = blogPostMapper.toDto(blogPost);
        restBlogPostMockMvc.perform(post("/api/blog-posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogPostDTO)))
            .andExpect(status().isCreated());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeCreate + 1);
        BlogPost testBlogPost = blogPostList.get(blogPostList.size() - 1);
        assertThat(testBlogPost.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBlogPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blogPostRepository.findAll().size();

        // Create the BlogPost with an existing ID
        blogPost.setId(1L);
        BlogPostDTO blogPostDTO = blogPostMapper.toDto(blogPost);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlogPostMockMvc.perform(post("/api/blog-posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogPostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBlogPosts() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        // Get all the blogPostList
        restBlogPostMockMvc.perform(get("/api/blog-posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blogPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getBlogPost() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        // Get the blogPost
        restBlogPostMockMvc.perform(get("/api/blog-posts/{id}", blogPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blogPost.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllBlogPostsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        // Get all the blogPostList where name equals to DEFAULT_NAME
        defaultBlogPostShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the blogPostList where name equals to UPDATED_NAME
        defaultBlogPostShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBlogPostsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        // Get all the blogPostList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBlogPostShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the blogPostList where name equals to UPDATED_NAME
        defaultBlogPostShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBlogPostsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        // Get all the blogPostList where name is not null
        defaultBlogPostShouldBeFound("name.specified=true");

        // Get all the blogPostList where name is null
        defaultBlogPostShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllBlogPostsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        blogPost.setUser(user);
        blogPostRepository.saveAndFlush(blogPost);
        Long userId = user.getId();

        // Get all the blogPostList where user equals to userId
        defaultBlogPostShouldBeFound("userId.equals=" + userId);

        // Get all the blogPostList where user equals to userId + 1
        defaultBlogPostShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBlogPostShouldBeFound(String filter) throws Exception {
        restBlogPostMockMvc.perform(get("/api/blog-posts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blogPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));

        // Check, that the count call also returns 1
        restBlogPostMockMvc.perform(get("/api/blog-posts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBlogPostShouldNotBeFound(String filter) throws Exception {
        restBlogPostMockMvc.perform(get("/api/blog-posts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBlogPostMockMvc.perform(get("/api/blog-posts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBlogPost() throws Exception {
        // Get the blogPost
        restBlogPostMockMvc.perform(get("/api/blog-posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlogPost() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        int databaseSizeBeforeUpdate = blogPostRepository.findAll().size();

        // Update the blogPost
        BlogPost updatedBlogPost = blogPostRepository.findById(blogPost.getId()).get();
        // Disconnect from session so that the updates on updatedBlogPost are not directly saved in db
        em.detach(updatedBlogPost);
        updatedBlogPost
            .name(UPDATED_NAME);
        BlogPostDTO blogPostDTO = blogPostMapper.toDto(updatedBlogPost);

        restBlogPostMockMvc.perform(put("/api/blog-posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogPostDTO)))
            .andExpect(status().isOk());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
        BlogPost testBlogPost = blogPostList.get(blogPostList.size() - 1);
        assertThat(testBlogPost.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBlogPost() throws Exception {
        int databaseSizeBeforeUpdate = blogPostRepository.findAll().size();

        // Create the BlogPost
        BlogPostDTO blogPostDTO = blogPostMapper.toDto(blogPost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogPostMockMvc.perform(put("/api/blog-posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogPostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBlogPost() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        int databaseSizeBeforeDelete = blogPostRepository.findAll().size();

        // Get the blogPost
        restBlogPostMockMvc.perform(delete("/api/blog-posts/{id}", blogPost.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlogPost.class);
        BlogPost blogPost1 = new BlogPost();
        blogPost1.setId(1L);
        BlogPost blogPost2 = new BlogPost();
        blogPost2.setId(blogPost1.getId());
        assertThat(blogPost1).isEqualTo(blogPost2);
        blogPost2.setId(2L);
        assertThat(blogPost1).isNotEqualTo(blogPost2);
        blogPost1.setId(null);
        assertThat(blogPost1).isNotEqualTo(blogPost2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlogPostDTO.class);
        BlogPostDTO blogPostDTO1 = new BlogPostDTO();
        blogPostDTO1.setId(1L);
        BlogPostDTO blogPostDTO2 = new BlogPostDTO();
        assertThat(blogPostDTO1).isNotEqualTo(blogPostDTO2);
        blogPostDTO2.setId(blogPostDTO1.getId());
        assertThat(blogPostDTO1).isEqualTo(blogPostDTO2);
        blogPostDTO2.setId(2L);
        assertThat(blogPostDTO1).isNotEqualTo(blogPostDTO2);
        blogPostDTO1.setId(null);
        assertThat(blogPostDTO1).isNotEqualTo(blogPostDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(blogPostMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(blogPostMapper.fromId(null)).isNull();
    }
}
