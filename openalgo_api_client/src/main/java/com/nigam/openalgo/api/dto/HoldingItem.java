package com.nigam.openalgo.api.dto;

import lombok.Data;

/**
 * Represents a single holding entry from OpenAlgo.
 */
@Data
public class HoldingItem {

    private String symbol;
    private String exchange;
    private String product;
    private double average_price;
    private double quantity;
    private double pnl;
    private double pnlpercent;
}
