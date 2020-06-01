package com.secondteam.rentauto.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.secondteam.rentauto.domain.enumeration.CarType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.secondteam.rentauto.domain.Reservecar} entity. This class is used
 * in {@link com.secondteam.rentauto.web.rest.ReservecarResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /reservecars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReservecarCriteria implements Serializable, Criteria {
    /**
     * Class for filtering CarType
     */
    public static class CarTypeFilter extends Filter<CarType> {

        public CarTypeFilter() {
        }

        public CarTypeFilter(CarTypeFilter filter) {
            super(filter);
        }

        @Override
        public CarTypeFilter copy() {
            return new CarTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private CarTypeFilter type;

    private IntegerFilter totalPrice;

    private ZonedDateTimeFilter datePickUP;

    private ZonedDateTimeFilter dateDropDown;

    private StringFilter description;

    private LongFilter customerId;

    public ReservecarCriteria() {
    }

    public ReservecarCriteria(ReservecarCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.totalPrice = other.totalPrice == null ? null : other.totalPrice.copy();
        this.datePickUP = other.datePickUP == null ? null : other.datePickUP.copy();
        this.dateDropDown = other.dateDropDown == null ? null : other.dateDropDown.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public ReservecarCriteria copy() {
        return new ReservecarCriteria(this);
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

    public CarTypeFilter getType() {
        return type;
    }

    public void setType(CarTypeFilter type) {
        this.type = type;
    }

    public IntegerFilter getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(IntegerFilter totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ZonedDateTimeFilter getDatePickUP() {
        return datePickUP;
    }

    public void setDatePickUP(ZonedDateTimeFilter datePickUP) {
        this.datePickUP = datePickUP;
    }

    public ZonedDateTimeFilter getDateDropDown() {
        return dateDropDown;
    }

    public void setDateDropDown(ZonedDateTimeFilter dateDropDown) {
        this.dateDropDown = dateDropDown;
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
        final ReservecarCriteria that = (ReservecarCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(totalPrice, that.totalPrice) &&
            Objects.equals(datePickUP, that.datePickUP) &&
            Objects.equals(dateDropDown, that.dateDropDown) &&
            Objects.equals(description, that.description) &&
            Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        type,
        totalPrice,
        datePickUP,
        dateDropDown,
        description,
        customerId
        );
    }

    @Override
    public String toString() {
        return "ReservecarCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (totalPrice != null ? "totalPrice=" + totalPrice + ", " : "") +
                (datePickUP != null ? "datePickUP=" + datePickUP + ", " : "") +
                (dateDropDown != null ? "dateDropDown=" + dateDropDown + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }

}
