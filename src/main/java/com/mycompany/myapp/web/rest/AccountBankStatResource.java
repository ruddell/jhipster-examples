package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.AccountBankStat;
import com.mycompany.myapp.service.AccountBankStatService;
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
 * REST controller for managing AccountBankStat.
 */
@RestController
@RequestMapping("/api")
public class AccountBankStatResource {

    private final Logger log = LoggerFactory.getLogger(AccountBankStatResource.class);

    private static final String ENTITY_NAME = "accountBankStat";

    private final AccountBankStatService accountBankStatService;

    public AccountBankStatResource(AccountBankStatService accountBankStatService) {
        this.accountBankStatService = accountBankStatService;
    }

    /**
     * POST  /account-bank-stats : Create a new accountBankStat.
     *
     * @param accountBankStat the accountBankStat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accountBankStat, or with status 400 (Bad Request) if the accountBankStat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/account-bank-stats")
    @Timed
    public ResponseEntity<AccountBankStat> createAccountBankStat(@RequestBody AccountBankStat accountBankStat) throws URISyntaxException {
        log.debug("REST request to save AccountBankStat : {}", accountBankStat);
        if (accountBankStat.getId() != null) {
            throw new BadRequestAlertException("A new accountBankStat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountBankStat result = accountBankStatService.save(accountBankStat);
        return ResponseEntity.created(new URI("/api/account-bank-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /account-bank-stats : Updates an existing accountBankStat.
     *
     * @param accountBankStat the accountBankStat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accountBankStat,
     * or with status 400 (Bad Request) if the accountBankStat is not valid,
     * or with status 500 (Internal Server Error) if the accountBankStat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/account-bank-stats")
    @Timed
    public ResponseEntity<AccountBankStat> updateAccountBankStat(@RequestBody AccountBankStat accountBankStat) throws URISyntaxException {
        log.debug("REST request to update AccountBankStat : {}", accountBankStat);
        if (accountBankStat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountBankStat result = accountBankStatService.save(accountBankStat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accountBankStat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /account-bank-stats : get all the accountBankStats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of accountBankStats in body
     */
    @GetMapping("/account-bank-stats")
    @Timed
    public List<AccountBankStat> getAllAccountBankStats() {
        log.debug("REST request to get all AccountBankStats");
        return accountBankStatService.findAll();
    }

    /**
     * GET  /account-bank-stats/:id : get the "id" accountBankStat.
     *
     * @param id the id of the accountBankStat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accountBankStat, or with status 404 (Not Found)
     */
    @GetMapping("/account-bank-stats/{id}")
    @Timed
    public ResponseEntity<AccountBankStat> getAccountBankStat(@PathVariable Long id) {
        log.debug("REST request to get AccountBankStat : {}", id);
        Optional<AccountBankStat> accountBankStat = accountBankStatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountBankStat);
    }

    /**
     * DELETE  /account-bank-stats/:id : delete the "id" accountBankStat.
     *
     * @param id the id of the accountBankStat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/account-bank-stats/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccountBankStat(@PathVariable Long id) {
        log.debug("REST request to delete AccountBankStat : {}", id);
        accountBankStatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
