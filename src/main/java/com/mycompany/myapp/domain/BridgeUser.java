package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BridgeUser.
 */
@Entity
@Table(name = "bridge_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BridgeUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid_bridge")
    private String uuidBridge;

    @Column(name = "email")
    private String email;

    @Column(name = "jhi_password")
    private String password;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "bridgeUser")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BridgeAccountOnlineBank> bridgeAccountsOnlineBanks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuidBridge() {
        return uuidBridge;
    }

    public BridgeUser uuidBridge(String uuidBridge) {
        this.uuidBridge = uuidBridge;
        return this;
    }

    public void setUuidBridge(String uuidBridge) {
        this.uuidBridge = uuidBridge;
    }

    public String getEmail() {
        return email;
    }

    public BridgeUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public BridgeUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public BridgeUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<BridgeAccountOnlineBank> getBridgeAccountsOnlineBanks() {
        return bridgeAccountsOnlineBanks;
    }

    public BridgeUser bridgeAccountsOnlineBanks(Set<BridgeAccountOnlineBank> bridgeAccountOnlineBanks) {
        this.bridgeAccountsOnlineBanks = bridgeAccountOnlineBanks;
        return this;
    }

    public BridgeUser addBridgeAccountsOnlineBank(BridgeAccountOnlineBank bridgeAccountOnlineBank) {
        this.bridgeAccountsOnlineBanks.add(bridgeAccountOnlineBank);
        bridgeAccountOnlineBank.setBridgeUser(this);
        return this;
    }

    public BridgeUser removeBridgeAccountsOnlineBank(BridgeAccountOnlineBank bridgeAccountOnlineBank) {
        this.bridgeAccountsOnlineBanks.remove(bridgeAccountOnlineBank);
        bridgeAccountOnlineBank.setBridgeUser(null);
        return this;
    }

    public void setBridgeAccountsOnlineBanks(Set<BridgeAccountOnlineBank> bridgeAccountOnlineBanks) {
        this.bridgeAccountsOnlineBanks = bridgeAccountOnlineBanks;
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
        BridgeUser bridgeUser = (BridgeUser) o;
        if (bridgeUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bridgeUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BridgeUser{" +
            "id=" + getId() +
            ", uuidBridge='" + getUuidBridge() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
