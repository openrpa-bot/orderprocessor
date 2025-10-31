package com.nigam.openalgo.api.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;

/**
 * Represents a trade order payload with selective JSON inclusion.
 * Only explicitly set fields appear in the final JSON.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeOrder {

    private final String apikey;
    private final String strategy;
    private final String symbol;
    private final String action;
    private final String exchange;
    private final String orderid;
    private final String product;
    private final String pricetype;
    private final String price;
    private final String quantity;
    private final String disclosed_quantity;
    private final String trigger_price;

    private TradeOrder(Builder builder) {
        this.apikey = builder.apikey;
        this.strategy = builder.strategy;
        this.symbol = builder.symbol;
        this.action = builder.action;
        this.exchange = builder.exchange;
        this.orderid = builder.orderid;
        this.product = builder.product;
        this.pricetype = builder.pricetype;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.disclosed_quantity = builder.disclosed_quantity;
        this.trigger_price = builder.trigger_price;
    }

    public static class Builder {
        private String apikey;
        private String strategy;
        private String symbol;
        private String action;
        private String exchange;
        private String orderid;
        private String product;
        private String pricetype;
        private String price;
        private String quantity;
        private String disclosed_quantity;
        private String trigger_price;

        public Builder apikey(String apikey) { this.apikey = apikey; return this; }
        public Builder strategy(String strategy) { this.strategy = strategy; return this; }
        public Builder symbol(String symbol) { this.symbol = symbol; return this; }
        public Builder action(String action) { this.action = action; return this; }
        public Builder exchange(String exchange) { this.exchange = exchange; return this; }
        public Builder orderid(String orderid) { this.orderid = orderid; return this; }
        public Builder product(String product) { this.product = product; return this; }
        public Builder pricetype(String pricetype) { this.pricetype = pricetype; return this; }
        public Builder price(String price) { this.price = price; return this; }
        public Builder quantity(String quantity) { this.quantity = quantity; return this; }
        public Builder disclosed_quantity(String disclosed_quantity) { this.disclosed_quantity = disclosed_quantity; return this; }
        public Builder trigger_price(String trigger_price) { this.trigger_price = trigger_price; return this; }

        public TradeOrder build() {
            return new TradeOrder(this);
        }
    }

    // ✅ Convert to JSON string — only non-null fields will be serialized

    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); // 👈 important
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert TradeOrder to JSON", e);
        }
    }

}
