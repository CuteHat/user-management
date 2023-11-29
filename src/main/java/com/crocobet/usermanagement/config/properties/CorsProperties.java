package com.crocobet.usermanagement.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "security.cors")

public record CorsProperties(List<String> allowedOrigins,
                      List<String> allowedHttpMethods,
                      List<String> allowedHttpHeaders,
                      String apiPattern) {
}

