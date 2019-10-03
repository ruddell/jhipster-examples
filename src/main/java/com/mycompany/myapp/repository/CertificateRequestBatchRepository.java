package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CertificateRequestBatch;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CertificateRequestBatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CertificateRequestBatchRepository extends JpaRepository<CertificateRequestBatch, Long>, JpaSpecificationExecutor<CertificateRequestBatch> {

}
