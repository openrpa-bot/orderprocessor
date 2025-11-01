package com.nigam.openalgo.api.orders_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.nigam.openalgo.api.communicator.APICallers;
import com.nigam.openalgo.api.payload.TradeOrder;
import com.nigam.openalgo.api.urlcreator.URLCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class PlaceOrder {
    String API_ENDPOINT = "api/v1/placeorder";

    public JsonNode sendQuery(String serverIP,
                              String serverPort,
                              String apiKey,
                              String strategy,
                              String symbol,
                              String action,
                              String exchange,
                              String pricetype,
                              String product,
                              String quantity
                        ) throws IOException {
        log.trace("Ping sent to server " + serverIP + " on port " + serverPort + " with API key " + apiKey);

        String url = URLCreator.createURL("false", serverIP, serverPort, API_ENDPOINT);
        log.trace("Request Url :" + url);

        TradeOrder partialOrder = new TradeOrder.Builder()
                .apikey(apiKey)
                .strategy(strategy)
                .symbol(symbol)
                .action(action)
                .exchange(exchange)
                .pricetype(pricetype)
                .product(product)
                .quantity(quantity)
                .build();
        String jsonRequest =  partialOrder.toJson();
        log.trace("Request Payload :" + jsonRequest);
        return APICallers.CallAPI(url, jsonRequest) ;
    }
}
