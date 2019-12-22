package com.grcp.weather.rhythm.restclient.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MainResponse {

    private double temp;

    @JsonIgnore
    public double valueOfCelsius() {
        return (temp - 32.0) * (5.0/9.0);
    }
}
