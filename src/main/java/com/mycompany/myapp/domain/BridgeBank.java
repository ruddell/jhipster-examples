package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BridgeBank.
 */
@Entity
@Table(name = "bridge_bank")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BridgeBank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_bridge")
    private Long idBridge;

    @Column(name = "name")
    private String name;

    @Column(name = "country_code")
    private String countryCode;

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

    public BridgeBank idBridge(Long idBridge) {
        this.idBridge = idBridge;
        return this;
    }

    public void setIdBridge(Long idBridge) {
        this.idBridge = idBridge;
    }

    public String getName() {
        return name;
    }

    public BridgeBank name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public BridgeBank countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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
        BridgeBank bridgeBank = (BridgeBank) o;
        if (bridgeBank.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bridgeBank.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BridgeBank{" +
            "id=" + getId() +
            ", idBridge=" + getIdBridge() +
            ", name='" + getName() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            "}";
    }
}
