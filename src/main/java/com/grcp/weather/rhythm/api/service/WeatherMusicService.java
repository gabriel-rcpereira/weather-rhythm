package com.grcp.weather.rhythm.api.service;

import com.grcp.weather.rhythm.api.exception.WeatherMusicException;
import com.grcp.weather.rhythm.api.model.MusicResponse;
import com.grcp.weather.rhythm.api.model.WeatherMusicResponse;
import com.grcp.weather.rhythm.api.model.WeatherMusicVo;
import com.grcp.weather.rhythm.restclient.openweather.model.MainResponse;
import com.grcp.weather.rhythm.restclient.openweather.model.WeatherApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WeatherMusicService {

    private final WeatherHelper weatherHelper;
    private final MusicHelper musicHelper;
    private final ValidatorHelper validatorHelper;

    public WeatherMusicResponse retrieveMusicsByCityName(WeatherMusicVo vo) throws WeatherMusicException {
        WeatherApiResponse weatherResponse = weatherHelper.getCurrentWeatherByCityName(vo.getCityName());
        return retrieveWeatherMusicResponse(weatherResponse);
    }

    public WeatherMusicResponse retrieveMusicsByCoordinates(WeatherMusicVo vo) throws WeatherMusicException {
        WeatherApiResponse weatherResponse = weatherHelper.getCurrentWeatherByCoordinates(vo.getLatitude(), vo.getLongitude());
        return retrieveWeatherMusicResponse(weatherResponse);
    }

    private WeatherMusicResponse retrieveWeatherMusicResponse(WeatherApiResponse weatherResponse) throws WeatherMusicException {
        MainResponse mainResponse = weatherResponse.getMain();
        return buildWeatherMusicResponse(mainResponse, retrieveMusicsResponse(mainResponse));
    }

    private List<MusicResponse> retrieveMusicsResponse(MainResponse mainResponse) throws WeatherMusicException {
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

    private WeatherMusicResponse buildWeatherMusicResponse(MainResponse mainResponse, List<MusicResponse> musicsOfPartyCategory) {
        return WeatherMusicResponse.builder()
                .temperature(mainResponse.valueOfCelsius())
                .musics(musicsOfPartyCategory)
                .build();
    }
}
