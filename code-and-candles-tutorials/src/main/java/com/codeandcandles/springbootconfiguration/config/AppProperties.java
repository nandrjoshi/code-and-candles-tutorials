package com.codeandcandles.springbootconfiguration.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    /**
     * Logical name for this demo application (used in logs / responses).
     */
    @NotBlank
    private String name;

    /**
     * Example feature flag to show profile overrides and precedence.
     */
    private boolean featureXEnabled;

    /**
     * Example timeout to show overriding via YAML/env vars.
     */
    @Min(100)
    @Max(600000)
    private int timeoutMs;

    /**
     * Example duration field (binds from ISO-8601 like PT2S or "2s" in YAML).
     */
    private Duration startupDelay = Duration.ZERO;

    public String getName() {
        return name;
    }

    public boolean isFeatureXEnabled() {
        return featureXEnabled;
    }

    public int getTimeoutMs() {
        return timeoutMs;
    }

    public Duration getStartupDelay() {
        return startupDelay;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFeatureXEnabled(boolean featureXEnabled) {
        this.featureXEnabled = featureXEnabled;
    }

    public void setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public void setStartupDelay(Duration startupDelay) {
        this.startupDelay = startupDelay;
    }
}
