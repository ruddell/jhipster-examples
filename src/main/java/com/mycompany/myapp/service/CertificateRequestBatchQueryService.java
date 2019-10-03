package com.mycompany.myapp.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.CertificateRequestBatch;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.CertificateRequestBatchRepository;
import com.mycompany.myapp.service.dto.CertificateRequestBatchCriteria;

import com.mycompany.myapp.service.dto.CertificateRequestBatchDTO;
import com.mycompany.myapp.service.mapper.CertificateRequestBatchMapper;

/**
 * Service for executing complex queries for CertificateRequestBatch entities in the database.
 * The main input is a {@link CertificateRequestBatchCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CertificateRequestBatchDTO} or a {@link Page} of {@link CertificateRequestBatchDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CertificateRequestBatchQueryService extends QueryService<CertificateRequestBatch> {

    private final Logger log = LoggerFactory.getLogger(CertificateRequestBatchQueryService.class);


    private final CertificateRequestBatchRepository certificateRequestBatchRepository;

    private final CertificateRequestBatchMapper certificateRequestBatchMapper;

    public CertificateRequestBatchQueryService(CertificateRequestBatchRepository certificateRequestBatchRepository, CertificateRequestBatchMapper certificateRequestBatchMapper) {
        this.certificateRequestBatchRepository = certificateRequestBatchRepository;
        this.certificateRequestBatchMapper = certificateRequestBatchMapper;
    }

    /**
     * Return a {@link List} of {@link CertificateRequestBatchDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CertificateRequestBatchDTO> findByCriteria(CertificateRequestBatchCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CertificateRequestBatch> specification = createSpecification(criteria);
        return certificateRequestBatchMapper.toDto(certificateRequestBatchRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CertificateRequestBatchDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CertificateRequestBatchDTO> findByCriteria(CertificateRequestBatchCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CertificateRequestBatch> specification = createSpecification(criteria);
        final Page<CertificateRequestBatch> result = certificateRequestBatchRepository.findAll(specification, page);
        return result.map(certificateRequestBatchMapper::toDto);
    }

    /**
     * Function to convert CertificateRequestBatchCriteria to a {@link Specifications}
     */
    private Specifications<CertificateRequestBatch> createSpecification(CertificateRequestBatchCriteria criteria) {
        Specifications<CertificateRequestBatch> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CertificateRequestBatch_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CertificateRequestBatch_.name));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), CertificateRequestBatch_.creationDate));
            }
        }
        return specification;
    }

}
