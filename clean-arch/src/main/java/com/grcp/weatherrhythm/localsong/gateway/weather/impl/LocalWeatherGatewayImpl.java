package com.grcp.weatherrhythm.localsong.gateway.weather.impl;

import com.grcp.weatherrhythm.localsong.domain.LocalWeather;
import com.grcp.weatherrhythm.localsong.domain.exception.GatewayException;
import com.grcp.weatherrhythm.localsong.domain.exception.errors.GatewayError;
import com.grcp.weatherrhythm.localsong.gateway.weather.LocalWeatherGateway;
import com.grcp.weatherrhythm.localsong.gateway.weather.mapper.LocationWeatherMapper;
import com.grcp.weatherrhythm.localsong.gateway.weather.client.WeatherClient;
import com.grcp.weatherrhythm.localsong.gateway.weather.client.model.WeatherClientModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
@Component
public class LocalWeatherGatewayImpl implements LocalWeatherGateway {

    private final WeatherClient weatherClient;

    public LocalWeatherGatewayImpl(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    @Override
    public LocalWeather retrieveLocalWeatherByCityName(String cityName) {
        log.info("Retrieving local weather by city [{}].", cityName);
        WeatherClientModel weatherResponse = getWeatherByCityName(cityName);
        return LocationWeatherMapper.INSTANCE.mapToLocationWeather(weatherResponse);
    }

    @Override
    public LocalWeather retrieveLocalWeatherByLatitudeAndLongitude(double latitude, double longitude) {
        return null;
    }

    private WeatherClientModel getWeatherByCityName(String cityName) {
        try {
            return weatherClient.getWeatherByCityName(cityName);
        } catch (HttpStatusCodeException e) {
            log.error("An error occurred. Failed sending local weather request by city [{}].", cityName, e);
            throw new GatewayException(retrieveGatewayErrorByStatus(e.getStatusCode()), e);
        } catch (RuntimeException e) {
            log.error("An error occurred. Failed trying to send local weather request by city [{}].", cityName, e);
            throw new GatewayException(GatewayError.LOCAL_WEATHER_FAILED_REQUEST, e);
        }
    }

    private GatewayError retrieveGatewayErrorByStatus(HttpStatus status) {
        switch (status) {
            case NOT_FOUND:
                return GatewayError.LOCAL_WEATHER_CITY_NAME_WAS_NOT_FOUND;
            default:
                return GatewayError.LOCAL_WEATHER_SERVER_ERROR;
        }
    }
}
