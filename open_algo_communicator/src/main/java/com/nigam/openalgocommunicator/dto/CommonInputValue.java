package com.nigam.openalgocommunicator.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonInputValue {
    public CommonInputValue(CommonInputValue  commonInputValue) {
        this.isSecure = commonInputValue.getIsSecure();
        this.serverUrl = commonInputValue.getServerUrl();
        this.Port = commonInputValue.getPort();
        this.apiKey = commonInputValue.getApiKey();
        this.strategy = commonInputValue.getStrategy();
        this.payload = commonInputValue.getPayload();
    }
    private String isSecure;
    private String serverUrl;
    private String Port;
    private String apiKey;
    private String strategy;
    private String payload;
}
