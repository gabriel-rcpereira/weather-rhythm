package com.grcp.weatherrhythm.localsong.gateway.weather.adapter.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WeatherErrorDetailModel {

    private int status;
    private int message;
}
