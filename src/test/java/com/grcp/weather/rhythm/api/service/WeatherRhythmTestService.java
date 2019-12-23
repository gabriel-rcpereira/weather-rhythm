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
import org.mockito.InjectMocks;
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

    @InjectMocks
    private WeatherRhythmService service;
    
    @ParameterizedTest
    @ValueSource(doubles = {303.25, 304.15, 308.15})
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
    @ValueSource(doubles = {288.15, 291.15, 293.15, 298.65, 300.0, 303.15})
    public void retrieveRhythmByCityName_whenTemperatureIsBetweenFifteenAndThirty_expectedPopMusic(double temperature) throws IOException, SpotifyWebApiException {
        // given
        String cityName = "cityNameTest";
        MainResponse mainResponse = MainResponse.builder().temp(temperature).build();
        WeatherApiResponse weatherApiResponse = WeatherApiResponse.builder().main(mainResponse).build();

        // when
        when(weatherHelper.getCurrentWeatherByCityName(cityName)).thenReturn(weatherApiResponse);
        when(musicHelper.retrieveMusicsByPopCategory()).thenReturn(List.of(MusicResponse.builder().build()));

        // then
        WeatherRhythmResponse response = service.retrieveRhythmsByCityName(cityName);
        Assert.notEmpty(response.getMusics(), "Expected a list filled with at least one pop music.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {283.15, 285.0, 286.65, 287.15, 288.05})
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
        Assert.notEmpty(response.getMusics(), "Expected a list filled with at least one rock music.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {263.15, 268.15, 273.15, 281.15, 282.65, 283.05})
    public void retrieveRhythmByCityName_whenTemperatureIsBelowTen_expectedClassicMusic(double temperature) throws IOException, SpotifyWebApiException {
        // given
        String cityName = "cityNameTest";
        MainResponse mainResponse = MainResponse.builder().temp(temperature).build();
        WeatherApiResponse weatherApiResponse = WeatherApiResponse.builder().main(mainResponse).build();

        // when
        when(weatherHelper.getCurrentWeatherByCityName(cityName)).thenReturn(weatherApiResponse);
        when(musicHelper.retrieveMusicsByClassicalCategory()).thenReturn(List.of(MusicResponse.builder().build()));

        // then
        WeatherRhythmResponse response = service.retrieveRhythmsByCityName(cityName);
        Assert.notEmpty(response.getMusics(), "Expected a list filled with at least one classic music.");
    }
}
