package com.secondteam.rentauto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import com.secondteam.rentauto.domain.enumeration.CarType;

/**
 * A Autopark.
 */
@Entity
@Table(name = "autopark")
public class Autopark implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "mark", length = 100, nullable = false)
    private String mark;

    @NotNull
    @Size(max = 100)
    @Column(name = "model", length = 100, nullable = false)
    private String model;

    @Column(name = "pre_price")
    private Integer prePrice;

    @NotNull
    @Size(max = 20)
    @Column(name = "color", length = 20, nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CarType type;

    @Column(name = "pledge")
    private Integer pledge;

    @Column(name = "status_availeble")
    private Boolean statusAvaileble;

    @OneToMany(mappedBy = "autopark")
    private Set<Rate> rates = new HashSet<>();

    @ManyToMany(mappedBy = "autoparks")
    @JsonIgnore
    private Set<Fixcar> fixcars = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public Autopark mark(String mark) {
        this.mark = mark;
        return this;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public Autopark model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getPrePrice() {
        return prePrice;
    }

    public Autopark prePrice(Integer prePrice) {
        this.prePrice = prePrice;
        return this;
    }

    public void setPrePrice(Integer prePrice) {
        this.prePrice = prePrice;
    }

    public String getColor() {
        return color;
    }

    public Autopark color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public CarType getType() {
        return type;
    }

    public Autopark type(CarType type) {
        this.type = type;
        return this;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public Integer getPledge() {
        return pledge;
    }

    public Autopark pledge(Integer pledge) {
        this.pledge = pledge;
        return this;
    }

    public void setPledge(Integer pledge) {
        this.pledge = pledge;
    }

    public Boolean isStatusAvaileble() {
        return statusAvaileble;
    }

    public Autopark statusAvaileble(Boolean statusAvaileble) {
        this.statusAvaileble = statusAvaileble;
        return this;
    }

    public void setStatusAvaileble(Boolean statusAvaileble) {
        this.statusAvaileble = statusAvaileble;
    }

    public Set<Rate> getRates() {
        return rates;
    }

    public Autopark rates(Set<Rate> rates) {
        this.rates = rates;
        return this;
    }

    public Autopark addRates(Rate rate) {
        this.rates.add(rate);
        rate.setAutopark(this);
        return this;
    }

    public Autopark removeRates(Rate rate) {
        this.rates.remove(rate);
        rate.setAutopark(null);
        return this;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }

    public Set<Fixcar> getFixcars() {
        return fixcars;
    }

    public Autopark fixcars(Set<Fixcar> fixcars) {
        this.fixcars = fixcars;
        return this;
    }

    public Autopark addFixcars(Fixcar fixcar) {
        this.fixcars.add(fixcar);
        fixcar.getAutoparks().add(this);
        return this;
    }

    public Autopark removeFixcars(Fixcar fixcar) {
        this.fixcars.remove(fixcar);
        fixcar.getAutoparks().remove(this);
        return this;
    }

    public void setFixcars(Set<Fixcar> fixcars) {
        this.fixcars = fixcars;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Autopark)) {
            return false;
        }
        return id != null && id.equals(((Autopark) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Autopark{" +
            "id=" + getId() +
            ", mark='" + getMark() + "'" +
            ", model='" + getModel() + "'" +
            ", prePrice=" + getPrePrice() +
            ", color='" + getColor() + "'" +
            ", type='" + getType() + "'" +
            ", pledge=" + getPledge() +
            ", statusAvaileble='" + isStatusAvaileble() + "'" +
            "}";
    }
}
