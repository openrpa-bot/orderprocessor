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
import java.util.Optional;

@Slf4j
@Controller
public class HoldingsController {



    private final HoldingServices holdingServices;

    @Value("${openalgo.broker.default.strategy}")
    private String strategy;

    @Autowired
    Holdings openalgoQueryExecutor;

    @Autowired
    AppConfigRepository appConfigRepository;

    public HoldingsController(HoldingServices holdingsService) {
        this.holdingServices = holdingsService;
    }

    @GetMapping("/holdings")
    public String showHoldings(@RequestParam(required = false) String configid,
                               Model model,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        try {
            // Step 1: Determine which config ID to use
            Long activeConfigId = null;

            if (configid != null && !configid.isEmpty()) {
                session.setAttribute("configid", configid);
                activeConfigId = Long.parseLong(configid);
            } else {
                Object saved = session.getAttribute("configid");
                if (saved != null) {
                    activeConfigId = Long.parseLong(saved.toString());
                }
            }

            // Step 2: Load AppConfig based on ID, fallback to default if missing
            Optional<AppConfig> configOpt = Optional.empty();

            if (activeConfigId != null) {
                configOpt = appConfigRepository.findById(activeConfigId);
            }

            AppConfig config = null;

            if (configOpt.isPresent()) {
                config = configOpt.get();
            } else {
                // Try loading default configuration
                Optional<AppConfig> defaultConfig = appConfigRepository.findByIsDefaultTrue();
                if (defaultConfig.isPresent()) {
                    config = defaultConfig.get();
                    session.setAttribute("configid", config.getId().toString());
                } else {
                    // No config or default available — redirect to config page
                    redirectAttributes.addFlashAttribute("error", "⚠️ No valid configuration found. Please create or set one as default.");
                    return "redirect:/config";
                }
            }

            // Step 3: Fetch holdings data
            JsonNode jsonNode = openalgoQueryExecutor.sendQuery(
                    config.getIp(), config.getPort(), config.getApiKey());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode holdingsNode = jsonNode.path("data").path("holdings");
            List<Map<String, Object>> holdings = mapper.convertValue(holdingsNode, List.class);

            // Step 4: Add to model
            model.addAttribute("holdings", holdings);
            model.addAttribute("config", config);

            log.info("Loaded holdings using config ID: {}", config.getId());
            return "holdings";

        } catch (Exception e) {
            log.error("Error loading holdings", e);
            redirectAttributes.addFlashAttribute("error", "❌ Failed to load holdings: " + e.getMessage());
            return "redirect:/config";
        }
    }




    @PostMapping("/holdings/sellAll")
    public String sellSelectedHoldings(@RequestParam(value = "symbols", required = false) List<String> symbols,
                                       @RequestParam(required = false) String strategy,
                                       HttpSession session,
                                       RedirectAttributes redirectAttributes) {
        try {
            // Step 1: Validate selected symbols
            if (symbols == null || symbols.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "⚠️ No holdings selected for selling.");
                return "redirect:/holdings";
            }

            // Step 2: Resolve active config from session
            Object saved = session.getAttribute("configid");
            AppConfig config = null;

            if (saved != null) {
                Long configId = Long.parseLong(saved.toString());
                config = appConfigRepository.findById(configId).orElse(null);
            }

            // Step 3: Fallback to default config if session one missing
            if (config == null) {
                config = appConfigRepository.findByIsDefaultTrue().orElse(null);
            }

            // Step 4: Redirect if no valid config found
            if (config == null) {
                redirectAttributes.addFlashAttribute("error", "❌ No valid configuration found. Please configure one first.");
                return "redirect:/config";
            }

            // Step 5: Validate or use default strategy
            if (strategy == null || strategy.isBlank()) {
                strategy = (config.getStrategy() != null && !config.getStrategy().isBlank())
                        ? config.getStrategy()
                        : "default";
            }

            // Step 6: Perform sell operation
            holdingServices.Sell(config, openalgoQueryExecutor, symbols, strategy);

            // Step 7: Flash success message
            redirectAttributes.addFlashAttribute(
                    "message",
                    String.format("✅ Sold %d holdings successfully using Config: %s (%s:%s)",
                            symbols.size(),
                            config.getServer(),
                            config.getIp(),
                            config.getPort())
            );

            return "redirect:/holdings";

        } catch (Exception e) {
            log.error("Error during sellAll operation", e);
            redirectAttributes.addFlashAttribute("error", "❌ Failed to sell holdings: " + e.getMessage());
            return "redirect:/holdings";
        }
    }


}
