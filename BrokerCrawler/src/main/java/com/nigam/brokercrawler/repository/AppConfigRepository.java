package com.nigam.brokercrawler.repository;

import com.nigam.brokercrawler.entity.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface AppConfigRepository extends JpaRepository<AppConfig, Long> {

    Optional<AppConfig> findByIsDefaultTrue();

    Optional<AppConfig> findByIp(String ip);

    @Modifying
    @Query("UPDATE AppConfig c SET c.isDefault = false WHERE c.isDefault = true")
    void clearDefaultFlags();

    // ✅ Check if a default configuration exists (returns true/false)
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM AppConfig c WHERE c.isDefault = true")
    boolean existsDefaultConfig();

    @Query("SELECT COUNT(c) > 0 FROM AppConfig c")
    boolean existsAnyConfig();
}
