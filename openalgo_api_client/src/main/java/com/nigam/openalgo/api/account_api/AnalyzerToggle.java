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
public class AnalyzerToggle {

    String API_ENDPOINT = "api/v1/analyzer/toggle";

    public JsonNode sendQuery(String serverIP, String serverPort, TradeOrder tradeOrder) throws IOException {
        log.trace("AnalyzerToggle sent to server " + serverIP + " on port " + serverPort );

        String url = URLCreator.createURL("false", serverIP, serverPort, API_ENDPOINT);
        log.trace("AnalyzerToggle Request Url :" + url);

        String jsonRequest =  tradeOrder.toJson();
        log.trace("AnalyzerToggle Request Payload :\n\r" + jsonRequest);

        return APICallers.CallAPI(url, jsonRequest) ;
    }

    public JsonNode sendQuery(String serverIP, String serverPort, String apiKey, String mode) throws IOException {
        log.trace("AnalyzerToggle sent to server " + serverIP + " on port " + serverPort + " with API key " + apiKey);

        TradeOrder tradeOrder = new TradeOrder.Builder()
                .apikey(apiKey)
                .mode(mode)
                .build();

        return sendQuery(serverIP, serverPort, tradeOrder) ;
    }
}
