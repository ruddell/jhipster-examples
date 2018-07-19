package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BridgeBank;
import com.mycompany.myapp.repository.BridgeBankRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing BridgeBank.
 */
@Service
@Transactional
public class BridgeBankService {

    private final Logger log = LoggerFactory.getLogger(BridgeBankService.class);

    private final BridgeBankRepository bridgeBankRepository;

    public BridgeBankService(BridgeBankRepository bridgeBankRepository) {
        this.bridgeBankRepository = bridgeBankRepository;
    }

    /**
     * Save a bridgeBank.
     *
     * @param bridgeBank the entity to save
     * @return the persisted entity
     */
    public BridgeBank save(BridgeBank bridgeBank) {
        log.debug("Request to save BridgeBank : {}", bridgeBank);        return bridgeBankRepository.save(bridgeBank);
    }

    /**
     * Get all the bridgeBanks.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BridgeBank> findAll() {
        log.debug("Request to get all BridgeBanks");
        return bridgeBankRepository.findAll();
    }


    /**
     * Get one bridgeBank by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BridgeBank> findOne(Long id) {
        log.debug("Request to get BridgeBank : {}", id);
        return bridgeBankRepository.findById(id);
    }

    /**
     * Delete the bridgeBank by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BridgeBank : {}", id);
        bridgeBankRepository.deleteById(id);
    }
}
