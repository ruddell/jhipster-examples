package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A AccountBankStat.
 */
@Entity
@Table(name = "account_bank_stat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AccountBankStat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "last_refresh")
    private ZonedDateTime lastRefresh;

    @ManyToOne
    @JsonIgnoreProperties("bridgeAccountBankStats")
    private BridgeAccountBank bridgeAccountBank;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public AccountBankStat balance(Double balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public ZonedDateTime getLastRefresh() {
        return lastRefresh;
    }

    public AccountBankStat lastRefresh(ZonedDateTime lastRefresh) {
        this.lastRefresh = lastRefresh;
        return this;
    }

    public void setLastRefresh(ZonedDateTime lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

    public BridgeAccountBank getBridgeAccountBank() {
        return bridgeAccountBank;
    }

    public AccountBankStat bridgeAccountBank(BridgeAccountBank bridgeAccountBank) {
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
        AccountBankStat accountBankStat = (AccountBankStat) o;
        if (accountBankStat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountBankStat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountBankStat{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", lastRefresh='" + getLastRefresh() + "'" +
            "}";
    }
}
