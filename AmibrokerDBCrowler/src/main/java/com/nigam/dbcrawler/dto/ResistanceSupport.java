package com.nigam.dbcrawler.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResistanceSupport {
    private String equityName;
    private Double candleDate;
    private Double resistanceSupport;
    private Double volume_Ratio;
    private Double Spread_Ratio;
    private Double Spread_Position;
    private String resistenceSupport_Comment;
}
