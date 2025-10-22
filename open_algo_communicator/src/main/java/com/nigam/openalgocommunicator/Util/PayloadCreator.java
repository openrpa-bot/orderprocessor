package com.nigam.openalgocommunicator.Util;

import com.nigam.openalgocommunicator.dto.CommonInputValue;

public class PayloadCreator {
    public static String createPayload(CommonInputValue commonInputValue) {
        String jsonString = "";
        if(commonInputValue.getApiKey() != null && !commonInputValue.getApiKey().isEmpty()){
            jsonString = String.format("\"apikey\":\"%s\"", commonInputValue.getApiKey());
        }

        if(commonInputValue.getPayload() != null && !commonInputValue.getPayload().isEmpty()){
            if(!jsonString.isEmpty()){
                jsonString = String.format("%s,%s", jsonString, commonInputValue.getPayload());
            } else {
                jsonString = commonInputValue.getPayload();
            }
        }
        return jsonString;
    }
    public static String createStrategyPayload(CommonInputValue commonInputValue) {
        String jsonString = "";
        if(commonInputValue.getStrategy() != null && !commonInputValue.getStrategy().isEmpty()){
            jsonString = String.format("\"%s\":\"%s\",", "strategy", commonInputValue.getStrategy());
        }
        return jsonString;
    }
}
