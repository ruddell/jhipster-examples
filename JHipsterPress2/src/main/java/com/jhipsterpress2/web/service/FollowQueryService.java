package com.jhipsterpress2.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.jhipsterpress2.web.domain.Follow;
import com.jhipsterpress2.web.domain.*; // for static metamodels
import com.jhipsterpress2.web.repository.FollowRepository;
import com.jhipsterpress2.web.service.dto.FollowCriteria;

import com.jhipsterpress2.web.service.dto.FollowDTO;
import com.jhipsterpress2.web.service.mapper.FollowMapper;

/**
 * Service for executing complex queries for Follow entities in the database.
 * The main input is a {@link FollowCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FollowDTO} or a {@link Page} of {@link FollowDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FollowQueryService extends QueryService<Follow> {

    private final Logger log = LoggerFactory.getLogger(FollowQueryService.class);

    private final FollowRepository followRepository;

    private final FollowMapper followMapper;

    public FollowQueryService(FollowRepository followRepository, FollowMapper followMapper) {
        this.followRepository = followRepository;
        this.followMapper = followMapper;
    }

    /**
     * Return a {@link List} of {@link FollowDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FollowDTO> findByCriteria(FollowCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Follow> specification = createSpecification(criteria);
        return followMapper.toDto(followRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FollowDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FollowDTO> findByCriteria(FollowCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Follow> specification = createSpecification(criteria);
        return followRepository.findAll(specification, page)
            .map(followMapper::toDto);
    }

    /**
     * Function to convert FollowCriteria to a {@link Specification}
     */
    private Specification<Follow> createSpecification(FollowCriteria criteria) {
        Specification<Follow> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Follow_.id));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Follow_.creationDate));
            }
            if (criteria.getFollowedId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getFollowedId(), Follow_.followed, Party_.id));
            }
            if (criteria.getFollowingId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getFollowingId(), Follow_.following, Party_.id));
            }
        }
        return specification;
    }

}
