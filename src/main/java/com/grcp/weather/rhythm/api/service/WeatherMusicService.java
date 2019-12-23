package com.grcp.weather.rhythm.api.service;

import com.grcp.weather.rhythm.api.model.MusicResponse;
import com.grcp.weather.rhythm.api.model.WeatherMusicResponse;
import com.grcp.weather.rhythm.restclient.openweather.model.MainResponse;
import com.grcp.weather.rhythm.restclient.openweather.model.WeatherApiResponse;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WeatherMusicService {

    private final WeatherHelper weatherHelper;
    private final MusicHelper musicHelper;
    private final ValidatorHelper validatorHelper;

    public WeatherMusicResponse retrieveRhythmsByCityName(String cityName) throws IOException, SpotifyWebApiException {
        WeatherApiResponse weatherResponse = weatherHelper.getCurrentWeatherByCityName(cityName);
        MainResponse mainResponse = weatherResponse.getMain();
        return buildWeatherRhythmResponse(mainResponse, getMusics(mainResponse));
    }

    public List<WeatherMusicResponse> retrieveRhythmByCoordinates(long latitude, long longitude) {
        return List.of(WeatherMusicResponse.builder().build());
    }

    private List<MusicResponse> getMusics(MainResponse mainResponse) throws IOException, SpotifyWebApiException {
        List<MusicResponse> musics;
        double celsiusDegree = mainResponse.valueOfCelsius();

        if (validatorHelper.isAboveThirtyDegrees(celsiusDegree)) {
            musics = musicHelper.retrieveMusicsByPartyCategory();
        } else if (validatorHelper.isBetweenFifteenAndThirtyDegrees(celsiusDegree)) {
            musics = musicHelper.retrieveMusicsByPopCategory();
        } else if (validatorHelper.isBetweenTenAndFourteenDegrees(celsiusDegree)) {
            musics = musicHelper.retrieveMusicsByRockCategory();
        } else {
            musics = musicHelper.retrieveMusicsByClassicalCategory();
        }

        return musics;
    }

    private WeatherMusicResponse buildWeatherRhythmResponse(MainResponse mainResponse, List<MusicResponse> musicsOfPartyCategory) {
        return WeatherMusicResponse.builder()
                .temperature(mainResponse.valueOfCelsius())
                .musics(musicsOfPartyCategory)
                .build();
    }
}
