package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BridgeUser;
import com.mycompany.myapp.repository.BridgeUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing BridgeUser.
 */
@Service
@Transactional
public class BridgeUserService {

    private final Logger log = LoggerFactory.getLogger(BridgeUserService.class);

    private final BridgeUserRepository bridgeUserRepository;

    public BridgeUserService(BridgeUserRepository bridgeUserRepository) {
        this.bridgeUserRepository = bridgeUserRepository;
    }

    /**
     * Save a bridgeUser.
     *
     * @param bridgeUser the entity to save
     * @return the persisted entity
     */
    public BridgeUser save(BridgeUser bridgeUser) {
        log.debug("Request to save BridgeUser : {}", bridgeUser);        return bridgeUserRepository.save(bridgeUser);
    }

    /**
     * Get all the bridgeUsers.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BridgeUser> findAll() {
        log.debug("Request to get all BridgeUsers");
        return bridgeUserRepository.findAll();
    }


    /**
     * Get one bridgeUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BridgeUser> findOne(Long id) {
        log.debug("Request to get BridgeUser : {}", id);
        return bridgeUserRepository.findById(id);
    }

    /**
     * Delete the bridgeUser by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BridgeUser : {}", id);
        bridgeUserRepository.deleteById(id);
    }
}
