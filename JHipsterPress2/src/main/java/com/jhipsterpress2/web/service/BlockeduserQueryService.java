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

import com.jhipsterpress2.web.domain.Blockeduser;
import com.jhipsterpress2.web.domain.*; // for static metamodels
import com.jhipsterpress2.web.repository.BlockeduserRepository;
import com.jhipsterpress2.web.service.dto.BlockeduserCriteria;

import com.jhipsterpress2.web.service.dto.BlockeduserDTO;
import com.jhipsterpress2.web.service.mapper.BlockeduserMapper;

/**
 * Service for executing complex queries for Blockeduser entities in the database.
 * The main input is a {@link BlockeduserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BlockeduserDTO} or a {@link Page} of {@link BlockeduserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BlockeduserQueryService extends QueryService<Blockeduser> {

    private final Logger log = LoggerFactory.getLogger(BlockeduserQueryService.class);

    private final BlockeduserRepository blockeduserRepository;

    private final BlockeduserMapper blockeduserMapper;

    public BlockeduserQueryService(BlockeduserRepository blockeduserRepository, BlockeduserMapper blockeduserMapper) {
        this.blockeduserRepository = blockeduserRepository;
        this.blockeduserMapper = blockeduserMapper;
    }

    /**
     * Return a {@link List} of {@link BlockeduserDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BlockeduserDTO> findByCriteria(BlockeduserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Blockeduser> specification = createSpecification(criteria);
        return blockeduserMapper.toDto(blockeduserRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BlockeduserDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BlockeduserDTO> findByCriteria(BlockeduserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Blockeduser> specification = createSpecification(criteria);
        return blockeduserRepository.findAll(specification, page)
            .map(blockeduserMapper::toDto);
    }

    /**
     * Function to convert BlockeduserCriteria to a {@link Specification}
     */
    private Specification<Blockeduser> createSpecification(BlockeduserCriteria criteria) {
        Specification<Blockeduser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Blockeduser_.id));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Blockeduser_.creationDate));
            }
            if (criteria.getBlockinguserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getBlockinguserId(), Blockeduser_.blockinguser, Party_.id));
            }
            if (criteria.getBlockeduserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getBlockeduserId(), Blockeduser_.blockeduser, Party_.id));
            }
        }
        return specification;
    }

}
