package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.BridgeCategory;
import com.mycompany.myapp.service.BridgeCategoryService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BridgeCategory.
 */
@RestController
@RequestMapping("/api")
public class BridgeCategoryResource {

    private final Logger log = LoggerFactory.getLogger(BridgeCategoryResource.class);

    private static final String ENTITY_NAME = "bridgeCategory";

    private final BridgeCategoryService bridgeCategoryService;

    public BridgeCategoryResource(BridgeCategoryService bridgeCategoryService) {
        this.bridgeCategoryService = bridgeCategoryService;
    }

    /**
     * POST  /bridge-categories : Create a new bridgeCategory.
     *
     * @param bridgeCategory the bridgeCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bridgeCategory, or with status 400 (Bad Request) if the bridgeCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bridge-categories")
    @Timed
    public ResponseEntity<BridgeCategory> createBridgeCategory(@RequestBody BridgeCategory bridgeCategory) throws URISyntaxException {
        log.debug("REST request to save BridgeCategory : {}", bridgeCategory);
        if (bridgeCategory.getId() != null) {
            throw new BadRequestAlertException("A new bridgeCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BridgeCategory result = bridgeCategoryService.save(bridgeCategory);
        return ResponseEntity.created(new URI("/api/bridge-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bridge-categories : Updates an existing bridgeCategory.
     *
     * @param bridgeCategory the bridgeCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bridgeCategory,
     * or with status 400 (Bad Request) if the bridgeCategory is not valid,
     * or with status 500 (Internal Server Error) if the bridgeCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bridge-categories")
    @Timed
    public ResponseEntity<BridgeCategory> updateBridgeCategory(@RequestBody BridgeCategory bridgeCategory) throws URISyntaxException {
        log.debug("REST request to update BridgeCategory : {}", bridgeCategory);
        if (bridgeCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BridgeCategory result = bridgeCategoryService.save(bridgeCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bridgeCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bridge-categories : get all the bridgeCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bridgeCategories in body
     */
    @GetMapping("/bridge-categories")
    @Timed
    public List<BridgeCategory> getAllBridgeCategories() {
        log.debug("REST request to get all BridgeCategories");
        return bridgeCategoryService.findAll();
    }

    /**
     * GET  /bridge-categories/:id : get the "id" bridgeCategory.
     *
     * @param id the id of the bridgeCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bridgeCategory, or with status 404 (Not Found)
     */
    @GetMapping("/bridge-categories/{id}")
    @Timed
    public ResponseEntity<BridgeCategory> getBridgeCategory(@PathVariable Long id) {
        log.debug("REST request to get BridgeCategory : {}", id);
        Optional<BridgeCategory> bridgeCategory = bridgeCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bridgeCategory);
    }

    /**
     * DELETE  /bridge-categories/:id : delete the "id" bridgeCategory.
     *
     * @param id the id of the bridgeCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bridge-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteBridgeCategory(@PathVariable Long id) {
        log.debug("REST request to delete BridgeCategory : {}", id);
        bridgeCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
