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

import com.mycompany.myapp.domain.Pledge;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.PledgeRepository;
import com.mycompany.myapp.service.dto.PledgeCriteria;
import com.mycompany.myapp.service.dto.PledgeDTO;
import com.mycompany.myapp.service.mapper.PledgeMapper;

/**
 * Service for executing complex queries for {@link Pledge} entities in the database.
 * The main input is a {@link PledgeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PledgeDTO} or a {@link Page} of {@link PledgeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PledgeQueryService extends QueryService<Pledge> {

    private final Logger log = LoggerFactory.getLogger(PledgeQueryService.class);

    private final PledgeRepository pledgeRepository;

    private final PledgeMapper pledgeMapper;

    public PledgeQueryService(PledgeRepository pledgeRepository, PledgeMapper pledgeMapper) {
        this.pledgeRepository = pledgeRepository;
        this.pledgeMapper = pledgeMapper;
    }

    /**
     * Return a {@link List} of {@link PledgeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PledgeDTO> findByCriteria(PledgeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Pledge> specification = createSpecification(criteria);
        return pledgeMapper.toDto(pledgeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PledgeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PledgeDTO> findByCriteria(PledgeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pledge> specification = createSpecification(criteria);
        return pledgeRepository.findAll(specification, page)
            .map(pledgeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PledgeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Pledge> specification = createSpecification(criteria);
        return pledgeRepository.count(specification);
    }

    /**
     * Function to convert PledgeCriteria to a {@link Specification}.
     */
    private Specification<Pledge> createSpecification(PledgeCriteria criteria) {
        Specification<Pledge> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Pledge_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Pledge_.name));
            }
        }
        return specification;
    }
}
