package com.secondteam.rentauto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

import com.secondteam.rentauto.domain.enumeration.InsuarenceType;

/**
 * A Insuarence.
 */
@Entity
@Table(name = "insuarence")
public class Insuarence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_apply")
    private ZonedDateTime dateApply;

    @Column(name = "date_end")
    private ZonedDateTime dateEnd;

    @Column(name = "cost")
    private Integer cost;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private InsuarenceType type;

    @ManyToOne
    @JsonIgnoreProperties("insuarences")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateApply() {
        return dateApply;
    }

    public Insuarence dateApply(ZonedDateTime dateApply) {
        this.dateApply = dateApply;
        return this;
    }

    public void setDateApply(ZonedDateTime dateApply) {
        this.dateApply = dateApply;
    }

    public ZonedDateTime getDateEnd() {
        return dateEnd;
    }

    public Insuarence dateEnd(ZonedDateTime dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public void setDateEnd(ZonedDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getCost() {
        return cost;
    }

    public Insuarence cost(Integer cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public InsuarenceType getType() {
        return type;
    }

    public Insuarence type(InsuarenceType type) {
        this.type = type;
        return this;
    }

    public void setType(InsuarenceType type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Insuarence customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Insuarence)) {
            return false;
        }
        return id != null && id.equals(((Insuarence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Insuarence{" +
            "id=" + getId() +
            ", dateApply='" + getDateApply() + "'" +
            ", dateEnd='" + getDateEnd() + "'" +
            ", cost=" + getCost() +
            ", type='" + getType() + "'" +
            "}";
    }
}
