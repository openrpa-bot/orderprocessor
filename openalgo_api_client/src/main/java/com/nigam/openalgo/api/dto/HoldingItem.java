package com.nigam.openalgo.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HoldingItem {

    private String symbol;
    private String exchange;
    private String product;

    @JsonProperty(defaultValue = "0.0")
    private double average_price;  // default 0.0 if missing

    @JsonProperty(defaultValue = "0")
    private double quantity;       // default 0 if missing

    @JsonProperty(defaultValue = "0.0")
    private double pnl;

    @JsonProperty(defaultValue = "0.0")
    private double pnlpercent;
}
