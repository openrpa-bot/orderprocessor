package com.nigam.dbcrawler.entity.readonly;

import java.io.Serializable;
import java.util.Objects;

public class AgDayId implements Serializable {
    private Double candleDate;
    private String pk_field;

    public AgDayId() {}

    public AgDayId(Double candleDate, String pk_field) {
        this.candleDate = candleDate;
        this.pk_field = pk_field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgDayId)) return false;
        AgDayId that = (AgDayId) o;
        return Objects.equals(candleDate, that.candleDate)
                && Objects.equals(pk_field, that.pk_field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candleDate, pk_field);
    }
}
