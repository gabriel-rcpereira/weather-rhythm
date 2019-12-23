package com.grcp.weather.rhythm.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenWeatherConfig {

    @Value("${com.grcp.weather.rhythm.openweather.appid}")
    private String openWeatherAppId;

    @Bean
    public String appId() {
        return openWeatherAppId;
    }
}
