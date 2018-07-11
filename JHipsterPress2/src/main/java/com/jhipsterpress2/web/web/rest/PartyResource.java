package com.jhipsterpress2.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jhipsterpress2.web.service.PartyService;
import com.jhipsterpress2.web.web.rest.errors.BadRequestAlertException;
import com.jhipsterpress2.web.web.rest.util.HeaderUtil;
import com.jhipsterpress2.web.web.rest.util.PaginationUtil;
import com.jhipsterpress2.web.service.dto.PartyDTO;
import com.jhipsterpress2.web.service.dto.PartyCriteria;
import com.jhipsterpress2.web.service.PartyQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Party.
 */
@RestController
@RequestMapping("/api")
public class PartyResource {

    private final Logger log = LoggerFactory.getLogger(PartyResource.class);

    private static final String ENTITY_NAME = "party";

    private final PartyService partyService;

    private final PartyQueryService partyQueryService;

    public PartyResource(PartyService partyService, PartyQueryService partyQueryService) {
        this.partyService = partyService;
        this.partyQueryService = partyQueryService;
    }

    /**
     * POST  /parties : Create a new party.
     *
     * @param partyDTO the partyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partyDTO, or with status 400 (Bad Request) if the party has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parties")
    @Timed
    public ResponseEntity<PartyDTO> createParty(@Valid @RequestBody PartyDTO partyDTO) throws URISyntaxException {
        log.debug("REST request to save Party : {}", partyDTO);
        if (partyDTO.getId() != null) {
            throw new BadRequestAlertException("A new party cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyDTO result = partyService.save(partyDTO);
        return ResponseEntity.created(new URI("/api/parties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parties : Updates an existing party.
     *
     * @param partyDTO the partyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partyDTO,
     * or with status 400 (Bad Request) if the partyDTO is not valid,
     * or with status 500 (Internal Server Error) if the partyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parties")
    @Timed
    public ResponseEntity<PartyDTO> updateParty(@Valid @RequestBody PartyDTO partyDTO) throws URISyntaxException {
        log.debug("REST request to update Party : {}", partyDTO);
        if (partyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyDTO result = partyService.save(partyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, partyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parties : get all the parties.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of parties in body
     */
    @GetMapping("/parties")
    @Timed
    public ResponseEntity<List<PartyDTO>> getAllParties(PartyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Parties by criteria: {}", criteria);
        Page<PartyDTO> page = partyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parties");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /parties/:id : get the "id" party.
     *
     * @param id the id of the partyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/parties/{id}")
    @Timed
    public ResponseEntity<PartyDTO> getParty(@PathVariable Long id) {
        log.debug("REST request to get Party : {}", id);
        Optional<PartyDTO> partyDTO = partyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partyDTO);
    }

    /**
     * DELETE  /parties/:id : delete the "id" party.
     *
     * @param id the id of the partyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parties/{id}")
    @Timed
    public ResponseEntity<Void> deleteParty(@PathVariable Long id) {
        log.debug("REST request to delete Party : {}", id);
        partyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
