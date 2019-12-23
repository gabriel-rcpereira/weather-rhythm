package com.grcp.weather.rhythm.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpotifyApiConfig {

    @Value("${com.grcp.weather.rhythm.spotify.api.clientid}")
    private String spotifyApiClientId;

    @Value("${com.grcp.weather.rhythm.spotify.api.clientsecret}")
    private String spotifyApiClientSecret;

    @Bean
    public String clientId() {
        return spotifyApiClientId;
    }

    @Bean
    public String clientSecret() {
        return spotifyApiClientSecret;
    }
}
