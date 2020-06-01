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

/**
 * Criteria class for the {@link com.secondteam.rentauto.domain.Autopark} entity. This class is used
 * in {@link com.secondteam.rentauto.web.rest.AutoparkResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /autoparks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AutoparkCriteria implements Serializable, Criteria {
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

    private StringFilter mark;

    private StringFilter model;

    private IntegerFilter prePrice;

    private StringFilter color;

    private CarTypeFilter type;

    private IntegerFilter pledge;

    private BooleanFilter statusAvaileble;

    private LongFilter ratesId;

    private LongFilter fixcarsId;

    public AutoparkCriteria() {
    }

    public AutoparkCriteria(AutoparkCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.mark = other.mark == null ? null : other.mark.copy();
        this.model = other.model == null ? null : other.model.copy();
        this.prePrice = other.prePrice == null ? null : other.prePrice.copy();
        this.color = other.color == null ? null : other.color.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.pledge = other.pledge == null ? null : other.pledge.copy();
        this.statusAvaileble = other.statusAvaileble == null ? null : other.statusAvaileble.copy();
        this.ratesId = other.ratesId == null ? null : other.ratesId.copy();
        this.fixcarsId = other.fixcarsId == null ? null : other.fixcarsId.copy();
    }

    @Override
    public AutoparkCriteria copy() {
        return new AutoparkCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMark() {
        return mark;
    }

    public void setMark(StringFilter mark) {
        this.mark = mark;
    }

    public StringFilter getModel() {
        return model;
    }

    public void setModel(StringFilter model) {
        this.model = model;
    }

    public IntegerFilter getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(IntegerFilter prePrice) {
        this.prePrice = prePrice;
    }

    public StringFilter getColor() {
        return color;
    }

    public void setColor(StringFilter color) {
        this.color = color;
    }

    public CarTypeFilter getType() {
        return type;
    }

    public void setType(CarTypeFilter type) {
        this.type = type;
    }

    public IntegerFilter getPledge() {
        return pledge;
    }

    public void setPledge(IntegerFilter pledge) {
        this.pledge = pledge;
    }

    public BooleanFilter getStatusAvaileble() {
        return statusAvaileble;
    }

    public void setStatusAvaileble(BooleanFilter statusAvaileble) {
        this.statusAvaileble = statusAvaileble;
    }

    public LongFilter getRatesId() {
        return ratesId;
    }

    public void setRatesId(LongFilter ratesId) {
        this.ratesId = ratesId;
    }

    public LongFilter getFixcarsId() {
        return fixcarsId;
    }

    public void setFixcarsId(LongFilter fixcarsId) {
        this.fixcarsId = fixcarsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AutoparkCriteria that = (AutoparkCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(mark, that.mark) &&
            Objects.equals(model, that.model) &&
            Objects.equals(prePrice, that.prePrice) &&
            Objects.equals(color, that.color) &&
            Objects.equals(type, that.type) &&
            Objects.equals(pledge, that.pledge) &&
            Objects.equals(statusAvaileble, that.statusAvaileble) &&
            Objects.equals(ratesId, that.ratesId) &&
            Objects.equals(fixcarsId, that.fixcarsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        mark,
        model,
        prePrice,
        color,
        type,
        pledge,
        statusAvaileble,
        ratesId,
        fixcarsId
        );
    }

    @Override
    public String toString() {
        return "AutoparkCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (mark != null ? "mark=" + mark + ", " : "") +
                (model != null ? "model=" + model + ", " : "") +
                (prePrice != null ? "prePrice=" + prePrice + ", " : "") +
                (color != null ? "color=" + color + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (pledge != null ? "pledge=" + pledge + ", " : "") +
                (statusAvaileble != null ? "statusAvaileble=" + statusAvaileble + ", " : "") +
                (ratesId != null ? "ratesId=" + ratesId + ", " : "") +
                (fixcarsId != null ? "fixcarsId=" + fixcarsId + ", " : "") +
            "}";
    }

}
