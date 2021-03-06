package com.grcp.weather.rhythm.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WeatherMusicErrorReason {

    CITY_NOT_FOUND("The city was not found.", HttpStatus.NOT_FOUND),
    WEATHER_API_SERVER_ERROR("The weather api communication fail due to server error.", HttpStatus.PRECONDITION_FAILED),
    WEATHER_API_CLIENT_ERROR("The weather api communication fail due to client error.", HttpStatus.PRECONDITION_FAILED),
    ACQUIRE_MUSIC_API_CREDENTIALS_FAILED("The music api credentials were not acquired.", HttpStatus.PRECONDITION_FAILED),
    GET_CATEGORY_FAILED("The music api does not return the categories.", HttpStatus.PRECONDITION_FAILED),
    GET_PLAYLISTS_FAILED("The music api does not return the playlist by category.", HttpStatus.PRECONDITION_FAILED),
    GET_PLAYLIST_TRACKS("The music api does not return the tracks from a playlist.", HttpStatus.PRECONDITION_FAILED),
    COORDINATES_INVALID("The coordinates are invalid.", HttpStatus.BAD_REQUEST);

    private final String description;
    private final HttpStatus httpStatus;
}
