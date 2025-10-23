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
@Table(name = "\"dbcrawller_ResistenceSupport\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DBCrawller_ResistenceSupport implements Serializable {

    private static final long serialVersionUID = 1L;

    // Composite primary key handled with @IdClass
    @Id
    @Column(name = "\"PK_Field\"", nullable = false)
    private String pkField;

    @Id
    @Column(name = "\"CurrentIndex\"", nullable = false)
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

    @Column(name = "\"candle_Date\"")
    private Double candleDate;

    @Column(name = "\"candle_DateTime\"")
    private Timestamp candleDateTime;

    @Column(name = "\"tickerName\"")
    private String tickerName;

    @Column(name = "\"candle_Time\"")
    private Double candleTime;

    @Column(name = "\"Resistence1\"")
    private Double resistence1;

    @Column(name = "\"resistence_name_1\"")
    private String resistenceName1;

    @Column(name = "\"Resistence2\"")
    private Double resistence2;

    @Column(name = "\"resistence_name_2\"")
    private String resistenceName2;

    @Column(name = "\"Resistence3\"")
    private Double resistence3;

    @Column(name = "\"resistence_name_3\"")
    private String resistenceName3;

    @Column(name = "\"Resistence4\"")
    private Double resistence4;

    @Column(name = "\"resistence_name_4\"")
    private String resistenceName4;

    @Column(name = "\"Support1\"")
    private Double support1;

    @Column(name = "\"support_name_1\"")
    private String supportName1;

    @Column(name = "\"Support2\"")
    private Double support2;

    @Column(name = "\"support_name_2\"")
    private String supportName2;

    @Column(name = "\"Support3\"")
    private Double support3;

    @Column(name = "\"support_name_3\"")
    private String supportName3;

    @Column(name = "\"Support4\"")
    private Double support4;
}
