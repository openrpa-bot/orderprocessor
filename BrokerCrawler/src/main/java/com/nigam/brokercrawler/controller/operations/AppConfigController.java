package com.nigam.brokercrawler.controller.operations;

import com.nigam.brokercrawler.entity.AppConfig;
import com.nigam.brokercrawler.configuration.AppConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class AppConfigController {

    private final AppConfigService configService;

    /**
     * Displays the configuration form.
     */
    @GetMapping("/refreshConfig")
    public String showConfigPage(Model model) {
        AppConfig config = configService.getActiveConfig();

        // prevent null-pointer when rendering Thymeleaf
        if (config == null) {
            config = new AppConfig();
        }

        model.addAttribute("config", config);
        return "refreshConfig";
    }

    /**
     * Refresh the configuration (POST /refreshConfig)
     * Used when user clicks a "refresh" button externally (not via form submit)
     */
    @PostMapping("/refreshConfig")
    public String refreshConfig(
            @RequestParam String server,
            @RequestParam String ip,
            @RequestParam String port,
            @RequestParam String apiKey,
            Model model
    ) {
        AppConfig updated = configService.refreshConfig(server, ip, port, apiKey);
        updated.setLastRefreshed(LocalDateTime.now());
        model.addAttribute("config", updated);
        model.addAttribute("message", "✅ Configuration refreshed and set as default.");

        // Go back to config page instead of holdings
        return "refreshConfig";
    }

    /**
     * Updates configuration from Thymeleaf form
     */
    @PostMapping("/refreshConfig/update")
    public String updateConfig(@ModelAttribute("config") AppConfig config, Model model) {
        config.setLastRefreshed(LocalDateTime.now());
        configService.updateActiveConfig(config);
        model.addAttribute("config", config);
        model.addAttribute("message", "✅ Configuration updated successfully!");
        return "refreshConfig";
    }
}
