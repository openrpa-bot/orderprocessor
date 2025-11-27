package com.nigam.dbcrawler.entity.readonly;

import java.io.Serializable;
import java.util.Objects;

public class AgDayId implements Serializable {
    private Double candleDate;
    private String pkField;

    public AgDayId() {}

    public AgDayId(Double candleDate, String pkField) {
        this.candleDate = candleDate;
        this.pkField = pkField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgDayId)) return false;
        AgDayId that = (AgDayId) o;
        return Objects.equals(candleDate, that.candleDate)
                && Objects.equals(pkField, that.pkField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candleDate, pkField);
    }
}
