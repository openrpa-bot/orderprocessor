package com.nigam.temporal.core.config;

import com.sun.net.httpserver.HttpServer;
import com.uber.m3.tally.RootScopeBuilder;
import com.uber.m3.tally.Scope;
import com.uber.m3.util.ImmutableMap;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.common.reporter.MicrometerClientStatsReporter;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Configuration
public class TemporalConfig {

    @Value("${temporal.host}")
    private String temporalServiceAddress;

    @Value("${temporal.port}")
    private String temporalServicePort;

    @Value("${temporal.namespace}")
    private String temporalNamespace;

    @Bean
    public PrometheusMeterRegistry prometheusMeterRegistry() {
        return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    }

    @Bean
    public Scope metricsScope(PrometheusMeterRegistry prometheusMeterRegistry) {
        return new RootScopeBuilder()
                .tags(ImmutableMap.of(
                        "workerCustomTag1", "workerCustomTag1Value",
                        "workerCustomTag2", "workerCustomTag2Value"))
                .reporter(new MicrometerClientStatsReporter(prometheusMeterRegistry))
                .reportEvery(com.uber.m3.util.Duration.ofSeconds(10));
    }

    @PostConstruct
    public void logTemporalConfig() {
        System.out.println("Temporal Host: " + temporalServiceAddress);
        System.out.println("Temporal Port: " + temporalServicePort);
    }

    @Bean
    public HttpServer prometheusScrapeEndpoint(PrometheusMeterRegistry prometheusMeterRegistry) throws IOException {
        return MetricsUtils.startPrometheusScrapeEndpoint(prometheusMeterRegistry, 8077);
    }

    @Bean
    public WorkflowServiceStubs workflowServiceStubs(Scope metricsScope) {
        String target = temporalServiceAddress + ":" + temporalServicePort;
        System.out.println("Connecting to Temporal at: " + target); // Debug log
        return WorkflowServiceStubs.newInstance(
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget(target)
                        .setMetricsScope(metricsScope)
                        .build());
    }

    @Bean
    public WorkflowClient workflowClient(WorkflowServiceStubs workflowServiceStubs) {
        return WorkflowClient.newInstance(workflowServiceStubs,
                WorkflowClientOptions.newBuilder().setNamespace(temporalNamespace).build());
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient workflowClient) {
        return WorkerFactory.newInstance(workflowClient);
    }
}