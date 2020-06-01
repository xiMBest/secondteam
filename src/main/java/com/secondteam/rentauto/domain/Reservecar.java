package com.secondteam.rentauto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

import com.secondteam.rentauto.domain.enumeration.CarType;

/**
 * A Reservecar.
 */
@Entity
@Table(name = "reservecar")
public class Reservecar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CarType type;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "date_pick_up")
    private ZonedDateTime datePickUP;

    @Column(name = "date_drop_down")
    private ZonedDateTime dateDropDown;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("reservecars")
    private Customer customer;

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

    public Reservecar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarType getType() {
        return type;
    }

    public Reservecar type(CarType type) {
        this.type = type;
        return this;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public Reservecar totalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ZonedDateTime getDatePickUP() {
        return datePickUP;
    }

    public Reservecar datePickUP(ZonedDateTime datePickUP) {
        this.datePickUP = datePickUP;
        return this;
    }

    public void setDatePickUP(ZonedDateTime datePickUP) {
        this.datePickUP = datePickUP;
    }

    public ZonedDateTime getDateDropDown() {
        return dateDropDown;
    }

    public Reservecar dateDropDown(ZonedDateTime dateDropDown) {
        this.dateDropDown = dateDropDown;
        return this;
    }

    public void setDateDropDown(ZonedDateTime dateDropDown) {
        this.dateDropDown = dateDropDown;
    }

    public String getDescription() {
        return description;
    }

    public Reservecar description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Reservecar customer(Customer customer) {
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
        if (!(o instanceof Reservecar)) {
            return false;
        }
        return id != null && id.equals(((Reservecar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Reservecar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", datePickUP='" + getDatePickUP() + "'" +
            ", dateDropDown='" + getDateDropDown() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
