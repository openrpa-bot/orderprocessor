package com.nigam.brokercrawler.controller.orders_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nigam.openalgo.api.orders_api.CancelOrder;
import com.nigam.openalgo.api.orders_api.ModifyOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/broker-crawler/v1/CancelOrder")
public class BrokerCrowlerCancelOrderController {
    @Autowired
    CancelOrder openalgoQueryExecutor;

    @PostMapping
    public String receiveJson(@RequestBody String jsonPayload) {
        log.info("Received JSON payload: " + jsonPayload);
        String serverIP = "192.168.1.112";
        String serverPort = "5001";
        String apiKey = "a79902ef310ad435a5ec8b9d46701dd8c164a4ae9aab107b0a7f66c1a91b8c12";
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(openalgoQueryExecutor.sendQuery(
                    serverIP,
                    serverPort,
                    apiKey,
                    "Test Strategy",
                    "1000000123665912"
            ));

        } catch (Exception e) {
            // Handle or log the exception
            System.err.println("An error occurred while processing: " + e.getMessage());
            e.printStackTrace();
        }
        return "Received JSON payload: " + jsonString;
    }
}
