package com.nigam.brokercrawler.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "op_app_config")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AppConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String server;
    private String ip;
    private String apiKey;
    private String port;
    private String description;
    private String strategy;
    private Boolean isEnabled;
    private Boolean isDefault;


    @Column(name = "last_refreshed")
    private LocalDateTime lastRefreshed;
}
