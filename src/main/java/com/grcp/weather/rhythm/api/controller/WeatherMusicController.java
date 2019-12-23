package com.grcp.weather.rhythm.api.controller;

import com.grcp.weather.rhythm.api.exception.WeatherMusicException;
import com.grcp.weather.rhythm.api.model.WeatherMusicResponse;
import com.grcp.weather.rhythm.api.service.WeatherMusicService;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WeatherMusicController {

    private final WeatherMusicService service;

    @GetMapping("/weather/rhythm/v1/cities/{cityName}/musics")
    public ResponseEntity<WeatherMusicResponse> getRhythmsByCityName(@PathVariable("cityName") String cityName) throws IOException, SpotifyWebApiException, WeatherMusicException {
        return ResponseEntity.ok(service.retrieveRhythmsByCityName(cityName));
    }

    @GetMapping("/weather/rhythm/v1/cities/lat/{latitude}/long/{longitude}/musics")
    public ResponseEntity<List<WeatherMusicResponse>> getRhythmsByCoordinates(@PathVariable("latitude") long latitude,
                                                                              @PathVariable("longitude") long longitude) {
        return ResponseEntity.ok(service.retrieveRhythmByCoordinates(latitude, longitude));
    }
}
