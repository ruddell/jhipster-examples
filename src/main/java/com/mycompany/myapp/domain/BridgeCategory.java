package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BridgeCategory.
 */
@Entity
@Table(name = "bridge_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BridgeCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_bridge")
    private Long idBridge;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("")
    private BridgeCategory bridgeCategoryParent;

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

    public BridgeCategory idBridge(Long idBridge) {
        this.idBridge = idBridge;
        return this;
    }

    public void setIdBridge(Long idBridge) {
        this.idBridge = idBridge;
    }

    public String getName() {
        return name;
    }

    public BridgeCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BridgeCategory getBridgeCategoryParent() {
        return bridgeCategoryParent;
    }

    public BridgeCategory bridgeCategoryParent(BridgeCategory bridgeCategory) {
        this.bridgeCategoryParent = bridgeCategory;
        return this;
    }

    public void setBridgeCategoryParent(BridgeCategory bridgeCategory) {
        this.bridgeCategoryParent = bridgeCategory;
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
        BridgeCategory bridgeCategory = (BridgeCategory) o;
        if (bridgeCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bridgeCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BridgeCategory{" +
            "id=" + getId() +
            ", idBridge=" + getIdBridge() +
            ", name='" + getName() + "'" +
            "}";
    }
}
