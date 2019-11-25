package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.FooService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.FooDTO;
import com.mycompany.myapp.service.dto.FooCriteria;
import com.mycompany.myapp.service.FooQueryService;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Foo}.
 */
@RestController
@RequestMapping("/api")
public class FooResource {

    private final Logger log = LoggerFactory.getLogger(FooResource.class);

    private static final String ENTITY_NAME = "foo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FooService fooService;

    private final FooQueryService fooQueryService;

    public FooResource(FooService fooService, FooQueryService fooQueryService) {
        this.fooService = fooService;
        this.fooQueryService = fooQueryService;
    }

    /**
     * {@code POST  /foos} : Create a new foo.
     *
     * @param fooDTO the fooDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fooDTO, or with status {@code 400 (Bad Request)} if the foo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foos")
    public ResponseEntity<FooDTO> createFoo(@RequestBody FooDTO fooDTO) throws URISyntaxException {
        log.debug("REST request to save Foo : {}", fooDTO);
        if (fooDTO.getId() != null) {
            throw new BadRequestAlertException("A new foo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FooDTO result = fooService.save(fooDTO);
        return ResponseEntity.created(new URI("/api/foos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foos} : Updates an existing foo.
     *
     * @param fooDTO the fooDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fooDTO,
     * or with status {@code 400 (Bad Request)} if the fooDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fooDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foos")
    public ResponseEntity<FooDTO> updateFoo(@RequestBody FooDTO fooDTO) throws URISyntaxException {
        log.debug("REST request to update Foo : {}", fooDTO);
        if (fooDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FooDTO result = fooService.save(fooDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fooDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /foos} : get all the foos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foos in body.
     */
    @GetMapping("/foos")
    public ResponseEntity<List<FooDTO>> getAllFoos(FooCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Foos by criteria: {}", criteria);
        Page<FooDTO> page = fooQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /foos/count} : count all the foos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/foos/count")
    public ResponseEntity<Long> countFoos(FooCriteria criteria) {
        log.debug("REST request to count Foos by criteria: {}", criteria);
        return ResponseEntity.ok().body(fooQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /foos/:id} : get the "id" foo.
     *
     * @param id the id of the fooDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fooDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foos/{id}")
    public ResponseEntity<FooDTO> getFoo(@PathVariable Long id) {
        log.debug("REST request to get Foo : {}", id);
        Optional<FooDTO> fooDTO = fooService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fooDTO);
    }

    /**
     * {@code DELETE  /foos/:id} : delete the "id" foo.
     *
     * @param id the id of the fooDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foos/{id}")
    public ResponseEntity<Void> deleteFoo(@PathVariable Long id) {
        log.debug("REST request to delete Foo : {}", id);
        fooService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/foos?query=:query} : search for the foo corresponding
     * to the query.
     *
     * @param query the query of the foo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/foos")
    public ResponseEntity<List<FooDTO>> searchFoos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Foos for query {}", query);
        Page<FooDTO> page = fooService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
