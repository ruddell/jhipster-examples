package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BridgeAccountBank;
import com.mycompany.myapp.repository.BridgeAccountBankRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing BridgeAccountBank.
 */
@Service
@Transactional
public class BridgeAccountBankService {

    private final Logger log = LoggerFactory.getLogger(BridgeAccountBankService.class);

    private final BridgeAccountBankRepository bridgeAccountBankRepository;

    public BridgeAccountBankService(BridgeAccountBankRepository bridgeAccountBankRepository) {
        this.bridgeAccountBankRepository = bridgeAccountBankRepository;
    }

    /**
     * Save a bridgeAccountBank.
     *
     * @param bridgeAccountBank the entity to save
     * @return the persisted entity
     */
    public BridgeAccountBank save(BridgeAccountBank bridgeAccountBank) {
        log.debug("Request to save BridgeAccountBank : {}", bridgeAccountBank);        return bridgeAccountBankRepository.save(bridgeAccountBank);
    }

    /**
     * Get all the bridgeAccountBanks.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BridgeAccountBank> findAll() {
        log.debug("Request to get all BridgeAccountBanks");
        return bridgeAccountBankRepository.findAll();
    }


    /**
     * Get one bridgeAccountBank by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BridgeAccountBank> findOne(Long id) {
        log.debug("Request to get BridgeAccountBank : {}", id);
        return bridgeAccountBankRepository.findById(id);
    }

    /**
     * Delete the bridgeAccountBank by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BridgeAccountBank : {}", id);
        bridgeAccountBankRepository.deleteById(id);
    }
}
