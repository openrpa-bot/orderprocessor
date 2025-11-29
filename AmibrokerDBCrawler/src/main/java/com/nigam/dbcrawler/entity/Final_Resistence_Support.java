package com.nigam.dbcrawler.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@IdClass(DBCrawllerId.class)
@Table(name = "\"op_resistence_support_default\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Final_Resistence_Support implements Serializable {

    private static final long serialVersionUID = 1L;

    // Composite primary key handled with @IdClass
    @Id
    @Column(name = "\"pk_field\"", nullable = false)
    private String pk_field;

    @Id
    @Column(name = "\"candle_date\"", nullable = false)
    private Double candleDate;

    @Column(name = "\"current_index\"")
    private Double currentIndex;

    @Column(name = "\"candle_datetime\"")
    private Timestamp candleDateTime;

    @Column(name = "\"ticker_name\"")
    private String tickerName;

    @Column(name = "\"candle_time\"")
    private Double candleTime;

    @Column(name = "\"close\"")
    private Double close;

    @Column(name = "\"long_1\"")
    private Double long_1;

    @Column(name = "\"long_2\"")
    private Double long_2;

    @Column(name = "\"sell_1\"")
    private Double sell_1;

    @Column(name = "\"sell_2\"")
    private Double sell_2;

    @Column(name = "\"long_sl_1\"")
    private Double long_sl_1;

    @Column(name = "\"long_sl_2\"")
    private Double long_sl_2;

    @Column(name = "\"short_1\"")
    private Double short_1;

    @Column(name = "\"short_2\"")
    private Double short_2;

    @Column(name = "\"cover_1\"")
    private Double cover_1;

    @Column(name = "\"cover_2\"")
    private Double cover_2;

    @Column(name = "\"short_sl_1\"")
    private Double short_sl_1;

    @Column(name = "\"short_sl_2\"")
    private Double short_sl_2;
}
