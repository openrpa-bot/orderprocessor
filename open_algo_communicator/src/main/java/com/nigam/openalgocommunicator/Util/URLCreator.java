package com.nigam.openalgocommunicator.Util;

import com.nigam.openalgocommunicator.dto.CommonInputValue;

public class URLCreator {
    public static String createURL(CommonInputValue commonInputValue, String apiEndpoint) {
        if (commonInputValue.getIsSecure() == null || commonInputValue.getIsSecure().isEmpty() || !commonInputValue.getIsSecure().toLowerCase().equals("true")){
            return String.format("%s://%s:%s/%s", "http", commonInputValue.getServerUrl() , commonInputValue.getPort() , apiEndpoint);
        } else {
            return String.format("%s://%s:%s/%s", "https", commonInputValue.getServerUrl() , commonInputValue.getPort() , apiEndpoint);
        }

    }
}
