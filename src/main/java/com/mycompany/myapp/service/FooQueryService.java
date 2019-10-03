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

import com.mycompany.myapp.domain.Foo;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.FooRepository;
import com.mycompany.myapp.service.dto.FooCriteria;

import com.mycompany.myapp.service.dto.FooDTO;
import com.mycompany.myapp.service.mapper.FooMapper;

/**
 * Service for executing complex queries for Foo entities in the database.
 * The main input is a {@link FooCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FooDTO} or a {@link Page} of {@link FooDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FooQueryService extends QueryService<Foo> {

    private final Logger log = LoggerFactory.getLogger(FooQueryService.class);


    private final FooRepository fooRepository;

    private final FooMapper fooMapper;

    public FooQueryService(FooRepository fooRepository, FooMapper fooMapper) {
        this.fooRepository = fooRepository;
        this.fooMapper = fooMapper;
    }

    /**
     * Return a {@link List} of {@link FooDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FooDTO> findByCriteria(FooCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Foo> specification = createSpecification(criteria);
        return fooMapper.toDto(fooRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FooDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FooDTO> findByCriteria(FooCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Foo> specification = createSpecification(criteria);
        final Page<Foo> result = fooRepository.findAll(specification, page);
        return result.map(fooMapper::toDto);
    }

    /**
     * Function to convert FooCriteria to a {@link Specifications}
     */
    private Specifications<Foo> createSpecification(FooCriteria criteria) {
        Specifications<Foo> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Foo_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Foo_.name));
            }
        }
        return specification;
    }

}
