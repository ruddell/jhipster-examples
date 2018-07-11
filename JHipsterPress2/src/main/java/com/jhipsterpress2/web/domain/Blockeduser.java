package com.jhipsterpress2.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Blockeduser.
 */
@Entity
@Table(name = "blockeduser")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Blockeduser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "creation_date")
    private Instant creationDate;

    @ManyToOne
    @JsonIgnoreProperties("blockingusers")
    private Party blockinguser;

    @ManyToOne
    @JsonIgnoreProperties("blockedusers")
    private Party blockeduser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Blockeduser creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Party getBlockinguser() {
        return blockinguser;
    }

    public Blockeduser blockinguser(Party party) {
        this.blockinguser = party;
        return this;
    }

    public void setBlockinguser(Party party) {
        this.blockinguser = party;
    }

    public Party getBlockeduser() {
        return blockeduser;
    }

    public Blockeduser blockeduser(Party party) {
        this.blockeduser = party;
        return this;
    }

    public void setBlockeduser(Party party) {
        this.blockeduser = party;
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
        Blockeduser blockeduser = (Blockeduser) o;
        if (blockeduser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blockeduser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Blockeduser{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
