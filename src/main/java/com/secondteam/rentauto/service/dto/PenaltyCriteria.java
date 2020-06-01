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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.secondteam.rentauto.domain.Penalty} entity. This class is used
 * in {@link com.secondteam.rentauto.web.rest.PenaltyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /penalties?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PenaltyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter datePenalty;

    private IntegerFilter price;

    private StringFilter description;

    private LongFilter customerId;

    public PenaltyCriteria() {
    }

    public PenaltyCriteria(PenaltyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.datePenalty = other.datePenalty == null ? null : other.datePenalty.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public PenaltyCriteria copy() {
        return new PenaltyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getDatePenalty() {
        return datePenalty;
    }

    public void setDatePenalty(ZonedDateTimeFilter datePenalty) {
        this.datePenalty = datePenalty;
    }

    public IntegerFilter getPrice() {
        return price;
    }

    public void setPrice(IntegerFilter price) {
        this.price = price;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PenaltyCriteria that = (PenaltyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(datePenalty, that.datePenalty) &&
            Objects.equals(price, that.price) &&
            Objects.equals(description, that.description) &&
            Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        datePenalty,
        price,
        description,
        customerId
        );
    }

    @Override
    public String toString() {
        return "PenaltyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (datePenalty != null ? "datePenalty=" + datePenalty + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }

}
