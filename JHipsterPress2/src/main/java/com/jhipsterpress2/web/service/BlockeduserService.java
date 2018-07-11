package com.jhipsterpress2.web.service;

import com.jhipsterpress2.web.service.dto.BlockeduserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Blockeduser.
 */
public interface BlockeduserService {

    /**
     * Save a blockeduser.
     *
     * @param blockeduserDTO the entity to save
     * @return the persisted entity
     */
    BlockeduserDTO save(BlockeduserDTO blockeduserDTO);

    /**
     * Get all the blockedusers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BlockeduserDTO> findAll(Pageable pageable);


    /**
     * Get the "id" blockeduser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BlockeduserDTO> findOne(Long id);

    /**
     * Delete the "id" blockeduser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
