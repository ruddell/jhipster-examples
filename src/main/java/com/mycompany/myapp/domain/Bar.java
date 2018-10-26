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
 * A Bar.
 */
@Entity
@Table(name = "bar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "bar")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Foo> foos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Bar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Foo> getFoos() {
        return foos;
    }

    public Bar foos(Set<Foo> foos) {
        this.foos = foos;
        return this;
    }

    public Bar addFoo(Foo foo) {
        this.foos.add(foo);
        foo.setBar(this);
        return this;
    }

    public Bar removeFoo(Foo foo) {
        this.foos.remove(foo);
        foo.setBar(null);
        return this;
    }

    public void setFoos(Set<Foo> foos) {
        this.foos = foos;
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
        Bar bar = (Bar) o;
        if (bar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
