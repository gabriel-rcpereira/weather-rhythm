package com.grcp.weather.rhythm.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WeatherMusicException extends Exception {

    private HttpStatus httpStatus;

    public WeatherMusicException(WeatherMusicErrorReason errorReason) {
        super(errorReason.getDescription());
        httpStatus = errorReason.getHttpStatus();
    }

    public WeatherMusicException(WeatherMusicErrorReason errorReason, Throwable cause) {
        super(errorReason.getDescription(), cause);
        httpStatus = errorReason.getHttpStatus();
    }
}
