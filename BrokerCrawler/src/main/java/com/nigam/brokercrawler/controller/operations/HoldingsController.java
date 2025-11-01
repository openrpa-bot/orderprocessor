package com.nigam.brokercrawler.controller.operations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nigam.brokercrawler.configuration.AppConfigService;
import com.nigam.brokercrawler.entity.AppConfig;
import com.nigam.openalgo.api.account_api.Holdings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class HoldingsController {

    private final AppConfigService configService;

    @Autowired
    Holdings openalgoQueryExecutor;

    public HoldingsController(AppConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/holdings")
    public String showHoldings(Model model) {
        try {
            // Load (and persist) configuration
            AppConfig config = configService.refreshAndGetActive();

            // Fetch holdings
            JsonNode jsonNode = openalgoQueryExecutor.sendQuery(
                    config.getIp(), config.getPort(), config.getApiKey());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode holdingsNode = jsonNode.path("data").path("holdings");
            List<Map<String, Object>> holdings = mapper.convertValue(holdingsNode, List.class);

            model.addAttribute("holdings", holdings);
            model.addAttribute("config", config);
            return "holdings";

        } catch (Exception e) {
            log.error("Error loading holdings", e);
            model.addAttribute("error", "Failed to load holdings: " + e.getMessage());
            return "error";
        }
    }

}
