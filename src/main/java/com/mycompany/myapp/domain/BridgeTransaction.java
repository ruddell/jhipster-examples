package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A BridgeTransaction.
 */
@Entity
@Table(name = "bridge_transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BridgeTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_bridge")
    private Long idBridge;

    @Column(name = "description")
    private String description;

    @Column(name = "raw_description")
    private String rawDescription;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JsonIgnoreProperties("")
    private BridgeCategory bridgeCategory;

    @ManyToOne
    @JsonIgnoreProperties("bridgeTransactions")
    private BridgeAccountBank bridgeAccountBank;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdBridge() {
        return idBridge;
    }

    public BridgeTransaction idBridge(Long idBridge) {
        this.idBridge = idBridge;
        return this;
    }

    public void setIdBridge(Long idBridge) {
        this.idBridge = idBridge;
    }

    public String getDescription() {
        return description;
    }

    public BridgeTransaction description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRawDescription() {
        return rawDescription;
    }

    public BridgeTransaction rawDescription(String rawDescription) {
        this.rawDescription = rawDescription;
        return this;
    }

    public void setRawDescription(String rawDescription) {
        this.rawDescription = rawDescription;
    }

    public LocalDate getDate() {
        return date;
    }

    public BridgeTransaction date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public BridgeTransaction updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public BridgeTransaction isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public BridgeCategory getBridgeCategory() {
        return bridgeCategory;
    }

    public BridgeTransaction bridgeCategory(BridgeCategory bridgeCategory) {
        this.bridgeCategory = bridgeCategory;
        return this;
    }

    public void setBridgeCategory(BridgeCategory bridgeCategory) {
        this.bridgeCategory = bridgeCategory;
    }

    public BridgeAccountBank getBridgeAccountBank() {
        return bridgeAccountBank;
    }

    public BridgeTransaction bridgeAccountBank(BridgeAccountBank bridgeAccountBank) {
        this.bridgeAccountBank = bridgeAccountBank;
        return this;
    }

    public void setBridgeAccountBank(BridgeAccountBank bridgeAccountBank) {
        this.bridgeAccountBank = bridgeAccountBank;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BridgeTransaction bridgeTransaction = (BridgeTransaction) o;
        if (bridgeTransaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bridgeTransaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BridgeTransaction{" +
            "id=" + getId() +
            ", idBridge=" + getIdBridge() +
            ", description='" + getDescription() + "'" +
            ", rawDescription='" + getRawDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            "}";
    }
}
