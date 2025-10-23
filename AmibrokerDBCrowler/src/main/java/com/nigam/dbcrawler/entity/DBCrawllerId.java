package com.nigam.dbcrawler.entity;

import java.io.Serializable;
import java.util.Objects;

public class DBCrawllerId implements Serializable {
    private String pkField;
    private Double currentIndex;

    public DBCrawllerId() {}

    public DBCrawllerId(String pkField, Double currentIndex) {
        this.pkField = pkField;
        this.currentIndex = currentIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DBCrawllerId that)) return false;
        return Objects.equals(pkField, that.pkField) &&
                Objects.equals(currentIndex, that.currentIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkField, currentIndex);
    }
}
