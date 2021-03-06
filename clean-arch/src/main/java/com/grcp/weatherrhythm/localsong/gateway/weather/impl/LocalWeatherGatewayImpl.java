package com.grcp.weatherrhythm.localsong.gateway.weather.impl;

import com.grcp.weatherrhythm.localsong.domain.LocalWeather;
import com.grcp.weatherrhythm.localsong.domain.exception.GatewayException;
import com.grcp.weatherrhythm.localsong.domain.exception.errors.GatewayError;
import com.grcp.weatherrhythm.localsong.gateway.weather.LocalWeatherGateway;
import com.grcp.weatherrhythm.localsong.gateway.weather.mapper.LocationWeatherMapper;
import com.grcp.weatherrhythm.localsong.gateway.weather.adapter.WeatherAdapter;
import com.grcp.weatherrhythm.localsong.gateway.weather.adapter.model.WeatherClientModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
@Component
public class LocalWeatherGatewayImpl implements LocalWeatherGateway {

    private final WeatherAdapter weatherAdapter;

    public LocalWeatherGatewayImpl(WeatherAdapter weatherAdapter) {
        this.weatherAdapter = weatherAdapter;
    }

    @Override
    public LocalWeather retrieveLocalWeatherByCityName(String cityName) {
        log.info("Retrieving local weather by city [{}].", cityName);
        WeatherClientModel weather = getWeatherByCityName(cityName);
        return LocationWeatherMapper.INSTANCE.mapToLocationWeather(weather);
    }

    @Override
    public LocalWeather retrieveLocalWeatherByLatitudeAndLongitude(double latitude, double longitude) {
        log.info("Retrieving local weather by latitude [{}] and longitude [{}].", latitude, longitude);
        WeatherClientModel weather = weatherAdapter.retrieveWeatherByLatitudeAndLongitude(latitude, longitude);
        return LocationWeatherMapper.INSTANCE.mapToLocationWeather(weather);
    }

    private WeatherClientModel getWeatherByCityName(String cityName) {
        try {
            return weatherAdapter.retrieveWeatherByCityName(cityName);
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
