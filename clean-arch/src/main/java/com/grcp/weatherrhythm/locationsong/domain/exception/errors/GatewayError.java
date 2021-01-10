package com.grcp.weatherrhythm.locationsong.domain.exception.errors;

import lombok.Getter;

@Getter
public enum GatewayError {

    LOCAL_WEATHER_CITY_NAME_WAS_NOT_FOUND("004.001"),
    LOCAL_WEATHER_FAILED_REQUEST("004.002"),
    LOCAL_WEATHER_SERVER_ERROR("004.003");

    private String errorCode;

    GatewayError(String errorCode) {
        this.errorCode = errorCode;
    }
}
