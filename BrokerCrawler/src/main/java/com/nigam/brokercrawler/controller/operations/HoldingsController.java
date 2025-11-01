package com.nigam.brokercrawler.controller.operations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nigam.brokercrawler.configuration.AppConfigService;
import com.nigam.brokercrawler.entity.AppConfig;
import com.nigam.brokercrawler.services.HoldingServices;
import com.nigam.openalgo.api.account_api.Holdings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class HoldingsController {

    private final AppConfigService configService;

    private final HoldingServices holdingServices;

    @Value("${openalgo.broker.default.strategy}")
    private String strategy;

    @Autowired
    Holdings openalgoQueryExecutor;

    public HoldingsController(AppConfigService configService , HoldingServices holdingsService) {
        this.configService = configService;
        this.holdingServices = holdingsService;
    }

    @GetMapping("/holdings")
    public String showHoldings(@RequestParam(required = false) String strategy,
                               Model model,
                               HttpSession session) {
        try {
            // Step 1: manage session strategy
            if (strategy != null && !strategy.isEmpty()) {
                // store new value
                session.setAttribute("strategy", strategy);
            } else {
                // fallback to session if not in URL
                Object saved = session.getAttribute("strategy");
                if (saved != null) {
                    strategy = saved.toString();
                } else {
                    // default strategy if none
                    strategy = "default";
                    session.setAttribute("strategy", strategy);
                }
            }

            // Step 2: load config and holdings
            AppConfig config = configService.refreshAndGetActive();
            JsonNode jsonNode = openalgoQueryExecutor.sendQuery(
                    config.getIp(), config.getPort(), config.getApiKey());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode holdingsNode = jsonNode.path("data").path("holdings");
            List<Map<String, Object>> holdings = mapper.convertValue(holdingsNode, List.class);

            model.addAttribute("holdings", holdings);
            model.addAttribute("config", config);
            model.addAttribute("strategy", strategy);

            return "holdings";

        } catch (Exception e) {
            log.error("Error loading holdings", e);
            model.addAttribute("error", "Failed to load holdings: " + e.getMessage());
            return "error";
        }
    }


    @PostMapping("/holdings/sellAll")
    public String sellSelectedHoldings(@RequestParam(value = "symbols", required = false) List<String> symbols, @RequestParam String strategy,
                                       Model model) throws IOException {
        if (symbols == null || symbols.isEmpty()) {
            model.addAttribute("message", "⚠️ No holdings selected for selling.");
            return "redirect:/holdings";
        }
        AppConfig config = configService.refreshAndGetActive();
        holdingServices.Sell(config, openalgoQueryExecutor, symbols, strategy);  // delegate to service
        model.addAttribute("message", "✅ Selected holdings sold successfully.");

        return "redirect:/holdings";
    }


}
