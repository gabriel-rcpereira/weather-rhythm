package com.grcp.weatherrhythm.locationsong.gateway.weather.restclient.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WeatherMainResponse {

    private double temp;

    @JsonIgnore
    public double toCelsius() {
        double fahrenheit = 273.15;
        return (temp - fahrenheit);
    }
}
