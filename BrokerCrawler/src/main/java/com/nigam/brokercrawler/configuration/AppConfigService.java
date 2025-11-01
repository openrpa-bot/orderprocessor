package com.nigam.brokercrawler.configuration;

import com.nigam.brokercrawler.entity.AppConfig;
import com.nigam.brokercrawler.repository.AppConfigRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppConfigService {

    private final AppConfigRepository repository;

    // Keep the currently active configuration in memory
    private AppConfig activeConfig;

    @Value("${openalgo.broker.default.server.name}")
    private String defaultServerName;

    @Value("${openalgo.broker.default.server.host}")
    private String defaultServerHost;

    @Value("${openalgo.broker.default.server.port}")
    private String defaultServerPort;

    @Value("${openalgo.broker.default.server.apiKey}")
    private String defaultApiKey;

    /**
     * Returns the currently active configuration.
     * If not cached, loads it from DB or default properties.
     */
    public AppConfig getActiveConfig() {
        if (activeConfig == null) {
            activeConfig = loadActiveConfig();
        }
        return activeConfig;
    }

    public AppConfig refreshAndGetActive() {
        AppConfig config = loadActiveConfig(); // Load from DB or default
        config.setLastRefreshed(LocalDateTime.now());
        repository.save(config);
        return config;
    }


    /**
     * Updates the in-memory active config.
     */
    public void updateActiveConfig(AppConfig newConfig) {
        this.activeConfig = repository.save(newConfig);
    }

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
     * Refresh configuration:
     *  - If same IP exists, update its details
     *  - Otherwise create new entry
     *  - Enable and set as default
     *  - Disable others
     */
    @Transactional
    public AppConfig refreshConfig(String server, String ip, String port, String apiKey) {
        log.info("Refreshing configuration for server: {}", server);

        repository.clearDefaultFlags();

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

        // 👇 Add this line here
        config.setLastRefreshed(LocalDateTime.now());

        repository.save(config);
        activeConfig = config;

        log.info("Refreshed configuration saved and set as default: {}", config.getServer());
        return config;
    }

}
