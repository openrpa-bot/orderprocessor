package com.nigam.brokercrawler.controller.operations;

import com.fasterxml.jackson.databind.JsonNode;

import com.nigam.brokercrawler.services.AllHoldings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/AllHoldings")
public class AllHoldingsController {

    @Autowired
    AllHoldings allHoldings;

    @GetMapping("/SellAll")
    public String SellAll(
            @RequestParam String serverIP,
            @RequestParam String serverPort,
            @RequestParam String apikey
    ) throws IOException {

        return allHoldings.Sell(serverIP, serverPort, apikey);
    }

    @GetMapping("/ListAll")
    public String ListAll(
            @RequestParam String serverIP,
            @RequestParam String serverPort,
            @RequestParam String apikey
    ) throws IOException {
        return allHoldings.Get(serverIP, serverPort, apikey);
    }
}
