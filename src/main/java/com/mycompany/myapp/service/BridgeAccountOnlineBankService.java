package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BridgeAccountOnlineBank;
import com.mycompany.myapp.repository.BridgeAccountOnlineBankRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing BridgeAccountOnlineBank.
 */
@Service
@Transactional
public class BridgeAccountOnlineBankService {

    private final Logger log = LoggerFactory.getLogger(BridgeAccountOnlineBankService.class);

    private final BridgeAccountOnlineBankRepository bridgeAccountOnlineBankRepository;

    public BridgeAccountOnlineBankService(BridgeAccountOnlineBankRepository bridgeAccountOnlineBankRepository) {
        this.bridgeAccountOnlineBankRepository = bridgeAccountOnlineBankRepository;
    }

    /**
     * Save a bridgeAccountOnlineBank.
     *
     * @param bridgeAccountOnlineBank the entity to save
     * @return the persisted entity
     */
    public BridgeAccountOnlineBank save(BridgeAccountOnlineBank bridgeAccountOnlineBank) {
        log.debug("Request to save BridgeAccountOnlineBank : {}", bridgeAccountOnlineBank);        return bridgeAccountOnlineBankRepository.save(bridgeAccountOnlineBank);
    }

    /**
     * Get all the bridgeAccountOnlineBanks.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BridgeAccountOnlineBank> findAll() {
        log.debug("Request to get all BridgeAccountOnlineBanks");
        return bridgeAccountOnlineBankRepository.findAll();
    }


    /**
     * Get one bridgeAccountOnlineBank by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BridgeAccountOnlineBank> findOne(Long id) {
        log.debug("Request to get BridgeAccountOnlineBank : {}", id);
        return bridgeAccountOnlineBankRepository.findById(id);
    }

    /**
     * Delete the bridgeAccountOnlineBank by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BridgeAccountOnlineBank : {}", id);
        bridgeAccountOnlineBankRepository.deleteById(id);
    }
}
