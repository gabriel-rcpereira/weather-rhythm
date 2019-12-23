package com.grcp.weather.rhythm.api.service;

import com.grcp.weather.rhythm.api.model.MusicResponse;
import com.grcp.weather.rhythm.api.model.WeatherRhythmResponse;
import com.grcp.weather.rhythm.restclient.model.MainResponse;
import com.grcp.weather.rhythm.restclient.model.WeatherApiResponse;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
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

    public List<WeatherRhythmResponse> retrieveRhythmByCoordinates(long latitude, long longitude) {
        return List.of(WeatherRhythmResponse.builder().build());
    }

    private List<MusicResponse> getMusics(MainResponse mainResponse) throws IOException, SpotifyWebApiException {
        List<MusicResponse> musics;
        double celsiusDegree = mainResponse.valueOfCelsius();

        if (isAboveThirtyDegrees(celsiusDegree)) {
            musics = musicHelper.retrieveMusicsByPartyCategory();
        } else if (isBetweenFifteenAndThirtyDegrees(celsiusDegree)) {
            musics = musicHelper.retrieveMusicsByPopCategory();
        } else if (isBetweenTenAndFourteenDegrees(celsiusDegree)) {
            musics = musicHelper.retrieveMusicsByRockCategory();
        } else {
            musics = musicHelper.retrieveMusicsByClassicalCategory();
        }

        return musics;
    }

    private boolean isBetweenTenAndFourteenDegrees(double celsiusDegree) {
        return celsiusDegree >= 10.0 && celsiusDegree < 15.0;
    }

    private boolean isBetweenFifteenAndThirtyDegrees(double celsiusDegree) {
        return celsiusDegree >= 15.0 && celsiusDegree <= 30.0;
    }

    private boolean isAboveThirtyDegrees(double celsiusDegree) {
        return celsiusDegree > 30.0;
    }

    private WeatherRhythmResponse buildWeatherRhythmResponse(MainResponse mainResponse, List<MusicResponse> musicsOfPartyCategory) {
        return WeatherRhythmResponse.builder()
                .temperature(mainResponse.valueOfCelsius())
                .musics(musicsOfPartyCategory)
                .build();
    }
}
