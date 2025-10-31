package com.nigam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nigam.openalgo.api.account_api.Ping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/broker-crawler/v1/ping")
public class BrokerCrowlerPingController {
        @PostMapping
        public String receiveJson(@RequestBody String jsonPayload) {
            log.info("Received JSON payload: " + jsonPayload);
            String serverIP = "192.168.1.112";
            String serverPort = "5001";
            String apiKey = "a79902ef310ad435a5ec8b9d46701dd8c164a4ae9aab107b0a7f66c1a91b8c12";
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = "";

            try {
            Ping ping = new Ping();
                 jsonString = mapper.writeValueAsString(ping.sendPing(serverIP, serverPort, apiKey));
            } catch (Exception e) {
                // Handle or log the exception
                System.err.println("An error occurred while processing: " + e.getMessage());
                e.printStackTrace();
            }
            return "Received JSON payload: " + jsonString;
        }
}
