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
public class CancelAllOrder {
    String API_ENDPOINT = "api/v1/cancelallorder";

    public JsonNode sendQuery(String serverIP, String serverPort, TradeOrder tradeOrder) throws IOException {
        log.trace("CancelAllOrder sent to server " + serverIP + " on port " + serverPort );

        String url = URLCreator.createURL("false", serverIP, serverPort, API_ENDPOINT);
        log.trace("CancelAllOrder Request Url :" + url);

        String jsonRequest =  tradeOrder.toJson();
        log.trace("CancelAllOrder Request Payload :\n\r" + jsonRequest);

        return APICallers.CallAPI(url, jsonRequest) ;
    }

    public JsonNode sendQuery(String serverIP,
                              String serverPort,
                              String apiKey,
                              String strategy
                        ) throws IOException {
        log.trace("CancelAllOrder sent to server " + serverIP + " on port " + serverPort + " with API key " + apiKey);

        TradeOrder tradeOrder = new TradeOrder.Builder()
                .apikey(apiKey)
                .strategy(strategy)
                .build();

        return sendQuery(serverIP, serverPort, tradeOrder) ;
    }
}
