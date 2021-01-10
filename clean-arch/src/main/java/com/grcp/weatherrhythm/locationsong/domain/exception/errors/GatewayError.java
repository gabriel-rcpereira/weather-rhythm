package com.grcp.weatherrhythm.locationsong.domain.exception.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GatewayError {

    LOCAL_WEATHER_CITY_NAME_WAS_NOT_FOUND("004.001", HttpStatus.NOT_FOUND),
    LOCAL_WEATHER_FAILED_REQUEST("004.002", HttpStatus.INTERNAL_SERVER_ERROR),
    LOCAL_WEATHER_SERVER_ERROR("004.003", HttpStatus.INTERNAL_SERVER_ERROR);

    private String errorCode;
    private HttpStatus status;

    GatewayError(String errorCode, HttpStatus status) {
        this.errorCode = errorCode;
        this.status = status;
    }
}
