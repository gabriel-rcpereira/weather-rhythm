package com.grcp.weather.rhythm.api.exception;

public class WeatherMusicException extends Exception {

    public WeatherMusicException(WeatherMusicErrorReason errorReason, Throwable cause) {
        super(errorReason.getDescription(), cause);
    }
}
