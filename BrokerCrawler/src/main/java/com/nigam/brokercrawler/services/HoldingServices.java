package com.nigam.brokercrawler.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nigam.openalgo.api.account_api.Holdings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class AllHoldings {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Holdings holdings;

    public String Sell(String serverIP, String serverPort, String apikey) throws IOException {
        String retVal = "";
        return retVal ;
    }

    public String Get(String serverIP, String serverPort, String apikey) throws IOException {
        String retVal = "";

        try {
            retVal = objectMapper.writeValueAsString(holdings.sendQuery(serverIP, serverPort, apikey));
        } catch (Exception e) {
            // Handle or log the exception
            System.err.println("An error occurred while processing: " + e.getMessage());
            e.printStackTrace();
        }
        return retVal ;
    }
}
