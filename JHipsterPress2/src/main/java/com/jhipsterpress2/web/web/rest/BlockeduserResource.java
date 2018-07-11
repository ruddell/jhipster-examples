package com.jhipsterpress2.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jhipsterpress2.web.service.BlockeduserService;
import com.jhipsterpress2.web.web.rest.errors.BadRequestAlertException;
import com.jhipsterpress2.web.web.rest.util.HeaderUtil;
import com.jhipsterpress2.web.web.rest.util.PaginationUtil;
import com.jhipsterpress2.web.service.dto.BlockeduserDTO;
import com.jhipsterpress2.web.service.dto.BlockeduserCriteria;
import com.jhipsterpress2.web.service.BlockeduserQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Blockeduser.
 */
@RestController
@RequestMapping("/api")
public class BlockeduserResource {

    private final Logger log = LoggerFactory.getLogger(BlockeduserResource.class);

    private static final String ENTITY_NAME = "blockeduser";

    private final BlockeduserService blockeduserService;

    private final BlockeduserQueryService blockeduserQueryService;

    public BlockeduserResource(BlockeduserService blockeduserService, BlockeduserQueryService blockeduserQueryService) {
        this.blockeduserService = blockeduserService;
        this.blockeduserQueryService = blockeduserQueryService;
    }

    /**
     * POST  /blockedusers : Create a new blockeduser.
     *
     * @param blockeduserDTO the blockeduserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new blockeduserDTO, or with status 400 (Bad Request) if the blockeduser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/blockedusers")
    @Timed
    public ResponseEntity<BlockeduserDTO> createBlockeduser(@RequestBody BlockeduserDTO blockeduserDTO) throws URISyntaxException {
        log.debug("REST request to save Blockeduser : {}", blockeduserDTO);
        if (blockeduserDTO.getId() != null) {
            throw new BadRequestAlertException("A new blockeduser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlockeduserDTO result = blockeduserService.save(blockeduserDTO);
        return ResponseEntity.created(new URI("/api/blockedusers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blockedusers : Updates an existing blockeduser.
     *
     * @param blockeduserDTO the blockeduserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated blockeduserDTO,
     * or with status 400 (Bad Request) if the blockeduserDTO is not valid,
     * or with status 500 (Internal Server Error) if the blockeduserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/blockedusers")
    @Timed
    public ResponseEntity<BlockeduserDTO> updateBlockeduser(@RequestBody BlockeduserDTO blockeduserDTO) throws URISyntaxException {
        log.debug("REST request to update Blockeduser : {}", blockeduserDTO);
        if (blockeduserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BlockeduserDTO result = blockeduserService.save(blockeduserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, blockeduserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /blockedusers : get all the blockedusers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of blockedusers in body
     */
    @GetMapping("/blockedusers")
    @Timed
    public ResponseEntity<List<BlockeduserDTO>> getAllBlockedusers(BlockeduserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Blockedusers by criteria: {}", criteria);
        Page<BlockeduserDTO> page = blockeduserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blockedusers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /blockedusers/:id : get the "id" blockeduser.
     *
     * @param id the id of the blockeduserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the blockeduserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/blockedusers/{id}")
    @Timed
    public ResponseEntity<BlockeduserDTO> getBlockeduser(@PathVariable Long id) {
        log.debug("REST request to get Blockeduser : {}", id);
        Optional<BlockeduserDTO> blockeduserDTO = blockeduserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blockeduserDTO);
    }

    /**
     * DELETE  /blockedusers/:id : delete the "id" blockeduser.
     *
     * @param id the id of the blockeduserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/blockedusers/{id}")
    @Timed
    public ResponseEntity<Void> deleteBlockeduser(@PathVariable Long id) {
        log.debug("REST request to delete Blockeduser : {}", id);
        blockeduserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
