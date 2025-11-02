package com.nigam.openalgo.api.data_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.nigam.openalgo.api.communicator.APICallers;
import com.nigam.openalgo.api.payload.TradeOrder;
import com.nigam.openalgo.api.urlcreator.TickerUrlBuilder;
import com.nigam.openalgo.api.urlcreator.URLCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class Ticker {
    String API_ENDPOINT = "api/v1/ticker";

    public JsonNode sendQuery(String serverIP, String serverPort, String url) throws IOException {
        log.trace("Ticker sent to server " + serverIP + " on port " + serverPort );

        log.trace("Ticker Request URL :" + url);

        return APICallers.GetAPI(url) ;
    }

    public JsonNode sendQuery(String serverIP,
                              String serverPort,
                              String apiKey,
                              String symbol,
                              String exchange,
                              String interval,
                              String from,
                              String to
                        ) throws IOException {
        log.trace("Depth sent to server " + serverIP + " on port " + serverPort + " with API key " + apiKey);

        String baseUrl = URLCreator.createURL("false", serverIP, serverPort, API_ENDPOINT);
        log.trace("Ticker Request base Url :" + baseUrl);


        String url = TickerUrlBuilder
                .fromBase(baseUrl)
                .symbol(exchange, symbol)
                .param("apikey", apiKey)
                .param("interval", interval)
                .param("from", from)
                .param("to", to)
                .build();


        return sendQuery(serverIP, serverPort, url) ;
    }
}
