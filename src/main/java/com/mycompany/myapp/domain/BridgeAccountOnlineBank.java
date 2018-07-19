package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BridgeAccountOnlineBank.
 */
@Entity
@Table(name = "bridge_account_online_bank")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BridgeAccountOnlineBank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_bridge")
    private Long idBridge;

    @Column(name = "status")
    private Long status;

    @OneToMany(mappedBy = "bridgeAccountOnlineBank")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BridgeAccountBank> bridgeAccountsBanks = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private BridgeBank bridgeBank;

    @ManyToOne
    @JsonIgnoreProperties("bridgeAccountsOnlineBanks")
    private BridgeUser bridgeUser;

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

    public BridgeAccountOnlineBank idBridge(Long idBridge) {
        this.idBridge = idBridge;
        return this;
    }

    public void setIdBridge(Long idBridge) {
        this.idBridge = idBridge;
    }

    public Long getStatus() {
        return status;
    }

    public BridgeAccountOnlineBank status(Long status) {
        this.status = status;
        return this;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Set<BridgeAccountBank> getBridgeAccountsBanks() {
        return bridgeAccountsBanks;
    }

    public BridgeAccountOnlineBank bridgeAccountsBanks(Set<BridgeAccountBank> bridgeAccountBanks) {
        this.bridgeAccountsBanks = bridgeAccountBanks;
        return this;
    }

    public BridgeAccountOnlineBank addBridgeAccountsBank(BridgeAccountBank bridgeAccountBank) {
        this.bridgeAccountsBanks.add(bridgeAccountBank);
        bridgeAccountBank.setBridgeAccountOnlineBank(this);
        return this;
    }

    public BridgeAccountOnlineBank removeBridgeAccountsBank(BridgeAccountBank bridgeAccountBank) {
        this.bridgeAccountsBanks.remove(bridgeAccountBank);
        bridgeAccountBank.setBridgeAccountOnlineBank(null);
        return this;
    }

    public void setBridgeAccountsBanks(Set<BridgeAccountBank> bridgeAccountBanks) {
        this.bridgeAccountsBanks = bridgeAccountBanks;
    }

    public BridgeBank getBridgeBank() {
        return bridgeBank;
    }

    public BridgeAccountOnlineBank bridgeBank(BridgeBank bridgeBank) {
        this.bridgeBank = bridgeBank;
        return this;
    }

    public void setBridgeBank(BridgeBank bridgeBank) {
        this.bridgeBank = bridgeBank;
    }

    public BridgeUser getBridgeUser() {
        return bridgeUser;
    }

    public BridgeAccountOnlineBank bridgeUser(BridgeUser bridgeUser) {
        this.bridgeUser = bridgeUser;
        return this;
    }

    public void setBridgeUser(BridgeUser bridgeUser) {
        this.bridgeUser = bridgeUser;
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
        BridgeAccountOnlineBank bridgeAccountOnlineBank = (BridgeAccountOnlineBank) o;
        if (bridgeAccountOnlineBank.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bridgeAccountOnlineBank.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BridgeAccountOnlineBank{" +
            "id=" + getId() +
            ", idBridge=" + getIdBridge() +
            ", status=" + getStatus() +
            "}";
    }
}
