package com.nigam.dbcrawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DBCrawllerId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pk_field;
    private Double candleDate;
}
