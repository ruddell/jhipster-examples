package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.PledgeService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.PledgeDTO;
import com.mycompany.myapp.service.dto.PledgeCriteria;
import com.mycompany.myapp.service.PledgeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Pledge}.
 */
@RestController
@RequestMapping("/api")
public class PledgeResource {

    private final Logger log = LoggerFactory.getLogger(PledgeResource.class);

    private static final String ENTITY_NAME = "pledge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PledgeService pledgeService;

    private final PledgeQueryService pledgeQueryService;

    public PledgeResource(PledgeService pledgeService, PledgeQueryService pledgeQueryService) {
        this.pledgeService = pledgeService;
        this.pledgeQueryService = pledgeQueryService;
    }

    /**
     * {@code POST  /pledges} : Create a new pledge.
     *
     * @param pledgeDTO the pledgeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pledgeDTO, or with status {@code 400 (Bad Request)} if the pledge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pledges")
    public ResponseEntity<PledgeDTO> createPledge(@RequestBody PledgeDTO pledgeDTO) throws URISyntaxException {
        log.debug("REST request to save Pledge : {}", pledgeDTO);
        if (pledgeDTO.getId() != null) {
            throw new BadRequestAlertException("A new pledge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PledgeDTO result = pledgeService.save(pledgeDTO);
        return ResponseEntity.created(new URI("/api/pledges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pledges} : Updates an existing pledge.
     *
     * @param pledgeDTO the pledgeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pledgeDTO,
     * or with status {@code 400 (Bad Request)} if the pledgeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pledgeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pledges")
    public ResponseEntity<PledgeDTO> updatePledge(@RequestBody PledgeDTO pledgeDTO) throws URISyntaxException {
        log.debug("REST request to update Pledge : {}", pledgeDTO);
        if (pledgeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PledgeDTO result = pledgeService.save(pledgeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pledgeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pledges} : get all the pledges.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pledges in body.
     */
    @GetMapping("/pledges")
    public ResponseEntity<List<PledgeDTO>> getAllPledges(PledgeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Pledges by criteria: {}", criteria);
        Page<PledgeDTO> page = pledgeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /pledges/count} : count all the pledges.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/pledges/count")
    public ResponseEntity<Long> countPledges(PledgeCriteria criteria) {
        log.debug("REST request to count Pledges by criteria: {}", criteria);
        return ResponseEntity.ok().body(pledgeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pledges/:id} : get the "id" pledge.
     *
     * @param id the id of the pledgeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pledgeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pledges/{id}")
    public ResponseEntity<PledgeDTO> getPledge(@PathVariable Long id) {
        log.debug("REST request to get Pledge : {}", id);
        Optional<PledgeDTO> pledgeDTO = pledgeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pledgeDTO);
    }

    /**
     * {@code DELETE  /pledges/:id} : delete the "id" pledge.
     *
     * @param id the id of the pledgeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pledges/{id}")
    public ResponseEntity<Void> deletePledge(@PathVariable Long id) {
        log.debug("REST request to delete Pledge : {}", id);
        pledgeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
