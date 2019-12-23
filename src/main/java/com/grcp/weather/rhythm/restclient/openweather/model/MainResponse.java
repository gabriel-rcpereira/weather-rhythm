package com.grcp.weather.rhythm.restclient.openweather.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainResponse {

    private double temp;

    @JsonIgnore
    public double valueOfCelsius() {
        return (temp - 273.15);
    }
}
