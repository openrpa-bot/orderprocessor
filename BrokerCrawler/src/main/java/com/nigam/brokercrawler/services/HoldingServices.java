package com.nigam.brokercrawler.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nigam.brokercrawler.entity.AppConfig;
import com.nigam.openalgo.api.account_api.Holdings;
import com.nigam.openalgo.api.dto.HoldingItem;
import com.nigam.openalgo.api.orders_api.PlaceOrder;
import com.nigam.openalgo.api.payload.TradeOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class HoldingServices {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlaceOrder placeOrder;

    /**
     * Iterates over all holdings and sells/buys those whose symbols match the given list.
     */
    public String Sell(AppConfig config, Holdings holdings, List<String> symbols, String strategy) throws IOException {
        StringBuilder retVal = new StringBuilder();

        // Fetch holdings data
        JsonNode jsonNode = holdings.sendQuery(config.getIp(), config.getPort(), config.getApiKey());
        JsonNode holdingsNode = jsonNode.path("data").path("holdings");

        if (holdingsNode.isMissingNode() || !holdingsNode.isArray()) {
            log.warn("Holdings data not found or invalid format.");
            return "No holdings found to process.";
        }

        List<String> soldSymbols = new ArrayList<>();
        List<String> skippedSymbols = new ArrayList<>();

        // Deserialize and iterate through each holding
        for (JsonNode node : holdingsNode) {
            try {
                HoldingItem holdingItem = objectMapper.treeToValue(node, HoldingItem.class);
                String symbol = holdingItem.getSymbol();

                if (symbol == null || symbol.isEmpty()) {
                    log.warn("Skipping holding with missing symbol: {}", node);
                    continue;
                }

                if (symbols.contains(symbol)) {
                    String action = holdingItem.getQuantity() < 0 ? "BUY" : "SELL";

                    log.info("Attempting {}: {} (Qty: {})", action, symbol, holdingItem.getQuantity());

                    TradeOrder tradeOrder = new TradeOrder.Builder()
                            .apikey(config.getApiKey())
                            .strategy(strategy)
                            .symbol(symbol)
                            .action(action)
                            .exchange(holdingItem.getExchange())
                            .pricetype("MARKET")
                            .product(holdingItem.getProduct())
                            .quantity(String.format("%.0f", Math.abs(holdingItem.getQuantity())))
                            .build();

                    JsonNode response = placeOrder.sendQuery(config.getIp(), config.getPort(), tradeOrder);
                    log.info("{} response for {}: {}", action, symbol, response);

                    soldSymbols.add(symbol);
                } else {
                    skippedSymbols.add(symbol);
                }

            } catch (Exception e) {
                log.error("Error processing holding entry: {}", e.getMessage(), e);
            }
        }

        retVal.append("✅ Sold symbols: ").append(soldSymbols).append("\n");
        retVal.append("⏸️ Skipped symbols: ").append(skippedSymbols);

        return retVal.toString();
    }
}
