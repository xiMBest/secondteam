package com.secondteam.rentauto.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Fixcar.
 */
@Entity
@Table(name = "fixcar")
public class Fixcar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private Integer price;

    @Column(name = "date_fixing")
    private ZonedDateTime dateFixing;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "fixcar_autoparks",
               joinColumns = @JoinColumn(name = "fixcar_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "autoparks_id", referencedColumnName = "id"))
    private Set<Autopark> autoparks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public Fixcar price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ZonedDateTime getDateFixing() {
        return dateFixing;
    }

    public Fixcar dateFixing(ZonedDateTime dateFixing) {
        this.dateFixing = dateFixing;
        return this;
    }

    public void setDateFixing(ZonedDateTime dateFixing) {
        this.dateFixing = dateFixing;
    }

    public String getDescription() {
        return description;
    }

    public Fixcar description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Autopark> getAutoparks() {
        return autoparks;
    }

    public Fixcar autoparks(Set<Autopark> autoparks) {
        this.autoparks = autoparks;
        return this;
    }

    public Fixcar addAutoparks(Autopark autopark) {
        this.autoparks.add(autopark);
        autopark.getFixcars().add(this);
        return this;
    }

    public Fixcar removeAutoparks(Autopark autopark) {
        this.autoparks.remove(autopark);
        autopark.getFixcars().remove(this);
        return this;
    }

    public void setAutoparks(Set<Autopark> autoparks) {
        this.autoparks = autoparks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fixcar)) {
            return false;
        }
        return id != null && id.equals(((Fixcar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Fixcar{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", dateFixing='" + getDateFixing() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
