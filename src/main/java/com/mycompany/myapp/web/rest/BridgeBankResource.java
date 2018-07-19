package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.BridgeBank;
import com.mycompany.myapp.service.BridgeBankService;
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
 * REST controller for managing BridgeBank.
 */
@RestController
@RequestMapping("/api")
public class BridgeBankResource {

    private final Logger log = LoggerFactory.getLogger(BridgeBankResource.class);

    private static final String ENTITY_NAME = "bridgeBank";

    private final BridgeBankService bridgeBankService;

    public BridgeBankResource(BridgeBankService bridgeBankService) {
        this.bridgeBankService = bridgeBankService;
    }

    /**
     * POST  /bridge-banks : Create a new bridgeBank.
     *
     * @param bridgeBank the bridgeBank to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bridgeBank, or with status 400 (Bad Request) if the bridgeBank has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bridge-banks")
    @Timed
    public ResponseEntity<BridgeBank> createBridgeBank(@RequestBody BridgeBank bridgeBank) throws URISyntaxException {
        log.debug("REST request to save BridgeBank : {}", bridgeBank);
        if (bridgeBank.getId() != null) {
            throw new BadRequestAlertException("A new bridgeBank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BridgeBank result = bridgeBankService.save(bridgeBank);
        return ResponseEntity.created(new URI("/api/bridge-banks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bridge-banks : Updates an existing bridgeBank.
     *
     * @param bridgeBank the bridgeBank to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bridgeBank,
     * or with status 400 (Bad Request) if the bridgeBank is not valid,
     * or with status 500 (Internal Server Error) if the bridgeBank couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bridge-banks")
    @Timed
    public ResponseEntity<BridgeBank> updateBridgeBank(@RequestBody BridgeBank bridgeBank) throws URISyntaxException {
        log.debug("REST request to update BridgeBank : {}", bridgeBank);
        if (bridgeBank.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BridgeBank result = bridgeBankService.save(bridgeBank);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bridgeBank.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bridge-banks : get all the bridgeBanks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bridgeBanks in body
     */
    @GetMapping("/bridge-banks")
    @Timed
    public List<BridgeBank> getAllBridgeBanks() {
        log.debug("REST request to get all BridgeBanks");
        return bridgeBankService.findAll();
    }

    /**
     * GET  /bridge-banks/:id : get the "id" bridgeBank.
     *
     * @param id the id of the bridgeBank to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bridgeBank, or with status 404 (Not Found)
     */
    @GetMapping("/bridge-banks/{id}")
    @Timed
    public ResponseEntity<BridgeBank> getBridgeBank(@PathVariable Long id) {
        log.debug("REST request to get BridgeBank : {}", id);
        Optional<BridgeBank> bridgeBank = bridgeBankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bridgeBank);
    }

    /**
     * DELETE  /bridge-banks/:id : delete the "id" bridgeBank.
     *
     * @param id the id of the bridgeBank to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bridge-banks/{id}")
    @Timed
    public ResponseEntity<Void> deleteBridgeBank(@PathVariable Long id) {
        log.debug("REST request to delete BridgeBank : {}", id);
        bridgeBankService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
