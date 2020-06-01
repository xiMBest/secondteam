package com.secondteam.rentauto.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.secondteam.rentauto.domain.Customer} entity. This class is used
 * in {@link com.secondteam.rentauto.web.rest.CustomerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter surname;

    private IntegerFilter years;

    private StringFilter email;

    private StringFilter phone;

    private StringFilter adress;

    private LongFilter reservecarsId;

    private LongFilter penaltysId;

    private LongFilter insuarencesId;

    public CustomerCriteria() {
    }

    public CustomerCriteria(CustomerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.surname = other.surname == null ? null : other.surname.copy();
        this.years = other.years == null ? null : other.years.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.adress = other.adress == null ? null : other.adress.copy();
        this.reservecarsId = other.reservecarsId == null ? null : other.reservecarsId.copy();
        this.penaltysId = other.penaltysId == null ? null : other.penaltysId.copy();
        this.insuarencesId = other.insuarencesId == null ? null : other.insuarencesId.copy();
    }

    @Override
    public CustomerCriteria copy() {
        return new CustomerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSurname() {
        return surname;
    }

    public void setSurname(StringFilter surname) {
        this.surname = surname;
    }

    public IntegerFilter getYears() {
        return years;
    }

    public void setYears(IntegerFilter years) {
        this.years = years;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getAdress() {
        return adress;
    }

    public void setAdress(StringFilter adress) {
        this.adress = adress;
    }

    public LongFilter getReservecarsId() {
        return reservecarsId;
    }

    public void setReservecarsId(LongFilter reservecarsId) {
        this.reservecarsId = reservecarsId;
    }

    public LongFilter getPenaltysId() {
        return penaltysId;
    }

    public void setPenaltysId(LongFilter penaltysId) {
        this.penaltysId = penaltysId;
    }

    public LongFilter getInsuarencesId() {
        return insuarencesId;
    }

    public void setInsuarencesId(LongFilter insuarencesId) {
        this.insuarencesId = insuarencesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerCriteria that = (CustomerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(surname, that.surname) &&
            Objects.equals(years, that.years) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(adress, that.adress) &&
            Objects.equals(reservecarsId, that.reservecarsId) &&
            Objects.equals(penaltysId, that.penaltysId) &&
            Objects.equals(insuarencesId, that.insuarencesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        surname,
        years,
        email,
        phone,
        adress,
        reservecarsId,
        penaltysId,
        insuarencesId
        );
    }

    @Override
    public String toString() {
        return "CustomerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (surname != null ? "surname=" + surname + ", " : "") +
                (years != null ? "years=" + years + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (adress != null ? "adress=" + adress + ", " : "") +
                (reservecarsId != null ? "reservecarsId=" + reservecarsId + ", " : "") +
                (penaltysId != null ? "penaltysId=" + penaltysId + ", " : "") +
                (insuarencesId != null ? "insuarencesId=" + insuarencesId + ", " : "") +
            "}";
    }

}
