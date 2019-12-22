package com.grcp.weather.rhythm.api.service;

import com.grcp.weather.rhythm.api.model.MusicResponse;
import com.grcp.weather.rhythm.api.model.WeatherRhythmResponse;
import com.grcp.weather.rhythm.restclient.api.WeatherApi;
import com.grcp.weather.rhythm.restclient.model.MainResponse;
import com.grcp.weather.rhythm.restclient.model.WeatherApiResponse;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WeatherRhythmService {

    private final WeatherHelper weatherHelper;
    private final MusicHelper musicHelper;

    public WeatherRhythmResponse retrieveRhythmsByCityName(String cityName) throws IOException, SpotifyWebApiException {
        WeatherApiResponse weatherResponse = weatherHelper.getCurrentWeatherByCityName(cityName);
        MainResponse mainResponse = weatherResponse.getMain();
        return buildWeatherRhythmResponse(mainResponse, getMusics(mainResponse));
    }

    private List<MusicResponse> getMusics(MainResponse mainResponse) throws IOException, SpotifyWebApiException {
        List<MusicResponse> musics = new ArrayList<>();

        if (isAboveThirtyDegrees(mainResponse)) {
            musics = musicHelper.retrieveMusicsByPartyCategory();
        } else if (isBetweenFifteenAndThirtyDegrees(mainResponse)) {
            //TODO: get the playlist related to pop
            musics = musicHelper.retrieveMusicsByPopCategory();
        } else if (isBetweenTenAndFifteenDegrees(mainResponse)) {
            //TODO: get the playlist related to rock
        } else {
            //TODO: get the playlist related to classical
        }
        return musics;
    }

    public List<WeatherRhythmResponse> retrieveRhythmByCoordinates(long latitude, long longitude) {
        return List.of(WeatherRhythmResponse.builder().build());
    }

    private boolean isBetweenTenAndFifteenDegrees(MainResponse mainResponse) {
        return mainResponse.getTemp() >= 10 && mainResponse.getTemp() < 14;
    }

    private boolean isBetweenFifteenAndThirtyDegrees(MainResponse mainResponse) {
        return mainResponse.getTemp() >= 15 && mainResponse.getTemp() <= 30;
    }

    private boolean isAboveThirtyDegrees(MainResponse mainResponse) {
        return mainResponse.getTemp() > 30;
    }

    private WeatherRhythmResponse buildWeatherRhythmResponse(MainResponse mainResponse, List<MusicResponse> musicsOfPartyCategory) {
        return WeatherRhythmResponse.builder()
                .temperature(mainResponse.getTemp())
                .musics(musicsOfPartyCategory)
                .build();
    }
}
