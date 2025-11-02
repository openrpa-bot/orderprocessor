package com.nigam.brokercrawler.controller.operations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nigam.brokercrawler.entity.AppConfig;
import com.nigam.brokercrawler.repository.AppConfigRepository;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class HoldingsController {



    private final HoldingServices holdingServices;

    @Value("${openalgo.broker.default.strategy}")
    private String strategy;

    @Autowired
    Holdings openalgoQueryExecutor;

    @Autowired
    AppConfigRepository configService;

    public HoldingsController(HoldingServices holdingsService) {
        this.holdingServices = holdingsService;
    }

    @GetMapping("/holdings")
    public String showHoldings(@RequestParam(required = false) String configid,
                               Model model,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        try {
            // Step 1: Determine active config ID
            Long activeConfigId = -1L;

            if (configid != null && !configid.isEmpty()) {
                // Save to session for future requests
                session.setAttribute("configid", configid);
                activeConfigId = Long.parseLong(configid);
            } else {
                // Fallback to session
                Object saved = session.getAttribute("configid");
                if (saved != null) {
                    activeConfigId = Long.parseLong(saved.toString());
                }
            }

            // Step 2: Validate config ID
            if (activeConfigId == null || !configService.existsById(activeConfigId) || activeConfigId == -1L) {
                redirectAttributes.addFlashAttribute("error", "Invalid or missing configuration ID.");
                return "redirect:/config";
            }
            log.info("Using config ID: {}", activeConfigId);
            // Step 3: Load configuration
            AppConfig config = configService.findById(activeConfigId)
                    .orElseThrow(() -> new IllegalStateException("Config not found for ID"));

            // Step 4: Load holdings data using that config
            JsonNode jsonNode = openalgoQueryExecutor.sendQuery(
                    config.getIp(), config.getPort(), config.getApiKey());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode holdingsNode = jsonNode.path("data").path("holdings");
            List<Map<String, Object>> holdings = mapper.convertValue(holdingsNode, List.class);

            // Step 5: Add to model
            model.addAttribute("holdings", holdings);
            model.addAttribute("config", config);

            return "holdings";

        } catch (Exception e) {
            log.error("Error loading holdings", e);
            model.addAttribute("error", "Failed to load holdings: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/holdings/sellAll")
    public String sellSelectedHoldings(@RequestParam(value = "symbols", required = false) List<String> symbols,
                                       @RequestParam(required = false) String strategy,
                                       Model model,
                                       HttpSession session,
                                       RedirectAttributes redirectAttributes) throws IOException {
        // Step 1: Validate selected symbols
        if (symbols == null || symbols.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "⚠️ No holdings selected for selling.");
            return "redirect:/holdings";
        }

        // Step 2: Resolve active config from session
        Object saved = session.getAttribute("configid");
        if (saved == null) {
            redirectAttributes.addFlashAttribute("error", "❌ No configuration selected. Please go to the Config page first.");
            return "redirect:/config";
        }

        Long configId = Long.parseLong(saved.toString());
        AppConfig config = configService.findById(configId)
                .orElseThrow(() -> new IllegalStateException("Configuration not found for ID: " + configId));

        // Step 3: Validate or default strategy
        if (strategy == null || strategy.isBlank()) {
            strategy = config.getStrategy() != null ? config.getStrategy() : "default";
        }

        // Step 4: Perform sell operation
        holdingServices.Sell(config, openalgoQueryExecutor, symbols, strategy);

        // Step 5: Set success message and redirect
        redirectAttributes.addFlashAttribute("message", "✅ Selected holdings sold successfully using config: " + config.getServer());
        return "redirect:/holdings";
    }
}
