package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.BlogPost;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.BlogPostRepository;
import com.mycompany.myapp.service.dto.BlogPostCriteria;
import com.mycompany.myapp.service.dto.BlogPostDTO;
import com.mycompany.myapp.service.mapper.BlogPostMapper;

/**
 * Service for executing complex queries for BlogPost entities in the database.
 * The main input is a {@link BlogPostCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BlogPostDTO} or a {@link Page} of {@link BlogPostDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BlogPostQueryService extends QueryService<BlogPost> {

    private final Logger log = LoggerFactory.getLogger(BlogPostQueryService.class);

    private final BlogPostRepository blogPostRepository;

    private final BlogPostMapper blogPostMapper;

    public BlogPostQueryService(BlogPostRepository blogPostRepository, BlogPostMapper blogPostMapper) {
        this.blogPostRepository = blogPostRepository;
        this.blogPostMapper = blogPostMapper;
    }

    /**
     * Return a {@link List} of {@link BlogPostDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BlogPostDTO> findByCriteria(BlogPostCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BlogPost> specification = createSpecification(criteria);
        return blogPostMapper.toDto(blogPostRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BlogPostDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BlogPostDTO> findByCriteria(BlogPostCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BlogPost> specification = createSpecification(criteria);
        return blogPostRepository.findAll(specification, page)
            .map(blogPostMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BlogPostCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BlogPost> specification = createSpecification(criteria);
        return blogPostRepository.count(specification);
    }

    /**
     * Function to convert BlogPostCriteria to a {@link Specification}
     */
    private Specification<BlogPost> createSpecification(BlogPostCriteria criteria) {
        Specification<BlogPost> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BlogPost_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BlogPost_.name));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(BlogPost_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
