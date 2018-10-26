package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Foo;
import com.mycompany.myapp.repository.FooRepository;
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
 * REST controller for managing Foo.
 */
@RestController
@RequestMapping("/api")
public class FooResource {

    private final Logger log = LoggerFactory.getLogger(FooResource.class);

    private static final String ENTITY_NAME = "foo";

    private FooRepository fooRepository;

    public FooResource(FooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }

    /**
     * POST  /foos : Create a new foo.
     *
     * @param foo the foo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new foo, or with status 400 (Bad Request) if the foo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/foos")
    @Timed
    public ResponseEntity<Foo> createFoo(@RequestBody Foo foo) throws URISyntaxException {
        log.debug("REST request to save Foo : {}", foo);
        if (foo.getId() != null) {
            throw new BadRequestAlertException("A new foo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Foo result = fooRepository.save(foo);
        return ResponseEntity.created(new URI("/api/foos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /foos : Updates an existing foo.
     *
     * @param foo the foo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated foo,
     * or with status 400 (Bad Request) if the foo is not valid,
     * or with status 500 (Internal Server Error) if the foo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/foos")
    @Timed
    public ResponseEntity<Foo> updateFoo(@RequestBody Foo foo) throws URISyntaxException {
        log.debug("REST request to update Foo : {}", foo);
        if (foo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Foo result = fooRepository.save(foo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, foo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /foos : get all the foos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of foos in body
     */
    @GetMapping("/foos")
    @Timed
    public List<Foo> getAllFoos() {
        log.debug("REST request to get all Foos");
        return fooRepository.findAll();
    }

    /**
     * GET  /foos/:id : get the "id" foo.
     *
     * @param id the id of the foo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the foo, or with status 404 (Not Found)
     */
    @GetMapping("/foos/{id}")
    @Timed
    public ResponseEntity<Foo> getFoo(@PathVariable Long id) {
        log.debug("REST request to get Foo : {}", id);
        Optional<Foo> foo = fooRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(foo);
    }

    /**
     * DELETE  /foos/:id : delete the "id" foo.
     *
     * @param id the id of the foo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/foos/{id}")
    @Timed
    public ResponseEntity<Void> deleteFoo(@PathVariable Long id) {
        log.debug("REST request to delete Foo : {}", id);

        fooRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
