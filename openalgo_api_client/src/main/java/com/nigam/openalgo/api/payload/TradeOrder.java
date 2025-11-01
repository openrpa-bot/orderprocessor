package com.nigam.openalgo.api.payload;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a trade order payload that supports both single and multi-order formats,
 * while allowing flexible quantity types (String or Integer).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeOrder {

    private final String apikey;
    private final Boolean mode;
    private final String strategy;

    // ✅ Single order fields
    private final String symbol;
    private final String exchange;
    private final String action;
    private final String orderid;
    private final String product;
    private final String pricetype;
    private final String price;
    private final Object quantity; // <-- updated type
    private final String position_size;
    private final String disclosed_quantity;
    private final String trigger_price;
    private final String underlying;
    private final Integer strike_int;
    private final String offset;
    private final String option_type;
    private final String splitsize;
    private final String interval;
    private final String start_date;
    private final String end_date;
    private final String instrumenttype;
    private final String expiry_date;
    private final String query;


    // ✅ Multi-order support
    private final List<Order> orders;

    private TradeOrder(Builder builder) {
        this.apikey = builder.apikey;
        this.mode = builder.mode;
        this.strategy = builder.strategy;
        this.symbol = builder.symbol;
        this.exchange = builder.exchange;
        this.action = builder.action;
        this.orderid = builder.orderid;
        this.product = builder.product;
        this.pricetype = builder.pricetype;
        this.price = builder.price;
        this.quantity = builder.quantity;  // can be string or number
        this.position_size = builder.position_size;
        this.disclosed_quantity = builder.disclosed_quantity;
        this.trigger_price = builder.trigger_price;
        this.underlying = builder.underlying;
        this.strike_int = builder.strike_int;
        this.offset = builder.offset;
        this.option_type = builder.option_type;
        this.orders = builder.orders.isEmpty() ? null : builder.orders;
        this.splitsize = builder.splitsize;
        this.interval = builder.interval;
        this.start_date = builder.start_date;
        this.end_date = builder.end_date;
        this.instrumenttype = builder.instrumenttype;
        this.expiry_date = builder.expiry_date;
        this.query = builder.query;

    }

    /** Represents an order in the 'orders' array */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Order {
        private final String symbol;
        private final String exchange;
        private final String action;
        private final Object quantity;  // <-- updated type
        private final String pricetype;
        private final String product;
        private final String orderid;
        private final String price;
        private final String position_size;
        private final String disclosed_quantity;
        private final String trigger_price;
        private final String underlying;
        private final Integer strike_int;
        private final String offset;
        private final String option_type;
        private final String splitsize;
        private final String interval;
        private final String start_date;
        private final String end_date;
        private final String instrumenttype;
        private final String expiry_date;
        private final String query;


        private Order(OrderBuilder builder) {
            this.symbol = builder.symbol;
            this.exchange = builder.exchange;
            this.action = builder.action;
            this.quantity = builder.quantity;
            this.pricetype = builder.pricetype;
            this.product = builder.product;
            this.orderid = builder.orderid;
            this.price = builder.price;
            this.position_size = builder.position_size;
            this.disclosed_quantity = builder.disclosed_quantity;
            this.trigger_price = builder.trigger_price;
            this.underlying = builder.underlying;
            this.strike_int = builder.strike_int;
            this.offset = builder.offset;
            this.option_type = builder.option_type;
            this.splitsize = builder.splitsize;
            this.interval = builder.interval;
            this.start_date = builder.start_date;
            this.end_date = builder.end_date;
            this.instrumenttype = builder.instrumenttype;
            this.expiry_date = builder.expiry_date;
            this.query = builder.query;
        }

        public static class OrderBuilder {
            private String symbol;
            private String exchange;
            private String action;
            private Object quantity;
            private String pricetype;
            private String product;
            private String orderid;
            private String price;
            private String position_size;
            private String disclosed_quantity;
            private String trigger_price;
            private String underlying;
            private Integer strike_int;
            private String offset;
            private String option_type;
            private String splitsize;
            private String interval;
            private String start_date;
            private String end_date;
            private String instrumenttype;
            private String expiry_date;
            private String query;

            public OrderBuilder symbol(String symbol) { this.symbol = symbol; return this; }
            public OrderBuilder exchange(String exchange) { this.exchange = exchange; return this; }
            public OrderBuilder action(String action) { this.action = action; return this; }
            public OrderBuilder quantity(Object quantity) { this.quantity = quantity; return this; }
            public OrderBuilder pricetype(String pricetype) { this.pricetype = pricetype; return this; }
            public OrderBuilder product(String product) { this.product = product; return this; }
            public OrderBuilder orderid(String orderid) { this.orderid = orderid; return this; }
            public OrderBuilder price(String price) { this.price = price; return this; }
            public OrderBuilder position_size(String position_size) { this.position_size = position_size; return this; }
            public OrderBuilder disclosed_quantity(String disclosed_quantity) { this.disclosed_quantity = disclosed_quantity; return this; }
            public OrderBuilder trigger_price(String trigger_price) { this.trigger_price = trigger_price; return this; }
            public OrderBuilder underlying(String underlying) { this.underlying = underlying; return this; }
            public OrderBuilder offset(String offset) { this.offset = offset; return this; }
            public OrderBuilder option_type(String option_type) { this.option_type = option_type; return this; }
            public OrderBuilder splitsize(String splitsize) { this.splitsize = splitsize; return this; }
            public OrderBuilder interval(String interval) { this.interval = interval; return this; }
            public OrderBuilder start_date(String start_date) { this.start_date = start_date; return this; }
            public OrderBuilder end_date(String end_date) { this.end_date = end_date; return this; }
            public OrderBuilder instrumenttype(String instrumenttype) { this.instrumenttype = instrumenttype; return this; }
            public OrderBuilder expiry_date(String expiry_date) { this.expiry_date = expiry_date; return this; }
            public OrderBuilder query(String query) { this.query = query; return this; }

            public OrderBuilder strike_int(String strike_int) {
                if (strike_int != null) this.strike_int = Integer.parseInt(strike_int);
                return this;
            }

            public Order build() { return new Order(this); }
        }
    }

    public static class Builder {
        private String apikey;
        private Boolean mode;
        private String strategy;

        private String symbol;
        private String exchange;
        private String action;
        private String orderid;
        private String product;
        private String pricetype;
        private String price;
        private Object quantity;  // <-- updated type
        private String position_size;
        private String disclosed_quantity;
        private String trigger_price;
        private String underlying;
        private Integer strike_int;
        private String offset;
        private String option_type;
        private String splitsize;
        private String interval;
        private String start_date;
        private String end_date;
        private String instrumenttype;
        private String expiry_date;
        private String query;

        private final List<Order> orders = new ArrayList<>();

        public Builder apikey(String apikey) { this.apikey = apikey; return this; }

        public Builder mode(String mode) {
            if (mode != null) {
                if (mode.equalsIgnoreCase("true")) this.mode = true;
                else if (mode.equalsIgnoreCase("false")) this.mode = false;
            }
            return this;
        }

        public Builder strike_int(String strike_int) {
            if (strike_int != null) this.strike_int = Integer.parseInt(strike_int);
            return this;
        }

        public Builder strategy(String strategy) { this.strategy = strategy; return this; }
        public Builder symbol(String symbol) { this.symbol = symbol; return this; }
        public Builder exchange(String exchange) { this.exchange = exchange; return this; }
        public Builder action(String action) { this.action = action; return this; }
        public Builder orderid(String orderid) { this.orderid = orderid; return this; }
        public Builder product(String product) { this.product = product; return this; }
        public Builder pricetype(String pricetype) { this.pricetype = pricetype; return this; }
        public Builder price(String price) { this.price = price; return this; }
        public Builder splitsize(String splitsize) { this.splitsize = splitsize; return this; }
        public Builder interval(String interval) { this.interval = interval; return this; }
        public Builder start_date(String start_date) { this.start_date = start_date; return this; }
        public Builder end_date(String end_date) { this.end_date = end_date; return this; }


        // ✅ Accepts either string or integer quantity
        public Builder quantity(Object quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder position_size(String position_size) { this.position_size = position_size; return this; }
        public Builder disclosed_quantity(String disclosed_quantity) { this.disclosed_quantity = disclosed_quantity; return this; }
        public Builder trigger_price(String trigger_price) { this.trigger_price = trigger_price; return this; }
        public Builder underlying(String underlying) { this.underlying = underlying; return this; }
        public Builder offset(String offset) { this.offset = offset; return this; }
        public Builder option_type(String option_type) { this.option_type = option_type; return this; }
        public Builder instrumenttype(String instrumenttype) { this.instrumenttype = instrumenttype; return this; }
        public Builder expiry_date(String expiry_date) { this.expiry_date = expiry_date; return this; }
        public Builder query(String query) { this.query = query; return this; }

        public Builder addOrder(Order order) {
            if (order != null) this.orders.add(order);
            return this;
        }

        public TradeOrder build() { return new TradeOrder(this); }
    }

    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert TradeOrder to JSON", e);
        }
    }
}
