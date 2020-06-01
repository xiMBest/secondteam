package com.secondteam.rentauto.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.secondteam.rentauto.domain.enumeration.InsuarenceType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.secondteam.rentauto.domain.Insuarence} entity. This class is used
 * in {@link com.secondteam.rentauto.web.rest.InsuarenceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /insuarences?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InsuarenceCriteria implements Serializable, Criteria {
    /**
     * Class for filtering InsuarenceType
     */
    public static class InsuarenceTypeFilter extends Filter<InsuarenceType> {

        public InsuarenceTypeFilter() {
        }

        public InsuarenceTypeFilter(InsuarenceTypeFilter filter) {
            super(filter);
        }

        @Override
        public InsuarenceTypeFilter copy() {
            return new InsuarenceTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter dateApply;

    private ZonedDateTimeFilter dateEnd;

    private IntegerFilter cost;

    private InsuarenceTypeFilter type;

    private LongFilter customerId;

    public InsuarenceCriteria() {
    }

    public InsuarenceCriteria(InsuarenceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateApply = other.dateApply == null ? null : other.dateApply.copy();
        this.dateEnd = other.dateEnd == null ? null : other.dateEnd.copy();
        this.cost = other.cost == null ? null : other.cost.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public InsuarenceCriteria copy() {
        return new InsuarenceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getDateApply() {
        return dateApply;
    }

    public void setDateApply(ZonedDateTimeFilter dateApply) {
        this.dateApply = dateApply;
    }

    public ZonedDateTimeFilter getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(ZonedDateTimeFilter dateEnd) {
        this.dateEnd = dateEnd;
    }

    public IntegerFilter getCost() {
        return cost;
    }

    public void setCost(IntegerFilter cost) {
        this.cost = cost;
    }

    public InsuarenceTypeFilter getType() {
        return type;
    }

    public void setType(InsuarenceTypeFilter type) {
        this.type = type;
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
        final InsuarenceCriteria that = (InsuarenceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dateApply, that.dateApply) &&
            Objects.equals(dateEnd, that.dateEnd) &&
            Objects.equals(cost, that.cost) &&
            Objects.equals(type, that.type) &&
            Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dateApply,
        dateEnd,
        cost,
        type,
        customerId
        );
    }

    @Override
    public String toString() {
        return "InsuarenceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateApply != null ? "dateApply=" + dateApply + ", " : "") +
                (dateEnd != null ? "dateEnd=" + dateEnd + ", " : "") +
                (cost != null ? "cost=" + cost + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }

}
