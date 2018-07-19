package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.BridgeUser;
import com.mycompany.myapp.service.BridgeUserService;
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
 * REST controller for managing BridgeUser.
 */
@RestController
@RequestMapping("/api")
public class BridgeUserResource {

    private final Logger log = LoggerFactory.getLogger(BridgeUserResource.class);

    private static final String ENTITY_NAME = "bridgeUser";

    private final BridgeUserService bridgeUserService;

    public BridgeUserResource(BridgeUserService bridgeUserService) {
        this.bridgeUserService = bridgeUserService;
    }

    /**
     * POST  /bridge-users : Create a new bridgeUser.
     *
     * @param bridgeUser the bridgeUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bridgeUser, or with status 400 (Bad Request) if the bridgeUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bridge-users")
    @Timed
    public ResponseEntity<BridgeUser> createBridgeUser(@RequestBody BridgeUser bridgeUser) throws URISyntaxException {
        log.debug("REST request to save BridgeUser : {}", bridgeUser);
        if (bridgeUser.getId() != null) {
            throw new BadRequestAlertException("A new bridgeUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BridgeUser result = bridgeUserService.save(bridgeUser);
        return ResponseEntity.created(new URI("/api/bridge-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bridge-users : Updates an existing bridgeUser.
     *
     * @param bridgeUser the bridgeUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bridgeUser,
     * or with status 400 (Bad Request) if the bridgeUser is not valid,
     * or with status 500 (Internal Server Error) if the bridgeUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bridge-users")
    @Timed
    public ResponseEntity<BridgeUser> updateBridgeUser(@RequestBody BridgeUser bridgeUser) throws URISyntaxException {
        log.debug("REST request to update BridgeUser : {}", bridgeUser);
        if (bridgeUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BridgeUser result = bridgeUserService.save(bridgeUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bridgeUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bridge-users : get all the bridgeUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bridgeUsers in body
     */
    @GetMapping("/bridge-users")
    @Timed
    public List<BridgeUser> getAllBridgeUsers() {
        log.debug("REST request to get all BridgeUsers");
        return bridgeUserService.findAll();
    }

    /**
     * GET  /bridge-users/:id : get the "id" bridgeUser.
     *
     * @param id the id of the bridgeUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bridgeUser, or with status 404 (Not Found)
     */
    @GetMapping("/bridge-users/{id}")
    @Timed
    public ResponseEntity<BridgeUser> getBridgeUser(@PathVariable Long id) {
        log.debug("REST request to get BridgeUser : {}", id);
        Optional<BridgeUser> bridgeUser = bridgeUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bridgeUser);
    }

    /**
     * DELETE  /bridge-users/:id : delete the "id" bridgeUser.
     *
     * @param id the id of the bridgeUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bridge-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteBridgeUser(@PathVariable Long id) {
        log.debug("REST request to delete BridgeUser : {}", id);
        bridgeUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
