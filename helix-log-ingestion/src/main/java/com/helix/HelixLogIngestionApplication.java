package com.helix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.helix")
@EnableJpaRepositories(basePackages = "com.helix.repository")
@EntityScan(basePackages = "com.helix.model")
@PropertySource("classpath:application.properties")
public class HelixLogIngestionApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelixLogIngestionApplication.class, args);
    }
}

