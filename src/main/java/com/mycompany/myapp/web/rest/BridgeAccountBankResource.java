package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.BridgeAccountBank;
import com.mycompany.myapp.service.BridgeAccountBankService;
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
 * REST controller for managing BridgeAccountBank.
 */
@RestController
@RequestMapping("/api")
public class BridgeAccountBankResource {

    private final Logger log = LoggerFactory.getLogger(BridgeAccountBankResource.class);

    private static final String ENTITY_NAME = "bridgeAccountBank";

    private final BridgeAccountBankService bridgeAccountBankService;

    public BridgeAccountBankResource(BridgeAccountBankService bridgeAccountBankService) {
        this.bridgeAccountBankService = bridgeAccountBankService;
    }

    /**
     * POST  /bridge-account-banks : Create a new bridgeAccountBank.
     *
     * @param bridgeAccountBank the bridgeAccountBank to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bridgeAccountBank, or with status 400 (Bad Request) if the bridgeAccountBank has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bridge-account-banks")
    @Timed
    public ResponseEntity<BridgeAccountBank> createBridgeAccountBank(@RequestBody BridgeAccountBank bridgeAccountBank) throws URISyntaxException {
        log.debug("REST request to save BridgeAccountBank : {}", bridgeAccountBank);
        if (bridgeAccountBank.getId() != null) {
            throw new BadRequestAlertException("A new bridgeAccountBank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BridgeAccountBank result = bridgeAccountBankService.save(bridgeAccountBank);
        return ResponseEntity.created(new URI("/api/bridge-account-banks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bridge-account-banks : Updates an existing bridgeAccountBank.
     *
     * @param bridgeAccountBank the bridgeAccountBank to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bridgeAccountBank,
     * or with status 400 (Bad Request) if the bridgeAccountBank is not valid,
     * or with status 500 (Internal Server Error) if the bridgeAccountBank couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bridge-account-banks")
    @Timed
    public ResponseEntity<BridgeAccountBank> updateBridgeAccountBank(@RequestBody BridgeAccountBank bridgeAccountBank) throws URISyntaxException {
        log.debug("REST request to update BridgeAccountBank : {}", bridgeAccountBank);
        if (bridgeAccountBank.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BridgeAccountBank result = bridgeAccountBankService.save(bridgeAccountBank);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bridgeAccountBank.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bridge-account-banks : get all the bridgeAccountBanks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bridgeAccountBanks in body
     */
    @GetMapping("/bridge-account-banks")
    @Timed
    public List<BridgeAccountBank> getAllBridgeAccountBanks() {
        log.debug("REST request to get all BridgeAccountBanks");
        return bridgeAccountBankService.findAll();
    }

    /**
     * GET  /bridge-account-banks/:id : get the "id" bridgeAccountBank.
     *
     * @param id the id of the bridgeAccountBank to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bridgeAccountBank, or with status 404 (Not Found)
     */
    @GetMapping("/bridge-account-banks/{id}")
    @Timed
    public ResponseEntity<BridgeAccountBank> getBridgeAccountBank(@PathVariable Long id) {
        log.debug("REST request to get BridgeAccountBank : {}", id);
        Optional<BridgeAccountBank> bridgeAccountBank = bridgeAccountBankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bridgeAccountBank);
    }

    /**
     * DELETE  /bridge-account-banks/:id : delete the "id" bridgeAccountBank.
     *
     * @param id the id of the bridgeAccountBank to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bridge-account-banks/{id}")
    @Timed
    public ResponseEntity<Void> deleteBridgeAccountBank(@PathVariable Long id) {
        log.debug("REST request to delete BridgeAccountBank : {}", id);
        bridgeAccountBankService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
