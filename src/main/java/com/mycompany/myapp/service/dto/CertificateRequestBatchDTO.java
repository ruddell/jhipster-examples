package com.mycompany.myapp.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CertificateRequestBatch entity.
 */
public class CertificateRequestBatchDTO implements Serializable {

    private Long id;

    private String name;

    private Instant creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CertificateRequestBatchDTO certificateRequestBatchDTO = (CertificateRequestBatchDTO) o;
        if(certificateRequestBatchDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), certificateRequestBatchDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CertificateRequestBatchDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
