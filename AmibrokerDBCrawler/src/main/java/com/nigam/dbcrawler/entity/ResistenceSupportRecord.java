package com.nigam.dbcrawler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "\"ResistenceSupport_Daily\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResistenceSupportRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    // Composite primary key handled with @IdClass
    @Id
    @Column(name = "\"PK_Field\"", nullable = false)
    private String pk_field;

    @Id
    @Column(name = "\"candle_Date\"", nullable = false)
    private Double candleDate;

    @Column(name = "\"CurrentIndex\"")
    private Double currentIndex;

    @Column(name = "\"Close\"")
    private Double close;

    @Column(name = "\"High\"")
    private Double high;

    @Column(name = "\"Low\"")
    private Double low;

    @Column(name = "\"Open\"")
    private Double open;

    @Column(name = "\"Volume\"")
    private Long volume;

    @Column(name = "\"candle_DateTime\"")
    private Timestamp candleDateTime;

    @Column(name = "\"candle_Date_Next\"")
    private Double candleDateNext;

    @Column(name = "\"pushTimeStamp\"")
    private Timestamp pushTimeStamp;

    @Column(name = "\"tickerName\"")
    private String tickerName;

    @Column(name = "\"tickerType\"")
    private String tickerType;

    @Column(name = "\"uniqueID\"")
    private String uniqueId;

    @Column(name = "\"VSA_Spread_MA\"")
    private Double vsaSpreadMa;

    @Column(name = "\"VSA_Volume_MA\"")
    private Double vsaVolumeMa;

    @Column(name = "\"candle_Previous_Date\"")
    private Double candlePreviousDate;

    @Column(name = "\"candle_Time\"")
    private Double candleTime;

    @Column(name = "\"dataType\"")
    private String dataType;
}
