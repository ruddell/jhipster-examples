package com.jhipsterpress2.web.service.impl;

import com.jhipsterpress2.web.service.BlockeduserService;
import com.jhipsterpress2.web.domain.Blockeduser;
import com.jhipsterpress2.web.repository.BlockeduserRepository;
import com.jhipsterpress2.web.service.dto.BlockeduserDTO;
import com.jhipsterpress2.web.service.mapper.BlockeduserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Blockeduser.
 */
@Service
@Transactional
public class BlockeduserServiceImpl implements BlockeduserService {

    private final Logger log = LoggerFactory.getLogger(BlockeduserServiceImpl.class);

    private final BlockeduserRepository blockeduserRepository;

    private final BlockeduserMapper blockeduserMapper;

    public BlockeduserServiceImpl(BlockeduserRepository blockeduserRepository, BlockeduserMapper blockeduserMapper) {
        this.blockeduserRepository = blockeduserRepository;
        this.blockeduserMapper = blockeduserMapper;
    }

    /**
     * Save a blockeduser.
     *
     * @param blockeduserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BlockeduserDTO save(BlockeduserDTO blockeduserDTO) {
        log.debug("Request to save Blockeduser : {}", blockeduserDTO);
        Blockeduser blockeduser = blockeduserMapper.toEntity(blockeduserDTO);
        blockeduser = blockeduserRepository.save(blockeduser);
        return blockeduserMapper.toDto(blockeduser);
    }

    /**
     * Get all the blockedusers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BlockeduserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Blockedusers");
        return blockeduserRepository.findAll(pageable)
            .map(blockeduserMapper::toDto);
    }


    /**
     * Get one blockeduser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BlockeduserDTO> findOne(Long id) {
        log.debug("Request to get Blockeduser : {}", id);
        return blockeduserRepository.findById(id)
            .map(blockeduserMapper::toDto);
    }

    /**
     * Delete the blockeduser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Blockeduser : {}", id);
        blockeduserRepository.deleteById(id);
    }
}
