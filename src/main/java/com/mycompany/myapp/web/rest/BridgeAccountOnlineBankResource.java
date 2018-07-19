package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.BridgeAccountOnlineBank;
import com.mycompany.myapp.service.BridgeAccountOnlineBankService;
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
 * REST controller for managing BridgeAccountOnlineBank.
 */
@RestController
@RequestMapping("/api")
public class BridgeAccountOnlineBankResource {

    private final Logger log = LoggerFactory.getLogger(BridgeAccountOnlineBankResource.class);

    private static final String ENTITY_NAME = "bridgeAccountOnlineBank";

    private final BridgeAccountOnlineBankService bridgeAccountOnlineBankService;

    public BridgeAccountOnlineBankResource(BridgeAccountOnlineBankService bridgeAccountOnlineBankService) {
        this.bridgeAccountOnlineBankService = bridgeAccountOnlineBankService;
    }

    /**
     * POST  /bridge-account-online-banks : Create a new bridgeAccountOnlineBank.
     *
     * @param bridgeAccountOnlineBank the bridgeAccountOnlineBank to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bridgeAccountOnlineBank, or with status 400 (Bad Request) if the bridgeAccountOnlineBank has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bridge-account-online-banks")
    @Timed
    public ResponseEntity<BridgeAccountOnlineBank> createBridgeAccountOnlineBank(@RequestBody BridgeAccountOnlineBank bridgeAccountOnlineBank) throws URISyntaxException {
        log.debug("REST request to save BridgeAccountOnlineBank : {}", bridgeAccountOnlineBank);
        if (bridgeAccountOnlineBank.getId() != null) {
            throw new BadRequestAlertException("A new bridgeAccountOnlineBank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BridgeAccountOnlineBank result = bridgeAccountOnlineBankService.save(bridgeAccountOnlineBank);
        return ResponseEntity.created(new URI("/api/bridge-account-online-banks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bridge-account-online-banks : Updates an existing bridgeAccountOnlineBank.
     *
     * @param bridgeAccountOnlineBank the bridgeAccountOnlineBank to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bridgeAccountOnlineBank,
     * or with status 400 (Bad Request) if the bridgeAccountOnlineBank is not valid,
     * or with status 500 (Internal Server Error) if the bridgeAccountOnlineBank couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bridge-account-online-banks")
    @Timed
    public ResponseEntity<BridgeAccountOnlineBank> updateBridgeAccountOnlineBank(@RequestBody BridgeAccountOnlineBank bridgeAccountOnlineBank) throws URISyntaxException {
        log.debug("REST request to update BridgeAccountOnlineBank : {}", bridgeAccountOnlineBank);
        if (bridgeAccountOnlineBank.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BridgeAccountOnlineBank result = bridgeAccountOnlineBankService.save(bridgeAccountOnlineBank);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bridgeAccountOnlineBank.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bridge-account-online-banks : get all the bridgeAccountOnlineBanks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bridgeAccountOnlineBanks in body
     */
    @GetMapping("/bridge-account-online-banks")
    @Timed
    public List<BridgeAccountOnlineBank> getAllBridgeAccountOnlineBanks() {
        log.debug("REST request to get all BridgeAccountOnlineBanks");
        return bridgeAccountOnlineBankService.findAll();
    }

    /**
     * GET  /bridge-account-online-banks/:id : get the "id" bridgeAccountOnlineBank.
     *
     * @param id the id of the bridgeAccountOnlineBank to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bridgeAccountOnlineBank, or with status 404 (Not Found)
     */
    @GetMapping("/bridge-account-online-banks/{id}")
    @Timed
    public ResponseEntity<BridgeAccountOnlineBank> getBridgeAccountOnlineBank(@PathVariable Long id) {
        log.debug("REST request to get BridgeAccountOnlineBank : {}", id);
        Optional<BridgeAccountOnlineBank> bridgeAccountOnlineBank = bridgeAccountOnlineBankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bridgeAccountOnlineBank);
    }

    /**
     * DELETE  /bridge-account-online-banks/:id : delete the "id" bridgeAccountOnlineBank.
     *
     * @param id the id of the bridgeAccountOnlineBank to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bridge-account-online-banks/{id}")
    @Timed
    public ResponseEntity<Void> deleteBridgeAccountOnlineBank(@PathVariable Long id) {
        log.debug("REST request to delete BridgeAccountOnlineBank : {}", id);
        bridgeAccountOnlineBankService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
