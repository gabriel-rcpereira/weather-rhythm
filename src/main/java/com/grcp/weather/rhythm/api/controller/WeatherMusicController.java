package com.grcp.weather.rhythm.api.controller;

import com.grcp.weather.rhythm.api.exception.WeatherMusicException;
import com.grcp.weather.rhythm.api.model.WeatherMusicResponse;
import com.grcp.weather.rhythm.api.service.WeatherMusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WeatherMusicController {

    private final WeatherMusicService service;

    @GetMapping("/weather/rhythm/v1/cities/{cityName}/musics")
    public ResponseEntity<WeatherMusicResponse> getMusicsByCityName(@PathVariable("cityName") String cityName) throws WeatherMusicException {
        return ResponseEntity.ok(service.retrieveMusicsByCityName(cityName));
    }

    @GetMapping("/weather/rhythm/v1/cities/lat/{latitude}/lon/{longitude}/musics")
    public ResponseEntity<WeatherMusicResponse> getMusicsByCoordinates(@PathVariable("latitude") double latitude,
                                                                       @PathVariable("longitude") double longitude) throws WeatherMusicException {
        return ResponseEntity.ok(service.retrieveMusicsByCoordinates(latitude, longitude));
    }
}
