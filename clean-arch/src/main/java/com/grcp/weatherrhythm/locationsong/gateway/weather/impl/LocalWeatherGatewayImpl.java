package com.grcp.weatherrhythm.locationsong.gateway.weather.impl;

import com.grcp.weatherrhythm.locationsong.domain.LocalWeather;
import com.grcp.weatherrhythm.locationsong.domain.exception.GatewayException;
import com.grcp.weatherrhythm.locationsong.domain.exception.errors.GatewayError;
import com.grcp.weatherrhythm.locationsong.gateway.weather.LocalWeatherGateway;
import com.grcp.weatherrhythm.locationsong.gateway.weather.mapper.LocationWeatherMapper;
import com.grcp.weatherrhythm.locationsong.gateway.weather.restclient.WeatherApi;
import com.grcp.weatherrhythm.locationsong.gateway.weather.restclient.json.WeatherApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
@Component
public class LocalWeatherGatewayImpl implements LocalWeatherGateway {

    private final WeatherApi weatherApi;

    public LocalWeatherGatewayImpl(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    @Override
    public LocalWeather retrieveLocationWeatherByCityName(String cityName) {
        log.info("Retrieving location weather by city [{}].", cityName);
        WeatherApiResponse weatherResponse = getWeatherByCityName(cityName);
        return LocationWeatherMapper.INSTANCE.mapToLocationWeather(weatherResponse);
    }

    private WeatherApiResponse getWeatherByCityName(String cityName) {
        try {
            return weatherApi.getWeatherByCityName(cityName);
        } catch (HttpStatusCodeException e) {
            log.error("An error occurred. Failed request with getWeatherByCityName by city [{}].", cityName, e);
            throw new GatewayException(retrieveGatewayErrorByStatus(e.getStatusCode()), e);
        }
    }

    private GatewayError retrieveGatewayErrorByStatus(HttpStatus status) {
        switch (status) {
            case NOT_FOUND:
                return GatewayError.LOCAL_WEATHER_CITY_NAME_WAS_NOT_FOUND;
            default:
                return GatewayError.LOCAL_WEATHER_FAILED_REQUEST;
        }
    }
}
