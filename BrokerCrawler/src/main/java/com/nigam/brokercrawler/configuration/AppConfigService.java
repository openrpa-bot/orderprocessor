package com.nigam.brokercrawler.configuration;

import com.nigam.brokercrawler.entity.AppConfig;
import com.nigam.brokercrawler.repository.AppConfigRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppConfigService {

    private final AppConfigRepository repository;

    @Value("${openalgo.broker.default.server.name}")
    private String defaultServerName;

    @Value("${openalgo.broker.default.server.host}")
    private String defaultServerHost;

    @Value("${openalgo.broker.default.server.port}")
    private String defaultServerPort;

    @Value("${openalgo.broker.default.server.apiKey}")
    private String defaultApiKey;

    /**
     * Loads active configuration:
     *  - If available in DB (isDefault = true), use that
     *  - Else load from default properties
     */
    public AppConfig loadActiveConfig() {
        Optional<AppConfig> activeConfig = repository.findByIsDefaultTrue();

        if (activeConfig.isPresent()) {
            log.info("Loaded active config from DB: {}", activeConfig.get().getServer());
            return activeConfig.get();
        }

        log.info("No active config found in DB, loading default config from properties.");
        AppConfig config = new AppConfig();
        config.setServer(defaultServerName);
        config.setIp(defaultServerHost);
        config.setPort(defaultServerPort);
        config.setApiKey(defaultApiKey);
        config.setIsEnabled(true);
        config.setIsDefault(true);

        repository.save(config);
        return config;
    }

    /**
     * Refresh the configuration:
     *  - If same IP exists, update
     *  - Else create new
     *  - Enable and set as default
     *  - Disable others
     */
    @Transactional
    public AppConfig refreshConfig(String server, String ip, String port, String apiKey) {
        log.info("Refreshing configuration for server: {}", server);

        // Disable all other configs
        repository.clearDefaultFlags();

        // Find existing config by IP
        Optional<AppConfig> existing = repository.findByIp(ip);
        AppConfig config;

        if (existing.isPresent()) {
            config = existing.get();
            config.setServer(server);
            config.setPort(port);
            config.setApiKey(apiKey);
            config.setIsEnabled(true);
            config.setIsDefault(true);
        } else {
            config = new AppConfig();
            config.setServer(server);
            config.setIp(ip);
            config.setPort(port);
            config.setApiKey(apiKey);
            config.setIsEnabled(true);
            config.setIsDefault(true);
        }

        repository.save(config);
        log.info("Refreshed configuration saved and set as default: {}", config.getServer());
        return config;
    }
}
