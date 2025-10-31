package com.nigam.openalgo.api.urlcreator;


public class URLCreator {
    public static String createURL(String isSecure, String host, String port, String apiEndpoint) {
        if (isSecure == null || isSecure.isEmpty() || !isSecure.toLowerCase().equals("true")){
            return String.format("%s://%s:%s/%s", "http", host , port , apiEndpoint);
        } else {
            return String.format("%s://%s:%s/%s", "https", host , port , apiEndpoint);
        }

    }
}
