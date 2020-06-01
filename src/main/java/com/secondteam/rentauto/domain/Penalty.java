package com.secondteam.rentauto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

/**
 * A Penalty.
 */
@Entity
@Table(name = "penalty")
public class Penalty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_penalty")
    private ZonedDateTime datePenalty;

    @Column(name = "price")
    private Integer price;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("penaltys")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDatePenalty() {
        return datePenalty;
    }

    public Penalty datePenalty(ZonedDateTime datePenalty) {
        this.datePenalty = datePenalty;
        return this;
    }

    public void setDatePenalty(ZonedDateTime datePenalty) {
        this.datePenalty = datePenalty;
    }

    public Integer getPrice() {
        return price;
    }

    public Penalty price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public Penalty description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Penalty customer(Customer customer) {
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
        if (!(o instanceof Penalty)) {
            return false;
        }
        return id != null && id.equals(((Penalty) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Penalty{" +
            "id=" + getId() +
            ", datePenalty='" + getDatePenalty() + "'" +
            ", price=" + getPrice() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
