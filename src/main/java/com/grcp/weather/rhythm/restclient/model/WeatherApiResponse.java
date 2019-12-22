package com.grcp.weather.rhythm.restclient.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WeatherApiResponse {

    private MainResponse main;
}
