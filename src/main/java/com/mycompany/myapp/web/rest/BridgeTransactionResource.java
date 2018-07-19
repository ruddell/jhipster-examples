package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.BridgeTransaction;
import com.mycompany.myapp.service.BridgeTransactionService;
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
 * REST controller for managing BridgeTransaction.
 */
@RestController
@RequestMapping("/api")
public class BridgeTransactionResource {

    private final Logger log = LoggerFactory.getLogger(BridgeTransactionResource.class);

    private static final String ENTITY_NAME = "bridgeTransaction";

    private final BridgeTransactionService bridgeTransactionService;

    public BridgeTransactionResource(BridgeTransactionService bridgeTransactionService) {
        this.bridgeTransactionService = bridgeTransactionService;
    }

    /**
     * POST  /bridge-transactions : Create a new bridgeTransaction.
     *
     * @param bridgeTransaction the bridgeTransaction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bridgeTransaction, or with status 400 (Bad Request) if the bridgeTransaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bridge-transactions")
    @Timed
    public ResponseEntity<BridgeTransaction> createBridgeTransaction(@RequestBody BridgeTransaction bridgeTransaction) throws URISyntaxException {
        log.debug("REST request to save BridgeTransaction : {}", bridgeTransaction);
        if (bridgeTransaction.getId() != null) {
            throw new BadRequestAlertException("A new bridgeTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BridgeTransaction result = bridgeTransactionService.save(bridgeTransaction);
        return ResponseEntity.created(new URI("/api/bridge-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bridge-transactions : Updates an existing bridgeTransaction.
     *
     * @param bridgeTransaction the bridgeTransaction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bridgeTransaction,
     * or with status 400 (Bad Request) if the bridgeTransaction is not valid,
     * or with status 500 (Internal Server Error) if the bridgeTransaction couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bridge-transactions")
    @Timed
    public ResponseEntity<BridgeTransaction> updateBridgeTransaction(@RequestBody BridgeTransaction bridgeTransaction) throws URISyntaxException {
        log.debug("REST request to update BridgeTransaction : {}", bridgeTransaction);
        if (bridgeTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BridgeTransaction result = bridgeTransactionService.save(bridgeTransaction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bridgeTransaction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bridge-transactions : get all the bridgeTransactions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bridgeTransactions in body
     */
    @GetMapping("/bridge-transactions")
    @Timed
    public List<BridgeTransaction> getAllBridgeTransactions() {
        log.debug("REST request to get all BridgeTransactions");
        return bridgeTransactionService.findAll();
    }

    /**
     * GET  /bridge-transactions/:id : get the "id" bridgeTransaction.
     *
     * @param id the id of the bridgeTransaction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bridgeTransaction, or with status 404 (Not Found)
     */
    @GetMapping("/bridge-transactions/{id}")
    @Timed
    public ResponseEntity<BridgeTransaction> getBridgeTransaction(@PathVariable Long id) {
        log.debug("REST request to get BridgeTransaction : {}", id);
        Optional<BridgeTransaction> bridgeTransaction = bridgeTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bridgeTransaction);
    }

    /**
     * DELETE  /bridge-transactions/:id : delete the "id" bridgeTransaction.
     *
     * @param id the id of the bridgeTransaction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bridge-transactions/{id}")
    @Timed
    public ResponseEntity<Void> deleteBridgeTransaction(@PathVariable Long id) {
        log.debug("REST request to delete BridgeTransaction : {}", id);
        bridgeTransactionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
