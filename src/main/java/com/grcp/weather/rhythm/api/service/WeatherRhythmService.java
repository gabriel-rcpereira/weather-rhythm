package com.grcp.weather.rhythm.api.service;

import com.grcp.weather.rhythm.api.model.WeatherRhythmResponse;
import com.grcp.weather.rhythm.httpclient.api.WeatherApi;
import com.grcp.weather.rhythm.httpclient.model.WeatherApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WeatherRhythmService {

    private final static String APP_ID = "dd822d5631f3dafc40163e815cdf5f10";

    private final WeatherApi weatherApi;

    public List<WeatherRhythmResponse> retrieveRhythmByCityName(String cityName) {
        WeatherApiResponse weatherApiResponse = weatherApi.getWeather(cityName, APP_ID);
        
        return List.of(WeatherRhythmResponse.builder().build());
    }

    public List<WeatherRhythmResponse> retrieveRhythmByCoordinates(long latitude, long longitude) {
        return List.of(WeatherRhythmResponse.builder().build());
    }
}
