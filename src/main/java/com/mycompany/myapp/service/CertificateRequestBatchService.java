package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CertificateRequestBatch;
import com.mycompany.myapp.repository.CertificateRequestBatchRepository;
import com.mycompany.myapp.service.dto.CertificateRequestBatchDTO;
import com.mycompany.myapp.service.mapper.CertificateRequestBatchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CertificateRequestBatch.
 */
@Service
@Transactional
public class CertificateRequestBatchService {

    private final Logger log = LoggerFactory.getLogger(CertificateRequestBatchService.class);

    private final CertificateRequestBatchRepository certificateRequestBatchRepository;

    private final CertificateRequestBatchMapper certificateRequestBatchMapper;

    public CertificateRequestBatchService(CertificateRequestBatchRepository certificateRequestBatchRepository, CertificateRequestBatchMapper certificateRequestBatchMapper) {
        this.certificateRequestBatchRepository = certificateRequestBatchRepository;
        this.certificateRequestBatchMapper = certificateRequestBatchMapper;
    }

    /**
     * Save a certificateRequestBatch.
     *
     * @param certificateRequestBatchDTO the entity to save
     * @return the persisted entity
     */
    public CertificateRequestBatchDTO save(CertificateRequestBatchDTO certificateRequestBatchDTO) {
        log.debug("Request to save CertificateRequestBatch : {}", certificateRequestBatchDTO);
        CertificateRequestBatch certificateRequestBatch = certificateRequestBatchMapper.toEntity(certificateRequestBatchDTO);
        certificateRequestBatch = certificateRequestBatchRepository.save(certificateRequestBatch);
        return certificateRequestBatchMapper.toDto(certificateRequestBatch);
    }

    /**
     * Get all the certificateRequestBatches.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CertificateRequestBatchDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CertificateRequestBatches");
        return certificateRequestBatchRepository.findAll(pageable)
            .map(certificateRequestBatchMapper::toDto);
    }

    /**
     * Get one certificateRequestBatch by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CertificateRequestBatchDTO findOne(Long id) {
        log.debug("Request to get CertificateRequestBatch : {}", id);
        CertificateRequestBatch certificateRequestBatch = certificateRequestBatchRepository.findOne(id);
        return certificateRequestBatchMapper.toDto(certificateRequestBatch);
    }

    /**
     * Delete the certificateRequestBatch by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CertificateRequestBatch : {}", id);
        certificateRequestBatchRepository.delete(id);
    }
}
