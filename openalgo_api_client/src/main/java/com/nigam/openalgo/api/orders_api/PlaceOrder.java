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

    public JsonNode sendQuery(String serverIP, String serverPort, TradeOrder tradeOrder) throws IOException {
        log.trace("PlaceOrder sent to server " + serverIP + " on port " + serverPort );

        String url = URLCreator.createURL("false", serverIP, serverPort, API_ENDPOINT);
        log.trace("PlaceOrder Request Url :" + url);

        String jsonRequest =  tradeOrder.toJson();
        log.trace("PlaceOrder Request Payload :\n\r" + jsonRequest);

        return APICallers.CallAPI(url, jsonRequest) ;
    }

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
        log.trace("PlaceOrder sent to server " + serverIP + " on port " + serverPort + " with API key " + apiKey);

        TradeOrder tradeOrder = new TradeOrder.Builder()
                .apikey(apiKey)
                .strategy(strategy)
                .symbol(symbol)
                .action(action)
                .exchange(exchange)
                .pricetype(pricetype)
                .product(product)
                .quantity(quantity)
                .build();

        return sendQuery(serverIP, serverPort, tradeOrder) ;
    }
}
