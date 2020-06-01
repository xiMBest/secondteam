package com.secondteam.rentauto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.secondteam.rentauto.domain.enumeration.Ratestars;

/**
 * A Rate.
 */
@Entity
@Table(name = "rate")
public class Rate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "raiting")
    private Ratestars raiting;

    @ManyToOne
    @JsonIgnoreProperties("rates")
    private Autopark autopark;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ratestars getRaiting() {
        return raiting;
    }

    public Rate raiting(Ratestars raiting) {
        this.raiting = raiting;
        return this;
    }

    public void setRaiting(Ratestars raiting) {
        this.raiting = raiting;
    }

    public Autopark getAutopark() {
        return autopark;
    }

    public Rate autopark(Autopark autopark) {
        this.autopark = autopark;
        return this;
    }

    public void setAutopark(Autopark autopark) {
        this.autopark = autopark;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rate)) {
            return false;
        }
        return id != null && id.equals(((Rate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Rate{" +
            "id=" + getId() +
            ", raiting='" + getRaiting() + "'" +
            "}";
    }
}
