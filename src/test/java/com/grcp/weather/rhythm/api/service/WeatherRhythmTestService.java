package com.grcp.weather.rhythm.api.service;

import com.grcp.weather.rhythm.api.model.MusicResponse;
import com.grcp.weather.rhythm.api.model.WeatherRhythmResponse;
import com.grcp.weather.rhythm.restclient.api.WeatherApi;
import com.grcp.weather.rhythm.restclient.model.MainResponse;
import com.grcp.weather.rhythm.restclient.model.WeatherApiResponse;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class WeatherRhythmTestService {

    @Mock
    private WeatherHelper weatherHelper;

    @Mock
    private MusicHelper musicHelper;

    private WeatherRhythmService service;

    @BeforeEach
    public void loadContext() {
        service = new WeatherRhythmService(weatherHelper, musicHelper);
    }

    @ParameterizedTest
    @ValueSource(doubles = {31.0, 32.0, 40.0, 50.0})
    public void retrieveRhythmByCityName_whenTemperatureIsAboveOfThirty_expectedPartyMusic(double temperature) throws IOException, SpotifyWebApiException {
        // given
        String cityName = "cityNameTest";
        MainResponse mainResponse = MainResponse.builder().temp(temperature).build();
        WeatherApiResponse weatherApiResponse = WeatherApiResponse.builder().main(mainResponse).build();

        // when
        when(weatherHelper.getCurrentWeatherByCityName(cityName)).thenReturn(weatherApiResponse);
        when(musicHelper.retrieveMusicsByPartyCategory()).thenReturn(List.of(MusicResponse.builder().build()));

        // then
        WeatherRhythmResponse response = service.retrieveRhythmsByCityName(cityName);
        Assert.notEmpty(response.getMusics(), "Expected a list filled with at least one party music.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {15.0, 16.0, 17.0, 21.0, 22.0, 23.0, 24.0, 28.0, 29.0, 30.0})
    public void retrieveRhythmByCityName_whenGetTemperatureIsBetweenFifteenAndThirty_expectedPopMusic(double temperature) throws IOException, SpotifyWebApiException {
        // given
        String cityName = "cityNameTest";
        MainResponse mainResponse = MainResponse.builder().temp(temperature).build();
        WeatherApiResponse weatherApiResponse = WeatherApiResponse.builder().main(mainResponse).build();

        // when
        when(weatherHelper.getCurrentWeatherByCityName(cityName)).thenReturn(weatherApiResponse);
        when(musicHelper.retrieveMusicsByPopCategory()).thenReturn(List.of(MusicResponse.builder().build()));

        // then
        WeatherRhythmResponse response = service.retrieveRhythmsByCityName(cityName);
        Assert.notEmpty(response.getMusics(), "Expected a list filled with at least one party music.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {10.0, 11.0, 12.0, 13.0, 14.0})
    public void retrieveRhythmByCityName_whenTemperatureIsBetweenTenAndFourteen_expectedRockMusic(double temperature) throws IOException, SpotifyWebApiException {
        // given
        String cityName = "cityNameTest";
        MainResponse mainResponse = MainResponse.builder().temp(temperature).build();
        WeatherApiResponse weatherApiResponse = WeatherApiResponse.builder().main(mainResponse).build();

        // when
        when(weatherHelper.getCurrentWeatherByCityName(cityName)).thenReturn(weatherApiResponse);
        when(musicHelper.retrieveMusicsByRockCategory()).thenReturn(List.of(MusicResponse.builder().build()));

        // then
        WeatherRhythmResponse response = service.retrieveRhythmsByCityName(cityName);
        Assert.notEmpty(response.getMusics(), "Expected a list filled with at least one party music.");
    }
}
