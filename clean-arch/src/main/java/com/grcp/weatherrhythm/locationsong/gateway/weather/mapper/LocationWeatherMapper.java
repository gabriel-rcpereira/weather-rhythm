package com.grcp.weatherrhythm.locationsong.gateway.weather.mapper;

import com.grcp.weatherrhythm.locationsong.domain.LocationWeather;
import com.grcp.weatherrhythm.locationsong.gateway.weather.restclient.json.WeatherMainResponse;
import com.grcp.weatherrhythm.locationsong.gateway.weather.restclient.json.WeatherApiResponse;

public class LocationWeatherMapper {

    public static final LocationWeatherMapper INSTANCE = new LocationWeatherMapper();

    private LocationWeatherMapper() {
    }

    public LocationWeather mapToLocationWeather(WeatherApiResponse weatherResponse) {
        WeatherMainResponse weatherMainResponse = weatherResponse.getMain();
        return LocationWeather.builder()
                .celsiusTemperature(weatherMainResponse.toCelsius())
                .build();
    }
}
