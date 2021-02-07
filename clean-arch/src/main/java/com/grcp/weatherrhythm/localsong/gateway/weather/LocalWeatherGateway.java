package com.grcp.weatherrhythm.localsong.gateway.weather;

import com.grcp.weatherrhythm.localsong.domain.LocalWeather;

public interface LocalWeatherGateway {

    LocalWeather retrieveLocalWeatherByCityName(String cityName);

    LocalWeather retrieveLocalWeatherByLatitudeAndLongitude(double latitude, double longitude);
}
