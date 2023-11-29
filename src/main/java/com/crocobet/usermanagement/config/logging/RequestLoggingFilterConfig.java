package com.crocobet.usermanagement.config.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setBeforeMessagePrefix("Received request: ");
        filter.setIncludeClientInfo(true);
        filter.setIncludeQueryString(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludePayload(true);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("Processed request with data: ");
        return filter;
    }
}