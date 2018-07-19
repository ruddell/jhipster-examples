package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BridgeAccountBank.
 */
@Entity
@Table(name = "bridge_account_bank")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BridgeAccountBank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_bridge")
    private Long idBridge;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "item_status")
    private Long itemStatus;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @OneToMany(mappedBy = "bridgeAccountBank")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AccountBankStat> bridgeAccountBankStats = new HashSet<>();

    @OneToMany(mappedBy = "bridgeAccountBank")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BridgeTransaction> bridgeTransactions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private BridgeBank bridgeBank;

    @ManyToOne
    @JsonIgnoreProperties("bridgeAccountsBanks")
    private BridgeAccountOnlineBank bridgeAccountOnlineBank;

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

    public BridgeAccountBank idBridge(Long idBridge) {
        this.idBridge = idBridge;
        return this;
    }

    public void setIdBridge(Long idBridge) {
        this.idBridge = idBridge;
    }

    public String getName() {
        return name;
    }

    public BridgeAccountBank name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public BridgeAccountBank type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public BridgeAccountBank currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getItemStatus() {
        return itemStatus;
    }

    public BridgeAccountBank itemStatus(Long itemStatus) {
        this.itemStatus = itemStatus;
        return this;
    }

    public void setItemStatus(Long itemStatus) {
        this.itemStatus = itemStatus;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public BridgeAccountBank updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<AccountBankStat> getBridgeAccountBankStats() {
        return bridgeAccountBankStats;
    }

    public BridgeAccountBank bridgeAccountBankStats(Set<AccountBankStat> accountBankStats) {
        this.bridgeAccountBankStats = accountBankStats;
        return this;
    }

    public BridgeAccountBank addBridgeAccountBankStats(AccountBankStat accountBankStat) {
        this.bridgeAccountBankStats.add(accountBankStat);
        accountBankStat.setBridgeAccountBank(this);
        return this;
    }

    public BridgeAccountBank removeBridgeAccountBankStats(AccountBankStat accountBankStat) {
        this.bridgeAccountBankStats.remove(accountBankStat);
        accountBankStat.setBridgeAccountBank(null);
        return this;
    }

    public void setBridgeAccountBankStats(Set<AccountBankStat> accountBankStats) {
        this.bridgeAccountBankStats = accountBankStats;
    }

    public Set<BridgeTransaction> getBridgeTransactions() {
        return bridgeTransactions;
    }

    public BridgeAccountBank bridgeTransactions(Set<BridgeTransaction> bridgeTransactions) {
        this.bridgeTransactions = bridgeTransactions;
        return this;
    }

    public BridgeAccountBank addBridgeTransactions(BridgeTransaction bridgeTransaction) {
        this.bridgeTransactions.add(bridgeTransaction);
        bridgeTransaction.setBridgeAccountBank(this);
        return this;
    }

    public BridgeAccountBank removeBridgeTransactions(BridgeTransaction bridgeTransaction) {
        this.bridgeTransactions.remove(bridgeTransaction);
        bridgeTransaction.setBridgeAccountBank(null);
        return this;
    }

    public void setBridgeTransactions(Set<BridgeTransaction> bridgeTransactions) {
        this.bridgeTransactions = bridgeTransactions;
    }

    public BridgeBank getBridgeBank() {
        return bridgeBank;
    }

    public BridgeAccountBank bridgeBank(BridgeBank bridgeBank) {
        this.bridgeBank = bridgeBank;
        return this;
    }

    public void setBridgeBank(BridgeBank bridgeBank) {
        this.bridgeBank = bridgeBank;
    }

    public BridgeAccountOnlineBank getBridgeAccountOnlineBank() {
        return bridgeAccountOnlineBank;
    }

    public BridgeAccountBank bridgeAccountOnlineBank(BridgeAccountOnlineBank bridgeAccountOnlineBank) {
        this.bridgeAccountOnlineBank = bridgeAccountOnlineBank;
        return this;
    }

    public void setBridgeAccountOnlineBank(BridgeAccountOnlineBank bridgeAccountOnlineBank) {
        this.bridgeAccountOnlineBank = bridgeAccountOnlineBank;
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
        BridgeAccountBank bridgeAccountBank = (BridgeAccountBank) o;
        if (bridgeAccountBank.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bridgeAccountBank.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BridgeAccountBank{" +
            "id=" + getId() +
            ", idBridge=" + getIdBridge() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", itemStatus=" + getItemStatus() +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
