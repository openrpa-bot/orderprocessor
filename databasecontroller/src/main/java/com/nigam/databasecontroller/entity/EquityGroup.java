package com.nigam.databasecontroller.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "\"Equity_Group\"")
@IdClass(EquityGroup.EquityGroupId.class)
public class EquityGroup {

    @Id
    @Column(name = "\"GroupName\"")
    private String groupName;

    @Id
    @Column(name = "\"tickerName\"")
    private String tickerName;

    @Column(name = "\"GroupType\"")
    private String groupType;

    public EquityGroup() {}

    public EquityGroup(String groupName, String tickerName, String groupType) {
        this.groupName = groupName;
        this.tickerName = tickerName;
        this.groupType = groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTickerName() {
        return tickerName;
    }

    public void setTickerName(String tickerName) {
        this.tickerName = tickerName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    // ---- Composite Key Class ----
    public static class EquityGroupId implements Serializable {
        private String groupName;
        private String tickerName;

        public EquityGroupId() {}
        public EquityGroupId(String groupName, String tickerName) {
            this.groupName = groupName;
            this.tickerName = tickerName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EquityGroupId that)) return false;
            return Objects.equals(groupName, that.groupName)
                    && Objects.equals(tickerName, that.tickerName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(groupName, tickerName);
        }
    }
}
