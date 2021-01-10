package com.grcp.weatherrhythm.locationsong.gateway.weather;

import com.grcp.weatherrhythm.locationsong.domain.LocalWeather;

public interface LocalWeatherGateway {

    LocalWeather retrieveLocalWeatherByCityName(String cityName);
}
