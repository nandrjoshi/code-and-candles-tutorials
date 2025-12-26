package com.codeandcandles.springbootconfiguration.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StartupInfoRunner implements ApplicationRunner {

    private final AppProperties props;
    private final Environment env;

    public StartupInfoRunner(AppProperties props, Environment env) {
        this.props = props;
        this.env = env;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("=== Spring Boot Configuration Demo ===");
        System.out.println("Active profiles: " + Arrays.toString(env.getActiveProfiles()));
        System.out.println("app.name: " + props.getName());
        System.out.println("app.feature-x-enabled: " + props.isFeatureXEnabled());
        System.out.println("app.timeout-ms: " + props.getTimeoutMs());
        System.out.println("app.startup-delay: " + props.getStartupDelay());
        System.out.println("======================================");
    }
}
