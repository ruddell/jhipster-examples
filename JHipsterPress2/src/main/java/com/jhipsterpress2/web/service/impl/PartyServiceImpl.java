package com.jhipsterpress2.web.service.impl;

import com.jhipsterpress2.web.service.PartyService;
import com.jhipsterpress2.web.domain.Party;
import com.jhipsterpress2.web.repository.PartyRepository;
import com.jhipsterpress2.web.service.dto.PartyDTO;
import com.jhipsterpress2.web.service.mapper.PartyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
/**
 * Service Implementation for managing Party.
 */
@Service
@Transactional
public class PartyServiceImpl implements PartyService {

    private final Logger log = LoggerFactory.getLogger(PartyServiceImpl.class);

    private final PartyRepository partyRepository;

    private final PartyMapper partyMapper;

    public PartyServiceImpl(PartyRepository partyRepository, PartyMapper partyMapper) {
        this.partyRepository = partyRepository;
        this.partyMapper = partyMapper;
    }

    /**
     * Save a party.
     *
     * @param partyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PartyDTO save(PartyDTO partyDTO) {
        log.debug("Request to save Party : {}", partyDTO);
        Party party = partyMapper.toEntity(partyDTO);
        party = partyRepository.save(party);
        return partyMapper.toDto(party);
    }

    /**
     * Get all the parties.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PartyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Parties");
        return partyRepository.findAll(pageable)
            .map(partyMapper::toDto);
    }



    /**
     *  get all the parties where Album is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PartyDTO> findAllWhereAlbumIsNull() {
        log.debug("Request to get all parties where Album is null");
        return StreamSupport
            .stream(partyRepository.findAll().spliterator(), false)
            .filter(party -> party.getAlbum() == null)
            .map(partyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one party by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PartyDTO> findOne(Long id) {
        log.debug("Request to get Party : {}", id);
        return partyRepository.findById(id)
            .map(partyMapper::toDto);
    }

    /**
     * Delete the party by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Party : {}", id);
        partyRepository.deleteById(id);
    }
}
