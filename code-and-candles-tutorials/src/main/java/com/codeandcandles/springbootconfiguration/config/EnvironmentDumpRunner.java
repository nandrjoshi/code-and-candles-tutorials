package com.codeandcandles.springbootconfiguration.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentDumpRunner implements ApplicationRunner {

    private final ConfigurableEnvironment env;

    public EnvironmentDumpRunner(ConfigurableEnvironment env) {
        this.env = env;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("=== PropertySources (higher precedence first-ish) ===");
        // Note: Order here reflects how Spring maintains sources; precedence is still deterministic.
        for (PropertySource<?> ps : env.getPropertySources()) {
            System.out.println(" - " + ps.getName());
        }
        System.out.println("====================================================");
    }
}

