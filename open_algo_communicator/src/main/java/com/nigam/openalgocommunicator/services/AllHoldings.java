package com.nigam.openalgocommunicator.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nigam.openalgocommunicator.dto.CommonInputValue;
import com.nigam.openalgocommunicator.dto.CommonOutputValue;
import com.nigam.openalgocommunicator.facade.impl.account_api.OpenAlgoCommunicator_Holdings;
import com.nigam.openalgocommunicator.facade.impl.order_api.OpenAlgoCommunicator_BasketOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.nigam.openalgocommunicator.Util.PayloadCreator.createStrategyPayload;


public class AllHoldings {
    private static final Logger logger = LogManager.getLogger(AllHoldings.class);

    public String clear(CommonInputValue commonInputValue) throws Exception {
        CommonOutputValue commonOutputValue_Holdings = new OpenAlgoCommunicator_Holdings().postRequest(commonInputValue);
        if(commonOutputValue_Holdings.getJsonNode()!=null) {

            JsonNode holdingsNode = commonOutputValue_Holdings.getJsonNode().path("data").path("holdings");
            String logListOfSymbols = "";
            String payload = "";
            if (holdingsNode.isArray()) {
                // Iterate through each item in the "holdings" array
                for (JsonNode holding : holdingsNode) {
                    String exchange = holding.path("exchange").asText();
                    String product = holding.path("product").asText();
                    String symbol = holding.path("symbol").asText();
                    int quantity = holding.path("quantity").asInt();

                    // Print the extracted fields
                    //logger.info("Exchange: " + exchange);
                    //logger.info("Product: " + product);
                    //logger.info("Symbol: " + symbol);
                    //logger.info("Quantity: " + quantity);
                    //logger.info("-----------------------");
                    if(quantity>0){
                        if(payload != ""){
                            payload += ",";
                        }
                        logListOfSymbols = logListOfSymbols + symbol + "," + exchange + "," + quantity + "," + product + "\n";
                        payload = payload
                                + "{"
                                + "\"symbol\":\"" + symbol+ "\","
                                + "\"exchange\":\"" + exchange+ "\","
                                + "\"action\":\"" + "SELL" + "\","
                                + "\"quantity\":\"" + quantity + "\","
                                + "\"pricetype\":\"" + "MARKET" + "\","
                                + "\"product\":\"" + product+ "\""
                                + "}";

                    }
                }
                payload = createStrategyPayload(commonInputValue) + "\"orders\":[" + payload + "]";
                logger.info("Final Payload: " + payload);
                logger.info("Final Product List: \n" + logListOfSymbols);
                CommonInputValue commonInputValue_BasketOrder = new CommonInputValue(commonInputValue);
                commonInputValue_BasketOrder.setPayload(payload);
                OpenAlgoCommunicator_BasketOrder openAlgoCommunicator_basketOrder = new OpenAlgoCommunicator_BasketOrder();
                CommonOutputValue commonOutputValue_BasketOrder = openAlgoCommunicator_basketOrder.postRequest(commonInputValue_BasketOrder);
                logger.info("Basket Order Response: " + commonOutputValue_BasketOrder.getJsonNode().toString());
            } else {
                logger.info("Holdings is not an array or is missing.");
            }

            return commonOutputValue_Holdings.getJsonNode().toString();
        }
        return "{ \"status\" : \"error\", \"message\" : \"No Data Found!\" }";
    };
}
