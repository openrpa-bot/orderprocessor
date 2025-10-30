package com.nigam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;


import java.io.IOException;
//http://localhost:8092
//http://localhost:8092/swagger-ui/index.html
//https://github.com/RuchiTanmay/nselib
@SpringBootApplication
@EnableCaching
@EnableJms
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
            logger.info("Starting the application");
            SpringApplication.run(Main.class, args);
    }
}