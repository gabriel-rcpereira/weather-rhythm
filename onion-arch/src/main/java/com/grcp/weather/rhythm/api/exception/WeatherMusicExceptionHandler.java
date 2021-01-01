package com.grcp.weather.rhythm.api.exception;

import com.grcp.weather.rhythm.api.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class WeatherMusicExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { WeatherMusicException.class })
    public ResponseEntity<Object> handleWeatherMusicException(WeatherMusicException ex, WebRequest request) {
        return handleExceptionInternal(ex, buildErrorResponse(ex), new HttpHeaders(), ex.getHttpStatus(), request);
    }

    private ErrorResponse buildErrorResponse(WeatherMusicException e) {
        return ErrorResponse.builder()
                .description(e.getMessage())
                .build();
    }
}
