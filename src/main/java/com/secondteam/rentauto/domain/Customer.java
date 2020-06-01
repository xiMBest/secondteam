package com.secondteam.rentauto.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(name = "surname", length = 100, nullable = false)
    private String surname;

    @Column(name = "years")
    private Integer years;

    @NotNull
    @Size(max = 100)
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$")
    @Column(name = "phone", unique = true)
    private String phone;

    @NotNull
    @Size(max = 100)
    @Column(name = "adress", length = 100, nullable = false)
    private String adress;

    @OneToMany(mappedBy = "customer")
    private Set<Reservecar> reservecars = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<Penalty> penaltys = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<Insuarence> insuarences = new HashSet<>();

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

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Customer surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getYears() {
        return years;
    }

    public Customer years(Integer years) {
        this.years = years;
        return this;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Customer phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public Customer adress(String adress) {
        this.adress = adress;
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Set<Reservecar> getReservecars() {
        return reservecars;
    }

    public Customer reservecars(Set<Reservecar> reservecars) {
        this.reservecars = reservecars;
        return this;
    }

    public Customer addReservecars(Reservecar reservecar) {
        this.reservecars.add(reservecar);
        reservecar.setCustomer(this);
        return this;
    }

    public Customer removeReservecars(Reservecar reservecar) {
        this.reservecars.remove(reservecar);
        reservecar.setCustomer(null);
        return this;
    }

    public void setReservecars(Set<Reservecar> reservecars) {
        this.reservecars = reservecars;
    }

    public Set<Penalty> getPenaltys() {
        return penaltys;
    }

    public Customer penaltys(Set<Penalty> penalties) {
        this.penaltys = penalties;
        return this;
    }

    public Customer addPenaltys(Penalty penalty) {
        this.penaltys.add(penalty);
        penalty.setCustomer(this);
        return this;
    }

    public Customer removePenaltys(Penalty penalty) {
        this.penaltys.remove(penalty);
        penalty.setCustomer(null);
        return this;
    }

    public void setPenaltys(Set<Penalty> penalties) {
        this.penaltys = penalties;
    }

    public Set<Insuarence> getInsuarences() {
        return insuarences;
    }

    public Customer insuarences(Set<Insuarence> insuarences) {
        this.insuarences = insuarences;
        return this;
    }

    public Customer addInsuarences(Insuarence insuarence) {
        this.insuarences.add(insuarence);
        insuarence.setCustomer(this);
        return this;
    }

    public Customer removeInsuarences(Insuarence insuarence) {
        this.insuarences.remove(insuarence);
        insuarence.setCustomer(null);
        return this;
    }

    public void setInsuarences(Set<Insuarence> insuarences) {
        this.insuarences = insuarences;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", years=" + getYears() +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", adress='" + getAdress() + "'" +
            "}";
    }
}
