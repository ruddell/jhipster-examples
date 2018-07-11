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

import com.jhipsterpress2.web.domain.Party;
import com.jhipsterpress2.web.domain.*; // for static metamodels
import com.jhipsterpress2.web.repository.PartyRepository;
import com.jhipsterpress2.web.service.dto.PartyCriteria;

import com.jhipsterpress2.web.service.dto.PartyDTO;
import com.jhipsterpress2.web.service.mapper.PartyMapper;

/**
 * Service for executing complex queries for Party entities in the database.
 * The main input is a {@link PartyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PartyDTO} or a {@link Page} of {@link PartyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartyQueryService extends QueryService<Party> {

    private final Logger log = LoggerFactory.getLogger(PartyQueryService.class);

    private final PartyRepository partyRepository;

    private final PartyMapper partyMapper;

    public PartyQueryService(PartyRepository partyRepository, PartyMapper partyMapper) {
        this.partyRepository = partyRepository;
        this.partyMapper = partyMapper;
    }

    /**
     * Return a {@link List} of {@link PartyDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PartyDTO> findByCriteria(PartyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Party> specification = createSpecification(criteria);
        return partyMapper.toDto(partyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PartyDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PartyDTO> findByCriteria(PartyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Party> specification = createSpecification(criteria);
        return partyRepository.findAll(specification, page)
            .map(partyMapper::toDto);
    }

    /**
     * Function to convert PartyCriteria to a {@link Specification}
     */
    private Specification<Party> createSpecification(PartyCriteria criteria) {
        Specification<Party> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Party_.id));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Party_.creationDate));
            }
            if (criteria.getPartyname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPartyname(), Party_.partyname));
            }
            if (criteria.getPartydescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPartydescription(), Party_.partydescription));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Party_.isActive));
            }
            if (criteria.getBlogId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getBlogId(), Party_.blogs, Blog_.id));
            }
            if (criteria.getCommentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCommentId(), Party_.comments, Comment_.id));
            }
            if (criteria.getMessageId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMessageId(), Party_.messages, Message_.id));
            }
            if (criteria.getFollowedId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getFollowedId(), Party_.followeds, Follow_.id));
            }
            if (criteria.getFollowingId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getFollowingId(), Party_.followings, Follow_.id));
            }
            if (criteria.getBlockinguserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getBlockinguserId(), Party_.blockingusers, Blockeduser_.id));
            }
            if (criteria.getBlockeduserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getBlockeduserId(), Party_.blockedusers, Blockeduser_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), Party_.user, User_.id));
            }
            if (criteria.getAlbumId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAlbumId(), Party_.album, Album_.id));
            }
            if (criteria.getInterestId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getInterestId(), Party_.interests, Interest_.id));
            }
            if (criteria.getActivityId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getActivityId(), Party_.activities, Activity_.id));
            }
            if (criteria.getCelebId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCelebId(), Party_.celebs, Celeb_.id));
            }
        }
        return specification;
    }

}
