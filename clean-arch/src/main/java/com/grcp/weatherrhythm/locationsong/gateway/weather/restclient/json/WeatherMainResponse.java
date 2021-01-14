package com.grcp.weatherrhythm.locationsong.gateway.weather.restclient.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.util.Precision;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WeatherMainResponse {

    private double temp;

    @JsonIgnore
    public double toCelsius() {
        double kelvinBase = 273.15;
        double celsius = temp - kelvinBase;
        return Precision.round(celsius, 2);
    }
}
