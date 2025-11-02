package com.nigam.openalgo.api.urlcreator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Builder for constructing ticker API URLs safely, skipping null or empty params.
 * Example:
 * http://127.0.0.1:5000/api/v1/ticker/NSE:RELIANCE?apikey=your_api_key_here&interval=5m
 */
public class TickerUrlBuilder {
    private final String baseUrl;
    private String symbol; // e.g., NSE:RELIANCE
    private final Map<String, String> queryParams = new LinkedHashMap<>();

    public TickerUrlBuilder(String baseUrl) {
        this.baseUrl = baseUrl.endsWith("/")
                ? baseUrl.substring(0, baseUrl.length() - 1)
                : baseUrl;
    }

    public TickerUrlBuilder symbol(String exchange, String ticker) {
        if (isValid(exchange) && isValid(ticker)) {
            // don't URL-encode the colon — keep raw for path
            this.symbol = exchange + ":" + ticker;
        }
        return this;
    }

    public TickerUrlBuilder param(String key, String value) {
        if (isValid(key) && isValid(value)) {
            queryParams.put(key, value);
        }
        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder(baseUrl);

        if (isValid(symbol)) {
            sb.append("/").append(symbol); // ✅ do not encode here
        }

        if (!queryParams.isEmpty()) {
            sb.append("?");
            queryParams.forEach((key, value) ->
                    sb.append(encode(key))
                            .append("=")
                            .append(encode(value))
                            .append("&")
            );
            sb.setLength(sb.length() - 1); // remove last &
        }

        return sb.toString();
    }

    private boolean isValid(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to encode URL component: " + value, e);
        }
    }

    public static TickerUrlBuilder fromBase(String baseUrl) {
        return new TickerUrlBuilder(baseUrl);
    }
}
