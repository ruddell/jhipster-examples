package com.jhipsterpress2.web.service;

import com.jhipsterpress2.web.service.dto.PartyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Party.
 */
public interface PartyService {

    /**
     * Save a party.
     *
     * @param partyDTO the entity to save
     * @return the persisted entity
     */
    PartyDTO save(PartyDTO partyDTO);

    /**
     * Get all the parties.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PartyDTO> findAll(Pageable pageable);
    /**
     * Get all the PartyDTO where Album is null.
     *
     * @return the list of entities
     */
    List<PartyDTO> findAllWhereAlbumIsNull();


    /**
     * Get the "id" party.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PartyDTO> findOne(Long id);

    /**
     * Delete the "id" party.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
