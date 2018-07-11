package com.jhipsterpress2.web.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Celeb.
 */
@Entity
@Table(name = "celeb")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Celeb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "celeb_name", length = 40, nullable = false)
    private String celebName;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "celeb_party",
               joinColumns = @JoinColumn(name = "celebs_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "parties_id", referencedColumnName = "id"))
    private Set<Party> parties = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "celeb_profile",
               joinColumns = @JoinColumn(name = "celebs_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "profiles_id", referencedColumnName = "id"))
    private Set<Profile> profiles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCelebName() {
        return celebName;
    }

    public Celeb celebName(String celebName) {
        this.celebName = celebName;
        return this;
    }

    public void setCelebName(String celebName) {
        this.celebName = celebName;
    }

    public Set<Party> getParties() {
        return parties;
    }

    public Celeb parties(Set<Party> parties) {
        this.parties = parties;
        return this;
    }

    public Celeb addParty(Party party) {
        this.parties.add(party);
        party.getCelebs().add(this);
        return this;
    }

    public Celeb removeParty(Party party) {
        this.parties.remove(party);
        party.getCelebs().remove(this);
        return this;
    }

    public void setParties(Set<Party> parties) {
        this.parties = parties;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public Celeb profiles(Set<Profile> profiles) {
        this.profiles = profiles;
        return this;
    }

    public Celeb addProfile(Profile profile) {
        this.profiles.add(profile);
        profile.getCelebs().add(this);
        return this;
    }

    public Celeb removeProfile(Profile profile) {
        this.profiles.remove(profile);
        profile.getCelebs().remove(this);
        return this;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
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
        Celeb celeb = (Celeb) o;
        if (celeb.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), celeb.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Celeb{" +
            "id=" + getId() +
            ", celebName='" + getCelebName() + "'" +
            "}";
    }
}
