package com.grcp.weatherrhythm.locationsong.gateway.weather.restclient.json;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WeatherErrorDetail {

    private int status;
    private int message;
}
