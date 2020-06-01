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
 * Criteria class for the {@link com.secondteam.rentauto.domain.Fixcar} entity. This class is used
 * in {@link com.secondteam.rentauto.web.rest.FixcarResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fixcars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FixcarCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter price;

    private ZonedDateTimeFilter dateFixing;

    private StringFilter description;

    private LongFilter autoparksId;

    public FixcarCriteria() {
    }

    public FixcarCriteria(FixcarCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.dateFixing = other.dateFixing == null ? null : other.dateFixing.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.autoparksId = other.autoparksId == null ? null : other.autoparksId.copy();
    }

    @Override
    public FixcarCriteria copy() {
        return new FixcarCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getPrice() {
        return price;
    }

    public void setPrice(IntegerFilter price) {
        this.price = price;
    }

    public ZonedDateTimeFilter getDateFixing() {
        return dateFixing;
    }

    public void setDateFixing(ZonedDateTimeFilter dateFixing) {
        this.dateFixing = dateFixing;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getAutoparksId() {
        return autoparksId;
    }

    public void setAutoparksId(LongFilter autoparksId) {
        this.autoparksId = autoparksId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FixcarCriteria that = (FixcarCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(price, that.price) &&
            Objects.equals(dateFixing, that.dateFixing) &&
            Objects.equals(description, that.description) &&
            Objects.equals(autoparksId, that.autoparksId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        price,
        dateFixing,
        description,
        autoparksId
        );
    }

    @Override
    public String toString() {
        return "FixcarCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (dateFixing != null ? "dateFixing=" + dateFixing + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (autoparksId != null ? "autoparksId=" + autoparksId + ", " : "") +
            "}";
    }

}
