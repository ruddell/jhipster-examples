package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Pledge;
import com.mycompany.myapp.repository.PledgeRepository;
import com.mycompany.myapp.service.dto.PledgeDTO;
import com.mycompany.myapp.service.mapper.PledgeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Pledge}.
 */
@Service
@Transactional
public class PledgeService {

    private final Logger log = LoggerFactory.getLogger(PledgeService.class);

    private final PledgeRepository pledgeRepository;

    private final PledgeMapper pledgeMapper;

    public PledgeService(PledgeRepository pledgeRepository, PledgeMapper pledgeMapper) {
        this.pledgeRepository = pledgeRepository;
        this.pledgeMapper = pledgeMapper;
    }

    /**
     * Save a pledge.
     *
     * @param pledgeDTO the entity to save.
     * @return the persisted entity.
     */
    public PledgeDTO save(PledgeDTO pledgeDTO) {
        log.debug("Request to save Pledge : {}", pledgeDTO);
        Pledge pledge = pledgeMapper.toEntity(pledgeDTO);
        pledge = pledgeRepository.save(pledge);
        return pledgeMapper.toDto(pledge);
    }

    /**
     * Get all the pledges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PledgeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pledges");
        return pledgeRepository.findAll(pageable)
            .map(pledgeMapper::toDto);
    }


    /**
     * Get one pledge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PledgeDTO> findOne(Long id) {
        log.debug("Request to get Pledge : {}", id);
        return pledgeRepository.findById(id)
            .map(pledgeMapper::toDto);
    }

    /**
     * Delete the pledge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pledge : {}", id);
        pledgeRepository.deleteById(id);
    }
}
