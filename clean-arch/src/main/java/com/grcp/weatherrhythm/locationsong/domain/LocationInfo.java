package com.grcp.weatherrhythm.locationsong.domain;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class LocationInfo {

    @NotNull
    private String city;
    @NotNull
    private double celsiusTemperature;
    @NotNull
    private Category category;
}
