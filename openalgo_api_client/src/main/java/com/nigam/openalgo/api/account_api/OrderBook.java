package com.nigam.openalgo.api.account_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.nigam.openalgo.api.communicator.APICallers;
import com.nigam.openalgo.api.payload.TradeOrder;
import com.nigam.openalgo.api.urlcreator.URLCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class OrderBook {
    String API_ENDPOINT = "api/v1/orderebook";
    public JsonNode sendQuery(String serverIP, String serverPort, String apiKey) throws IOException {
        log.trace("Ping sent to server " + serverIP + " on port " + serverPort + " with API key " + apiKey);

        String url = URLCreator.createURL("false", serverIP, serverPort, API_ENDPOINT);
        log.trace("Request Url :" + url);

        TradeOrder partialOrder = new TradeOrder.Builder()
                .apikey(apiKey)
                .build();
        String jsonRequest =  partialOrder.toJson();
        log.trace("Request Payload :" + jsonRequest);
        return APICallers.CallAPI(url, jsonRequest) ;
    }
}
