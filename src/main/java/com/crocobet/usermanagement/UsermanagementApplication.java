package com.crocobet.usermanagement;

import com.crocobet.usermanagement.config.properties.CorsProperties;
import com.crocobet.usermanagement.config.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableConfigurationProperties({JwtProperties.class, CorsProperties.class})
@EnableCaching
public class UsermanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsermanagementApplication.class, args);
    }

}
