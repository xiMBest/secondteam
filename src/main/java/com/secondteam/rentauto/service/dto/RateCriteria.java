package com.secondteam.rentauto.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.secondteam.rentauto.domain.enumeration.Ratestars;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.secondteam.rentauto.domain.Rate} entity. This class is used
 * in {@link com.secondteam.rentauto.web.rest.RateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RateCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Ratestars
     */
    public static class RatestarsFilter extends Filter<Ratestars> {

        public RatestarsFilter() {
        }

        public RatestarsFilter(RatestarsFilter filter) {
            super(filter);
        }

        @Override
        public RatestarsFilter copy() {
            return new RatestarsFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private RatestarsFilter raiting;

    private LongFilter autoparkId;

    public RateCriteria() {
    }

    public RateCriteria(RateCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.raiting = other.raiting == null ? null : other.raiting.copy();
        this.autoparkId = other.autoparkId == null ? null : other.autoparkId.copy();
    }

    @Override
    public RateCriteria copy() {
        return new RateCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public RatestarsFilter getRaiting() {
        return raiting;
    }

    public void setRaiting(RatestarsFilter raiting) {
        this.raiting = raiting;
    }

    public LongFilter getAutoparkId() {
        return autoparkId;
    }

    public void setAutoparkId(LongFilter autoparkId) {
        this.autoparkId = autoparkId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RateCriteria that = (RateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(raiting, that.raiting) &&
            Objects.equals(autoparkId, that.autoparkId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        raiting,
        autoparkId
        );
    }

    @Override
    public String toString() {
        return "RateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (raiting != null ? "raiting=" + raiting + ", " : "") +
                (autoparkId != null ? "autoparkId=" + autoparkId + ", " : "") +
            "}";
    }

}
