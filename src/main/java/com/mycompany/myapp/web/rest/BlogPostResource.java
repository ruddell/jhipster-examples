package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.BlogPostService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.BlogPostDTO;
import com.mycompany.myapp.service.dto.BlogPostCriteria;
import com.mycompany.myapp.service.BlogPostQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BlogPost.
 */
@RestController
@RequestMapping("/api")
public class BlogPostResource {

    private final Logger log = LoggerFactory.getLogger(BlogPostResource.class);

    private static final String ENTITY_NAME = "blogPost";

    private final BlogPostService blogPostService;

    private final BlogPostQueryService blogPostQueryService;

    public BlogPostResource(BlogPostService blogPostService, BlogPostQueryService blogPostQueryService) {
        this.blogPostService = blogPostService;
        this.blogPostQueryService = blogPostQueryService;
    }

    /**
     * POST  /blog-posts : Create a new blogPost.
     *
     * @param blogPostDTO the blogPostDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new blogPostDTO, or with status 400 (Bad Request) if the blogPost has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/blog-posts")
    @Timed
    public ResponseEntity<BlogPostDTO> createBlogPost(@RequestBody BlogPostDTO blogPostDTO) throws URISyntaxException {
        log.debug("REST request to save BlogPost : {}", blogPostDTO);
        if (blogPostDTO.getId() != null) {
            throw new BadRequestAlertException("A new blogPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlogPostDTO result = blogPostService.save(blogPostDTO);
        return ResponseEntity.created(new URI("/api/blog-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blog-posts : Updates an existing blogPost.
     *
     * @param blogPostDTO the blogPostDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated blogPostDTO,
     * or with status 400 (Bad Request) if the blogPostDTO is not valid,
     * or with status 500 (Internal Server Error) if the blogPostDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/blog-posts")
    @Timed
    public ResponseEntity<BlogPostDTO> updateBlogPost(@RequestBody BlogPostDTO blogPostDTO) throws URISyntaxException {
        log.debug("REST request to update BlogPost : {}", blogPostDTO);
        if (blogPostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BlogPostDTO result = blogPostService.save(blogPostDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, blogPostDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /blog-posts : get all the blogPosts.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of blogPosts in body
     */
    @GetMapping("/blog-posts")
    @Timed
    public ResponseEntity<List<BlogPostDTO>> getAllBlogPosts(BlogPostCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BlogPosts by criteria: {}", criteria);
        Page<BlogPostDTO> page = blogPostQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blog-posts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /blog-posts/count : count all the blogPosts.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/blog-posts/count")
    @Timed
    public ResponseEntity<Long> countBlogPosts(BlogPostCriteria criteria) {
        log.debug("REST request to count BlogPosts by criteria: {}", criteria);
        return ResponseEntity.ok().body(blogPostQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /blog-posts/:id : get the "id" blogPost.
     *
     * @param id the id of the blogPostDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the blogPostDTO, or with status 404 (Not Found)
     */
    @GetMapping("/blog-posts/{id}")
    @Timed
    public ResponseEntity<BlogPostDTO> getBlogPost(@PathVariable Long id) {
        log.debug("REST request to get BlogPost : {}", id);
        Optional<BlogPostDTO> blogPostDTO = blogPostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blogPostDTO);
    }

    /**
     * DELETE  /blog-posts/:id : delete the "id" blogPost.
     *
     * @param id the id of the blogPostDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/blog-posts/{id}")
    @Timed
    public ResponseEntity<Void> deleteBlogPost(@PathVariable Long id) {
        log.debug("REST request to delete BlogPost : {}", id);
        blogPostService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
