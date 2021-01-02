package com.grcp.weatherrhythm.locationsong.gateway.weather;

import com.grcp.weatherrhythm.locationsong.domain.LocationWeather;

public interface LocationWeatherGateway {

    LocationWeather retrieveLocationWeatherByCityName(String cityName);
}
