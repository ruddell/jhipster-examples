package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CertificateRequestBatchDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CertificateRequestBatch and its DTO CertificateRequestBatchDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CertificateRequestBatchMapper extends EntityMapper<CertificateRequestBatchDTO, CertificateRequestBatch> {



    default CertificateRequestBatch fromId(Long id) {
        if (id == null) {
            return null;
        }
        CertificateRequestBatch certificateRequestBatch = new CertificateRequestBatch();
        certificateRequestBatch.setId(id);
        return certificateRequestBatch;
    }
}
