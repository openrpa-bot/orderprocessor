package com.nigam.initialization;

import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


@Component
public class DatabaseBootstrapper implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static final Logger log = LoggerFactory.getLogger(DatabaseBootstrapper.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        log.debug("DatabaseBootstrapper triggered");

        String dbUrl = event.getEnvironment().getProperty("database.url");
        if (dbUrl.startsWith("jdbc:postgresql")) {
            String dbName = event.getEnvironment().getProperty("database.name");
            String username = event.getEnvironment().getProperty("database.username");
            String password = event.getEnvironment().getProperty("database.password");


            if (!dbUrl.endsWith("/")) {
                dbUrl = dbUrl + "/";
            }
            String adminUrl = dbUrl + "postgres?serverTimezone=Asia/Kolkata";

            //String adminUrl = dbUrl.substring(0, dbUrl.lastIndexOf("/")) + "/postgres";

            try (Connection conn = DriverManager.getConnection(adminUrl, username, password);
                 Statement stmt = conn.createStatement()) {

                stmt.executeUpdate("CREATE DATABASE \"" + dbName + "\"");
                System.out.println("✅ Database created: " + dbName);

            } catch (Exception e) {
                if (e.getMessage().contains("already exists")) {
                    System.out.println("ℹ️ Database already exists: " + dbName);
                } else {
                    System.err.println("⚠️ Database check failed: " + e.getMessage());
                }
            }
        }
    }
}

