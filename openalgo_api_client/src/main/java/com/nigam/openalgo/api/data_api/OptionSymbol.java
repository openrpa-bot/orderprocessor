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
public class OptionSymbol {
    String API_ENDPOINT = "api/v1/optionsymbol";

    public JsonNode sendQuery(String serverIP, String serverPort, TradeOrder tradeOrder) throws IOException {
        log.trace("OptionSymbol sent to server " + serverIP + " on port " + serverPort );

        String url = URLCreator.createURL("false", serverIP, serverPort, API_ENDPOINT);
        log.trace("OptionSymbol Request Url :" + url);

        String jsonRequest =  tradeOrder.toJson();
        log.trace("OptionSymbol Request Payload :\n\r" + jsonRequest);

        return APICallers.CallAPI(url, jsonRequest) ;
    }

    public JsonNode sendQuery(String serverIP,
                              String serverPort,
                              String apiKey,
                              String strategy,
                              String underlying,
                              String exchange,
                              String expiry_date,
                              String strike_int,
                              String offset,
                              String option_type
                        ) throws IOException {
        log.trace("OptionSymbol sent to server " + serverIP + " on port " + serverPort + " with API key " + apiKey);

        TradeOrder tradeOrder = new TradeOrder.Builder()
                .apikey(apiKey)
                .strategy(strategy)
                .underlying(underlying)
                .exchange(exchange)
                .expiry_date(expiry_date)
                .strike_int(strike_int)
                .offset(offset)
                .option_type(option_type)
                .build();

        return sendQuery(serverIP, serverPort, tradeOrder) ;
    }
}
