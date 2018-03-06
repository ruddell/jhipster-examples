package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.FooDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Foo.
 */
public interface FooService {

    /**
     * Save a foo.
     *
     * @param fooDTO the entity to save
     * @return the persisted entity
     */
    FooDTO save(FooDTO fooDTO);

    /**
     * Get all the foos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FooDTO> findAll(Pageable pageable);


    /**
     * Get the "id" foo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FooDTO> findOne(Long id);

    /**
     * Delete the "id" foo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
