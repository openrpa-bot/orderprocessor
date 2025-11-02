package com.nigam.openalgo.api.communicator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APICallers {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Makes a POST API call with a JSON payload and returns the response as a JsonNode.
     */
    public static JsonNode CallAPI(String strUrl, String payload) throws IOException {
        System.out.println("API URL (POST): " + strUrl);
        System.out.println("API Payload: " + payload);

        URL url = new URL(strUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send payload
        try (var outputStream = connection.getOutputStream()) {
            outputStream.write(payload.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }

        return handleResponse(connection);
    }

    /**
     * Makes a GET API call (no payload) and returns the response as a JsonNode.
     */
    public static JsonNode GetAPI(String strUrl) throws IOException {
        System.out.println("API URL (GET): " + strUrl);

        URL url = new URL(strUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        return handleResponse(connection);
    }

    /**
     * Common method for handling response from both POST and GET requests.
     */
    private static JsonNode handleResponse(HttpURLConnection connection) throws IOException {
        int statusCode = connection.getResponseCode();

        if (statusCode >= 200 && statusCode < 300) {
            // ✅ Success
            try (var inputStream = connection.getInputStream()) {
                JsonNode jsonNode = objectMapper.readTree(inputStream);
                System.out.println("API Response: " + jsonNode.toPrettyString());
                return jsonNode;
            } catch (JsonProcessingException e) {
                throw new IOException("Failed to parse API response JSON", e);
            }
        } else {
            // ❌ Error response
            try (var errorStream = connection.getErrorStream()) {
                String errorResponse = (errorStream != null)
                        ? new String(errorStream.readAllBytes(), StandardCharsets.UTF_8)
                        : "No response body";

                System.err.println("Error calling API: HTTP " + statusCode + ", Response: " + errorResponse);
            }
            throw new IOException("Failed to call API (HTTP " + statusCode + ")");
        }
    }
}
