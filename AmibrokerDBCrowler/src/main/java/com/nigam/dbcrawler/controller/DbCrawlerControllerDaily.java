package com.nigam.dbcrawler.controller;

import com.nigam.dbcrawler.service.MarketDBCrawler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//http://localhost:8091
//http://localhost:8091/swagger-ui/index.html
//https://github.com/RuchiTanmay/nselib
@Slf4j
@RestController
@RequestMapping("/db-crawler/v1/DailyCrawler")
public class DbCrawlerControllerDaily {
        @Autowired
        MarketDBCrawler marketDBCrawler;

        @PostMapping
        public String receiveJson(@RequestBody String jsonPayload) {
            log.info("Received JSON payload: " + jsonPayload);
            // Process the JSON payload

            return "Received JSON payload: " + marketDBCrawler.processAllRecords("Amibroker_Grafana_Algo2_200D");
        }
}
