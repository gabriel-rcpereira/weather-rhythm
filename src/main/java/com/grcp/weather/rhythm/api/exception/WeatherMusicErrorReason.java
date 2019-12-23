package com.grcp.weather.rhythm.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WeatherMusicErrorReason {

    CITY_NOT_FOUND("The city was not found.", HttpStatus.NOT_FOUND),
    WEATHER_API_COMMUNICATION_FAILED("The weather api communication failed.", HttpStatus.PRECONDITION_FAILED),
    WEATHER_API_CLIENT_ERROR("The weather api returns client error.", HttpStatus.PRECONDITION_FAILED);

    private final String description;
    private final HttpStatus httpStatus;
}
