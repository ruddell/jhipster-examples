package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BridgeCategory;
import com.mycompany.myapp.repository.BridgeCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing BridgeCategory.
 */
@Service
@Transactional
public class BridgeCategoryService {

    private final Logger log = LoggerFactory.getLogger(BridgeCategoryService.class);

    private final BridgeCategoryRepository bridgeCategoryRepository;

    public BridgeCategoryService(BridgeCategoryRepository bridgeCategoryRepository) {
        this.bridgeCategoryRepository = bridgeCategoryRepository;
    }

    /**
     * Save a bridgeCategory.
     *
     * @param bridgeCategory the entity to save
     * @return the persisted entity
     */
    public BridgeCategory save(BridgeCategory bridgeCategory) {
        log.debug("Request to save BridgeCategory : {}", bridgeCategory);        return bridgeCategoryRepository.save(bridgeCategory);
    }

    /**
     * Get all the bridgeCategories.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BridgeCategory> findAll() {
        log.debug("Request to get all BridgeCategories");
        return bridgeCategoryRepository.findAll();
    }


    /**
     * Get one bridgeCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BridgeCategory> findOne(Long id) {
        log.debug("Request to get BridgeCategory : {}", id);
        return bridgeCategoryRepository.findById(id);
    }

    /**
     * Delete the bridgeCategory by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BridgeCategory : {}", id);
        bridgeCategoryRepository.deleteById(id);
    }
}
