package com.grcp.weather.rhythm.api.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherMusicVo {

    private String cityName;
    private double latitude;
    private double longitude;
}
