package com.grcp.weatherrhythm.locationsong.gateway.weather.impl;

import com.grcp.weatherrhythm.locationsong.domain.LocationWeather;
import com.grcp.weatherrhythm.locationsong.gateway.weather.LocationWeatherGateway;
import com.grcp.weatherrhythm.locationsong.gateway.weather.mapper.LocationWeatherMapper;
import com.grcp.weatherrhythm.locationsong.gateway.weather.restclient.WeatherApi;
import com.grcp.weatherrhythm.locationsong.gateway.weather.restclient.json.WeatherApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LocationWeatherGatewayImpl implements LocationWeatherGateway {

    private final WeatherApi weatherApi;

    public LocationWeatherGatewayImpl(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    @Override
    public LocationWeather retrieveLocationWeatherByCityName(String cityName) {
        log.info("Retrieving location weather by city [{}].", cityName);
        WeatherApiResponse weatherResponse = weatherApi.getWeather(cityName);
        return LocationWeatherMapper.INSTANCE.mapToLocationWeather(weatherResponse);
    }
}
