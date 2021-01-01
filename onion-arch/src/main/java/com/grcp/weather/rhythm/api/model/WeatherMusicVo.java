package com.grcp.weather.rhythm.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherMusicVo {

    private static final int LATITUDE_ABSOLUTE_MAX = 90;
    private static final int LONGITUDE_ABSOLUTE_MAX = 180;

    private String cityName;
    private double latitude;
    private double longitude;

    @JsonIgnore
    public boolean isInvalidCoordinates() {
        return Math.abs(getLatitude()) > LATITUDE_ABSOLUTE_MAX || Math.abs(getLongitude()) > LONGITUDE_ABSOLUTE_MAX;
    }
}
