package com.codeandcandles.springbootconfiguration;

import com.codeandcandles.springbootconfiguration.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class SpringBootConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootConfigApplication.class, args);
    }
}
