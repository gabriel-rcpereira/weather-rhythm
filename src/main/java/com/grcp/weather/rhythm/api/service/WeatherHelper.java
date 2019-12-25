package com.grcp.weather.rhythm.api.service;

import com.grcp.weather.rhythm.api.exception.WeatherMusicException;
import com.grcp.weather.rhythm.api.exception.WeatherMusicErrorReason;
import com.grcp.weather.rhythm.restclient.openweather.api.WeatherApi;
import com.grcp.weather.rhythm.restclient.openweather.model.WeatherApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Slf4j
@RequiredArgsConstructor
@Component
public class WeatherHelper {

    private final String appId;
    private final WeatherApi weatherApi;

    public WeatherApiResponse getCurrentWeatherByCityName(String cityName) throws WeatherMusicException {
        try {
            return weatherApi.getWeather(cityName, appId);
        } catch (HttpClientErrorException e) {
            WeatherMusicErrorReason errorReason = getWeatherMusicErrorReason(e);
            log.error(errorReason.getDescription(), e);
            throw new WeatherMusicException(errorReason, e.getCause());
        } catch (HttpServerErrorException e) {
            log.error(WeatherMusicErrorReason.WEATHER_API_SERVER_ERROR.getDescription(), e);
            throw new WeatherMusicException(WeatherMusicErrorReason.WEATHER_API_SERVER_ERROR, e.getCause());
        }
    }

    public WeatherApiResponse getCurrentWeatherByCoordinates(double latitude, double longitude) throws WeatherMusicException {
        try {
            return weatherApi.getWeather(latitude, longitude, appId);
        } catch (HttpClientErrorException ex) {
            WeatherMusicErrorReason errorReason = getWeatherMusicErrorReason(ex);
            log.error(errorReason.getDescription(), ex);
            throw new WeatherMusicException(errorReason, ex.getCause());
        } catch (HttpServerErrorException ex) {
            log.error(WeatherMusicErrorReason.WEATHER_API_SERVER_ERROR.getDescription(), ex);
            throw new WeatherMusicException(WeatherMusicErrorReason.WEATHER_API_SERVER_ERROR, ex.getCause());
        }
    }

    private WeatherMusicErrorReason getWeatherMusicErrorReason(HttpClientErrorException ex) {
        WeatherMusicErrorReason errorReason;
        if (HttpStatus.NOT_FOUND.equals(ex.getStatusCode())) {
            errorReason = WeatherMusicErrorReason.CITY_NOT_FOUND;
        } else {
            errorReason = WeatherMusicErrorReason.WEATHER_API_CLIENT_ERROR;
        }
        return errorReason;
    }
}
