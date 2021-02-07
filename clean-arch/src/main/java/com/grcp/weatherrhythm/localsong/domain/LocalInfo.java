package com.grcp.weatherrhythm.localsong.domain;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class LocalInfo {

    @NotNull
    private double celsiusTemperature;
}
