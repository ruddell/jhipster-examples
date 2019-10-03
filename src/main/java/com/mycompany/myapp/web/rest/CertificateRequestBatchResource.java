package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.CertificateRequestBatchService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.CertificateRequestBatchDTO;
import com.mycompany.myapp.service.dto.CertificateRequestBatchCriteria;
import com.mycompany.myapp.service.CertificateRequestBatchQueryService;
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
 * REST controller for managing CertificateRequestBatch.
 */
@RestController
@RequestMapping("/api")
public class CertificateRequestBatchResource {

    private final Logger log = LoggerFactory.getLogger(CertificateRequestBatchResource.class);

    private static final String ENTITY_NAME = "certificateRequestBatch";

    private final CertificateRequestBatchService certificateRequestBatchService;

    private final CertificateRequestBatchQueryService certificateRequestBatchQueryService;

    public CertificateRequestBatchResource(CertificateRequestBatchService certificateRequestBatchService, CertificateRequestBatchQueryService certificateRequestBatchQueryService) {
        this.certificateRequestBatchService = certificateRequestBatchService;
        this.certificateRequestBatchQueryService = certificateRequestBatchQueryService;
    }

    /**
     * POST  /certificate-request-batches : Create a new certificateRequestBatch.
     *
     * @param certificateRequestBatchDTO the certificateRequestBatchDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new certificateRequestBatchDTO, or with status 400 (Bad Request) if the certificateRequestBatch has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/certificate-request-batches")
    @Timed
    public ResponseEntity<CertificateRequestBatchDTO> createCertificateRequestBatch(@RequestBody CertificateRequestBatchDTO certificateRequestBatchDTO) throws URISyntaxException {
        log.debug("REST request to save CertificateRequestBatch : {}", certificateRequestBatchDTO);
        if (certificateRequestBatchDTO.getId() != null) {
            throw new BadRequestAlertException("A new certificateRequestBatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CertificateRequestBatchDTO result = certificateRequestBatchService.save(certificateRequestBatchDTO);
        return ResponseEntity.created(new URI("/api/certificate-request-batches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /certificate-request-batches : Updates an existing certificateRequestBatch.
     *
     * @param certificateRequestBatchDTO the certificateRequestBatchDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated certificateRequestBatchDTO,
     * or with status 400 (Bad Request) if the certificateRequestBatchDTO is not valid,
     * or with status 500 (Internal Server Error) if the certificateRequestBatchDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/certificate-request-batches")
    @Timed
    public ResponseEntity<CertificateRequestBatchDTO> updateCertificateRequestBatch(@RequestBody CertificateRequestBatchDTO certificateRequestBatchDTO) throws URISyntaxException {
        log.debug("REST request to update CertificateRequestBatch : {}", certificateRequestBatchDTO);
        if (certificateRequestBatchDTO.getId() == null) {
            return createCertificateRequestBatch(certificateRequestBatchDTO);
        }
        CertificateRequestBatchDTO result = certificateRequestBatchService.save(certificateRequestBatchDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, certificateRequestBatchDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /certificate-request-batches : get all the certificateRequestBatches.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of certificateRequestBatches in body
     */
    @GetMapping("/certificate-request-batches")
    @Timed
    public ResponseEntity<List<CertificateRequestBatchDTO>> getAllCertificateRequestBatches(CertificateRequestBatchCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CertificateRequestBatches by criteria: {}", criteria);
        Page<CertificateRequestBatchDTO> page = certificateRequestBatchQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/certificate-request-batches");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /certificate-request-batches/:id : get the "id" certificateRequestBatch.
     *
     * @param id the id of the certificateRequestBatchDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the certificateRequestBatchDTO, or with status 404 (Not Found)
     */
    @GetMapping("/certificate-request-batches/{id}")
    @Timed
    public ResponseEntity<CertificateRequestBatchDTO> getCertificateRequestBatch(@PathVariable Long id) {
        log.debug("REST request to get CertificateRequestBatch : {}", id);
        CertificateRequestBatchDTO certificateRequestBatchDTO = certificateRequestBatchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(certificateRequestBatchDTO));
    }

    /**
     * DELETE  /certificate-request-batches/:id : delete the "id" certificateRequestBatch.
     *
     * @param id the id of the certificateRequestBatchDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/certificate-request-batches/{id}")
    @Timed
    public ResponseEntity<Void> deleteCertificateRequestBatch(@PathVariable Long id) {
        log.debug("REST request to delete CertificateRequestBatch : {}", id);
        certificateRequestBatchService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
