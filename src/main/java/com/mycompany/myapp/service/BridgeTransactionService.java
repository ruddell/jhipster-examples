package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BridgeTransaction;
import com.mycompany.myapp.repository.BridgeTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing BridgeTransaction.
 */
@Service
@Transactional
public class BridgeTransactionService {

    private final Logger log = LoggerFactory.getLogger(BridgeTransactionService.class);

    private final BridgeTransactionRepository bridgeTransactionRepository;

    public BridgeTransactionService(BridgeTransactionRepository bridgeTransactionRepository) {
        this.bridgeTransactionRepository = bridgeTransactionRepository;
    }

    /**
     * Save a bridgeTransaction.
     *
     * @param bridgeTransaction the entity to save
     * @return the persisted entity
     */
    public BridgeTransaction save(BridgeTransaction bridgeTransaction) {
        log.debug("Request to save BridgeTransaction : {}", bridgeTransaction);        return bridgeTransactionRepository.save(bridgeTransaction);
    }

    /**
     * Get all the bridgeTransactions.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BridgeTransaction> findAll() {
        log.debug("Request to get all BridgeTransactions");
        return bridgeTransactionRepository.findAll();
    }


    /**
     * Get one bridgeTransaction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BridgeTransaction> findOne(Long id) {
        log.debug("Request to get BridgeTransaction : {}", id);
        return bridgeTransactionRepository.findById(id);
    }

    /**
     * Delete the bridgeTransaction by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BridgeTransaction : {}", id);
        bridgeTransactionRepository.deleteById(id);
    }
}
