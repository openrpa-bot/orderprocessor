package com.nigam.openalgocommunicator.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APICallers {
    public static JsonNode CallAPI(String strUrl, String payload) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Setup HTTP connection
        System.out.println("API URL: " + strUrl.toString());
        System.out.println("API payload: " + payload.toString());
        URL url = new URL(strUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send Request
        try (var outputStream = connection.getOutputStream()) {
            outputStream.write(payload.getBytes());
            outputStream.flush();
        }

        // Handle Response
        int statusCode = connection.getResponseCode();
        if (statusCode == 200) {
            try (var inputStream = connection.getInputStream()) {
                // Inspect the response structure and use appropriate mapping
                JsonNode jsonNode = objectMapper.readTree(inputStream); // Generic JSON tree
                System.out.println("API Response: " + jsonNode.toString());
                return jsonNode;

            } catch (JsonProcessingException e) {
                // Handle JSON parsing errors
                throw new IOException("Failed to parse API response JSON", e);
            }
        } else {
            // Enhanced error handling for non-200 status code
            try (var errorStream = connection.getErrorStream()) {
                String errorResponse = errorStream != null
                        ? new String(errorStream.readAllBytes(), StandardCharsets.UTF_8)
                        : "No response body";

                System.err.println("Error calling API: HTTP " + statusCode + ", Response: " + errorResponse);
            }
            throw new IOException("Failed to call API (HTTP " + statusCode + ")");
        }
    }
}
