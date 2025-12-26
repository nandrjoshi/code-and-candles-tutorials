package com.codeandcandles.springbootconfiguration.controller;

import com.codeandcandles.springbootconfiguration.config.AppProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ConfigInspectorController {

    private final AppProperties props;
    private final Environment env;

    public ConfigInspectorController(AppProperties props, Environment env) {
        this.props = props;
        this.env = env;
    }

    @GetMapping("/config")
    public Map<String, Object> config() {
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("activeProfiles", Arrays.asList(env.getActiveProfiles()));
        out.put("app.name", props.getName());
        out.put("app.featureXEnabled", props.isFeatureXEnabled());
        out.put("app.timeoutMs", props.getTimeoutMs());
        out.put("app.startupDelay", String.valueOf(props.getStartupDelay()));
        out.put("example.precedenceHint",
                "Try overriding app.timeout-ms via: (1) --app.timeout-ms=..., (2) -Dapp.timeout-ms=..., (3) env var APP_TIMEOUT_MS, (4) ./config/application-<profile>.yml");
        return out;
    }
}
