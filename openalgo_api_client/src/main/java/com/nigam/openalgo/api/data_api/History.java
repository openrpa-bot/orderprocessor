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
public class History {
    String API_ENDPOINT = "api/v1/history";

    public JsonNode sendQuery(String serverIP, String serverPort, TradeOrder tradeOrder) throws IOException {
        log.trace("History sent to server " + serverIP + " on port " + serverPort );

        String url = URLCreator.createURL("false", serverIP, serverPort, API_ENDPOINT);
        log.trace("History Request Url :" + url);

        String jsonRequest =  tradeOrder.toJson();
        log.trace("History Request Payload :\n\r" + jsonRequest);

        return APICallers.CallAPI(url, jsonRequest) ;
    }

    public JsonNode sendQuery(String serverIP,
                              String serverPort,
                              String apiKey,
                              String symbol,
                              String exchange,
                              String interval,
                              String start_date,
                              String end_date
                        ) throws IOException {
        log.trace("History sent to server " + serverIP + " on port " + serverPort + " with API key " + apiKey);

        TradeOrder tradeOrder = new TradeOrder.Builder()
                .apikey(apiKey)
                .symbol(symbol)
                .exchange(exchange)
                .interval(interval)
                .start_date(start_date)
                .end_date(end_date)
                .build();

        return sendQuery(serverIP, serverPort, tradeOrder) ;
    }
}
