package com.nigam.openalgo.api.data_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.nigam.openalgo.api.communicator.APICallers;
import com.nigam.openalgo.api.payload.TradeOrder;
import com.nigam.openalgo.api.urlcreator.URLCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class Expiry {
    String API_ENDPOINT = "api/v1/expiry";

    public JsonNode sendQuery(String serverIP, String serverPort, TradeOrder tradeOrder) throws IOException {
        log.trace("Expiry sent to server " + serverIP + " on port " + serverPort );

        String url = URLCreator.createURL("false", serverIP, serverPort, API_ENDPOINT);
        log.trace("Expiry Request Url :" + url);

        String jsonRequest =  tradeOrder.toJson();
        log.trace("Expiry Request Payload :\n\r" + jsonRequest);

        return APICallers.CallAPI(url, jsonRequest) ;
    }

    public JsonNode sendQuery(String serverIP,
                              String serverPort,
                              String apiKey,
                              String symbol,
                              String exchange,
                              String instrumenttype
                        ) throws IOException {
        log.trace("Expiry sent to server " + serverIP + " on port " + serverPort + " with API key " + apiKey);

        TradeOrder tradeOrder = new TradeOrder.Builder()
                .apikey(apiKey)
                .symbol(symbol)
                .exchange(exchange)
                .instrumenttype(instrumenttype)
                .build();

        return sendQuery(serverIP, serverPort, tradeOrder) ;
    }
}
