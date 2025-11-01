package com.nigam.brokercrawler.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "om_app_config")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AppConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String server;
    private String ip;
    private String apiKey;
    private String port;
    private Boolean isEnabled;
    private Boolean isDefault;
}
