package com.grcp.weather.rhythm.api.service;

import com.grcp.weather.rhythm.restclient.api.WeatherApi;
import com.grcp.weather.rhythm.restclient.model.WeatherApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WeatherHelper {

    private final String appId;
    private final WeatherApi weatherApi;

    public WeatherApiResponse getCurrentWeatherByCityName(String cityName) {
        return weatherApi.getWeather(cityName, appId);
    }
}
