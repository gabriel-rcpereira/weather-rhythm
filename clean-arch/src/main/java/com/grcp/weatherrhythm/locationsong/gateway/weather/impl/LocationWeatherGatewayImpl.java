package com.grcp.weatherrhythm.locationsong.gateway.weather.impl;

import com.grcp.weatherrhythm.locationsong.domain.LocationWeather;
import com.grcp.weatherrhythm.locationsong.gateway.weather.LocationWeatherGateway;
import org.springframework.stereotype.Component;

@Component
public class LocationWeatherGatewayImpl implements LocationWeatherGateway {

    @Override
    public LocationWeather retrieveLocationWeatherByCityName(String cityName) {
        return null;
    }
}
