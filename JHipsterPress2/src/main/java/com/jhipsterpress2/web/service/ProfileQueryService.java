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

import com.jhipsterpress2.web.domain.Profile;
import com.jhipsterpress2.web.domain.*; // for static metamodels
import com.jhipsterpress2.web.repository.ProfileRepository;
import com.jhipsterpress2.web.service.dto.ProfileCriteria;

import com.jhipsterpress2.web.service.dto.ProfileDTO;
import com.jhipsterpress2.web.service.mapper.ProfileMapper;

/**
 * Service for executing complex queries for Profile entities in the database.
 * The main input is a {@link ProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProfileDTO} or a {@link Page} of {@link ProfileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProfileQueryService extends QueryService<Profile> {

    private final Logger log = LoggerFactory.getLogger(ProfileQueryService.class);

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    public ProfileQueryService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    /**
     * Return a {@link List} of {@link ProfileDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProfileDTO> findByCriteria(ProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Profile> specification = createSpecification(criteria);
        return profileMapper.toDto(profileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProfileDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfileDTO> findByCriteria(ProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Profile> specification = createSpecification(criteria);
        return profileRepository.findAll(specification, page)
            .map(profileMapper::toDto);
    }

    /**
     * Function to convert ProfileCriteria to a {@link Specification}
     */
    private Specification<Profile> createSpecification(ProfileCriteria criteria) {
        Specification<Profile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Profile_.id));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Profile_.creationDate));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), Profile_.gender));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Profile_.phone));
            }
            if (criteria.getBio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBio(), Profile_.bio));
            }
            if (criteria.getBirthdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthdate(), Profile_.birthdate));
            }
            if (criteria.getCivilStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getCivilStatus(), Profile_.civilStatus));
            }
            if (criteria.getLookingFor() != null) {
                specification = specification.and(buildSpecification(criteria.getLookingFor(), Profile_.lookingFor));
            }
            if (criteria.getPurpose() != null) {
                specification = specification.and(buildSpecification(criteria.getPurpose(), Profile_.purpose));
            }
            if (criteria.getPhysical() != null) {
                specification = specification.and(buildSpecification(criteria.getPhysical(), Profile_.physical));
            }
            if (criteria.getReligion() != null) {
                specification = specification.and(buildSpecification(criteria.getReligion(), Profile_.religion));
            }
            if (criteria.getEthnicGroup() != null) {
                specification = specification.and(buildSpecification(criteria.getEthnicGroup(), Profile_.ethnicGroup));
            }
            if (criteria.getStudies() != null) {
                specification = specification.and(buildSpecification(criteria.getStudies(), Profile_.studies));
            }
            if (criteria.getSibblings() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSibblings(), Profile_.sibblings));
            }
            if (criteria.getEyes() != null) {
                specification = specification.and(buildSpecification(criteria.getEyes(), Profile_.eyes));
            }
            if (criteria.getSmoker() != null) {
                specification = specification.and(buildSpecification(criteria.getSmoker(), Profile_.smoker));
            }
            if (criteria.getChildren() != null) {
                specification = specification.and(buildSpecification(criteria.getChildren(), Profile_.children));
            }
            if (criteria.getFutureChildren() != null) {
                specification = specification.and(buildSpecification(criteria.getFutureChildren(), Profile_.futureChildren));
            }
            if (criteria.getPet() != null) {
                specification = specification.and(buildSpecification(criteria.getPet(), Profile_.pet));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), Profile_.user, User_.id));
            }
            if (criteria.getInterestId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getInterestId(), Profile_.interests, Interest_.id));
            }
            if (criteria.getActivityId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getActivityId(), Profile_.activities, Activity_.id));
            }
            if (criteria.getCelebId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCelebId(), Profile_.celebs, Celeb_.id));
            }
        }
        return specification;
    }

}
