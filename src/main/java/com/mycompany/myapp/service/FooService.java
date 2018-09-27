package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Foo;
import com.mycompany.myapp.repository.FooRepository;
import com.mycompany.myapp.service.dto.FooDTO;
import com.mycompany.myapp.service.mapper.FooMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Foo.
 */
@Service
@Transactional
public class FooService {

    private final Logger log = LoggerFactory.getLogger(FooService.class);

    private final FooRepository fooRepository;

    private final FooMapper fooMapper;

    public FooService(FooRepository fooRepository, FooMapper fooMapper) {
        this.fooRepository = fooRepository;
        this.fooMapper = fooMapper;
    }

    /**
     * Save a foo.
     *
     * @param fooDTO the entity to save
     * @return the persisted entity
     */
    public FooDTO save(FooDTO fooDTO) {
        log.debug("Request to save Foo : {}", fooDTO);
        Foo foo = fooMapper.toEntity(fooDTO);
        foo = fooRepository.save(foo);
        return fooMapper.toDto(foo);
    }

    /**
     * Get all the foos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FooDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Foos");
        return fooRepository.findAll(pageable)
            .map(fooMapper::toDto);
    }


    /**
     * Get one foo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<FooDTO> findOne(Long id) {
        log.debug("Request to get Foo : {}", id);
        return fooRepository.findById(id)
            .map(fooMapper::toDto);
    }

    /**
     * Delete the foo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Foo : {}", id);
        fooRepository.deleteById(id);
    }
}
