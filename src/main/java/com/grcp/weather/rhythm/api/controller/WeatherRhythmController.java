package com.grcp.weather.rhythm.api.controller;

import com.grcp.weather.rhythm.api.model.WeatherRhythmResponse;
import com.grcp.weather.rhythm.api.service.WeatherRhythmService;
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
public class WeatherRhythmController {

    private final WeatherRhythmService service;

    @GetMapping("/weather/rhythm/v1/cities/{cityName}")
    public ResponseEntity<WeatherRhythmResponse> getRhythmsByCityName(@PathVariable("cityName") String cityName) throws IOException, SpotifyWebApiException {
        return ResponseEntity.ok(service.retrieveRhythmsByCityName(cityName));
    }

    @GetMapping("/weather/rhythm/v1/cities/latitude/{latitude}/longitude/{longitude}")
    public ResponseEntity<List<WeatherRhythmResponse>> getRhythmByCoordinates(@PathVariable("latitude") long latitude,
                                                                              @PathVariable("longitude") long longitude) {
        return ResponseEntity.ok(service.retrieveRhythmByCoordinates(latitude, longitude));
    }
}
