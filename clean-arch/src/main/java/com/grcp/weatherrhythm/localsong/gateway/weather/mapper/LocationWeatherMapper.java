package com.grcp.weatherrhythm.localsong.gateway.weather.mapper;

import com.grcp.weatherrhythm.localsong.domain.LocalWeather;
import com.grcp.weatherrhythm.localsong.gateway.weather.client.model.WeatherMainModel;
import com.grcp.weatherrhythm.localsong.gateway.weather.client.model.WeatherClientModel;

public class LocationWeatherMapper {

    public static final LocationWeatherMapper INSTANCE = new LocationWeatherMapper();

    private LocationWeatherMapper() {
    }

    public LocalWeather mapToLocationWeather(WeatherClientModel weatherResponse) {
        WeatherMainModel weatherMainModel = weatherResponse.getMain();
        return LocalWeather.builder()
                .celsiusTemperature(weatherMainModel.toCelsius())
                .build();
    }
}
