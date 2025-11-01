package com.nigam.brokercrawler.repository;

import com.nigam.brokercrawler.entity.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppConfigRepository extends JpaRepository<AppConfig, Long> {

    Optional<AppConfig> findByIsDefaultTrue();

    Optional<AppConfig> findByIp(String ip);

    @Modifying
    @Query("UPDATE AppConfig c SET c.isDefault = false")
    void clearDefaultFlags();
}
