package com.nigam.brokercrawler.controller.operations;

import com.nigam.brokercrawler.entity.AppConfig;
import com.nigam.brokercrawler.repository.AppConfigRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/config")
public class AppConfigController {

    private final AppConfigRepository repo;

    @Value("${openalgo.broker.default.strategy}")
    private String strategy;

    @Value("${openalgo.broker.default.server.name}")
    private String defaultServerName;

    @Value("${openalgo.broker.default.server.host}")
    private String defaultServerHost;

    @Value("${openalgo.broker.default.server.port}")
    private String defaultServerPort;

    @Value("${openalgo.broker.default.server.apiKey}")
    private String defaultApiKey;

    public AppConfigController(AppConfigRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(Model model) {
        // Prefill default config for Add form
        AppConfig defaultConfig = new AppConfig();
        defaultConfig.setServer(defaultServerName);
        defaultConfig.setIp(defaultServerHost);
        defaultConfig.setPort(defaultServerPort);
        defaultConfig.setStrategy(strategy);
        defaultConfig.setDescription("");
        defaultConfig.setIsEnabled(true);
        defaultConfig.setIsDefault(false);
        defaultConfig.setApiKey(defaultApiKey);

        model.addAttribute("configs", repo.findAll());
        model.addAttribute("newConfig", defaultConfig);
        model.addAttribute("editConfig", null);
        return "config-list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("newConfig") AppConfig config) {
        config.setLastRefreshed(LocalDateTime.now());
        repo.save(config);
        return "redirect:/config";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        AppConfig existing = repo.findById(id).orElseThrow();

        // For Add form defaults
        AppConfig defaultConfig = new AppConfig();
        defaultConfig.setServer(defaultServerName);
        defaultConfig.setIp(defaultServerHost);
        defaultConfig.setPort(defaultServerPort);
        defaultConfig.setStrategy(strategy);
        defaultConfig.setDescription("Non Default configuration");
        defaultConfig.setIsEnabled(true);
        defaultConfig.setIsDefault(false);
        defaultConfig.setApiKey(defaultApiKey);

        model.addAttribute("configs", repo.findAll());
        model.addAttribute("newConfig", defaultConfig);
        model.addAttribute("editConfig", existing);
        return "config-list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("editConfig") AppConfig config) {
        config.setLastRefreshed(LocalDateTime.now());
        repo.save(config);
        return "redirect:/config";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/config";
    }

    /** ✅ Efficient version: Unset all defaults, then set selected as default in one transaction */
    @Transactional
    @GetMapping("/set-default/{id}")
    public String setDefault(@PathVariable Long id) {
        // Set all to false
        repo.clearDefaultFlags();

        // Set chosen one to true
        AppConfig selected = repo.findById(id).orElseThrow();
        selected.setIsDefault(true);
        repo.save(selected);

        return "redirect:/config";
    }
}
