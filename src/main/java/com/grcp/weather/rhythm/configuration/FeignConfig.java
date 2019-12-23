package com.grcp.weather.rhythm.configuration;

import feign.Retryer;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig extends FeignClientsConfiguration {

    private static final int RETRY_TIME = 500;
    private static final int MAX_ATTEMPTS = 3;

    @Bean
    public Retryer retryer() {
        return new FeignRetryerConfig(RETRY_TIME, MAX_ATTEMPTS);
    }

}
