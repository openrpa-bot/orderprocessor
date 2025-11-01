package com.nigam.brokercrawler.controller.orders_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nigam.openalgo.api.orders_api.BasketOrder;
import com.nigam.openalgo.api.orders_api.OptionsOrder;
import com.nigam.openalgo.api.payload.TradeOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/broker-crawler/v1/BasketOrder")
public class BrokerCrowlerBasketOrderController {
    @Autowired
    BasketOrder openalgoQueryExecutor;

    @PostMapping
    public String receiveJson(@RequestBody String jsonPayload) {
        log.info("Received JSON payload: " + jsonPayload);
        String serverIP = "192.168.1.112";
        String serverPort = "5001";
        String apiKey = "a79902ef310ad435a5ec8b9d46701dd8c164a4ae9aab107b0a7f66c1a91b8c12";
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            TradeOrder partialOrder = new TradeOrder.Builder()
                    .apikey(apiKey)
                    .strategy("your-strategy")
                    .addOrder(new TradeOrder.Order.OrderBuilder()
                            .symbol("RELIANCE")
                            .exchange("NSE")
                            .action("BUY")
                            .quantity("1")
                            .pricetype("MARKET")
                            .product("MIS")
                            .build())
                    .addOrder(new TradeOrder.Order.OrderBuilder()
                            .symbol("INFY")
                            .exchange("NSE")
                            .action("SELL")
                            .quantity("1")
                            .pricetype("MARKET")
                            .product("MIS")
                            .build())
                    .build();
            jsonString = mapper.writeValueAsString(openalgoQueryExecutor.sendQuery(
                    serverIP,
                    serverPort,
                    partialOrder
            ));

        } catch (Exception e) {
            // Handle or log the exception
            System.err.println("An error occurred while processing: " + e.getMessage());
            e.printStackTrace();
        }
        return "Received JSON payload: " + jsonString;
    }
}
