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

    @GetMapping({"/holdings"})
    public String showHoldings(Model model) {
        try {
            // Load configuration (from DB or default)
            AppConfig config = configService.loadActiveConfig();

            // Fetch holdings JSON
            JsonNode jsonNode = openalgoQueryExecutor.sendQuery(
                    config.getIp(),
                    config.getPort(),
                    config.getApiKey()
            );

            ObjectMapper mapper = new ObjectMapper();
            // Navigate to data.holdings inside JSON
            JsonNode holdingsNode = jsonNode.path("data").path("holdings");

            // Convert JsonNode array → Java List of Maps
            List<Map<String, Object>> holdings =
                    mapper.convertValue(holdingsNode, List.class);

            // Add to model
            model.addAttribute("holdings", holdings);

            // Also add config details
            model.addAttribute("config", config);

            return "holdings";

        } catch (Exception e) {
            log.error("Error loading holdings", e);
            model.addAttribute("error", "Failed to load holdings: " + e.getMessage());
            return "error";
        }
    }
}
