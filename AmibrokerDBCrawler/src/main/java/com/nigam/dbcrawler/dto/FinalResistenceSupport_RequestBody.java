package com.nigam.dbcrawler.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FinalResistenceSupport_RequestBody {
    private String equityName;
    private Double candleDate;
    private Double candleTime;
    private Double timeframe;
}
