package com.grcp.weatherrhythm.locationsong.domain.song;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class LocationWeather {

    private double celsiusTemperature;
}
