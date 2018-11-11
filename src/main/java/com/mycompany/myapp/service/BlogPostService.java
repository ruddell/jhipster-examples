package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BlogPost;
import com.mycompany.myapp.repository.BlogPostRepository;
import com.mycompany.myapp.service.dto.BlogPostDTO;
import com.mycompany.myapp.service.mapper.BlogPostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing BlogPost.
 */
@Service
@Transactional
public class BlogPostService {

    private final Logger log = LoggerFactory.getLogger(BlogPostService.class);

    private final BlogPostRepository blogPostRepository;

    private final BlogPostMapper blogPostMapper;

    public BlogPostService(BlogPostRepository blogPostRepository, BlogPostMapper blogPostMapper) {
        this.blogPostRepository = blogPostRepository;
        this.blogPostMapper = blogPostMapper;
    }

    /**
     * Save a blogPost.
     *
     * @param blogPostDTO the entity to save
     * @return the persisted entity
     */
    public BlogPostDTO save(BlogPostDTO blogPostDTO) {
        log.debug("Request to save BlogPost : {}", blogPostDTO);

        BlogPost blogPost = blogPostMapper.toEntity(blogPostDTO);
        blogPost = blogPostRepository.save(blogPost);
        return blogPostMapper.toDto(blogPost);
    }

    /**
     * Get all the blogPosts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BlogPostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BlogPosts");
        return blogPostRepository.findAll(pageable)
            .map(blogPostMapper::toDto);
    }


    /**
     * Get one blogPost by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BlogPostDTO> findOne(Long id) {
        log.debug("Request to get BlogPost : {}", id);
        return blogPostRepository.findById(id)
            .map(blogPostMapper::toDto);
    }

    /**
     * Delete the blogPost by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BlogPost : {}", id);
        blogPostRepository.deleteById(id);
    }
}
