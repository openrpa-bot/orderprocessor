package com.nigam.temporal.core.config;

import com.sun.net.httpserver.HttpServer;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;


import java.io.IOException;
import java.net.InetSocketAddress;

public class MetricsUtils {

    public static HttpServer startPrometheusScrapeEndpoint(PrometheusMeterRegistry registry, int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/metrics", httpExchange -> {
            String response = registry.scrape();
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            httpExchange.getResponseBody().write(response.getBytes());
            httpExchange.close();
        });
        server.start();
        return server;
    }
}