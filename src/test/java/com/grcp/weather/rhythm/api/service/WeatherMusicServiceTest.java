package com.grcp.weather.rhythm.api.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.grcp.weather.rhythm.api.exception.WeatherMusicErrorReason;
import com.grcp.weather.rhythm.api.exception.WeatherMusicException;
import com.grcp.weather.rhythm.api.model.MusicResponse;
import com.grcp.weather.rhythm.api.model.WeatherMusicResponse;
import com.grcp.weather.rhythm.restclient.openweather.model.MainResponse;
import com.grcp.weather.rhythm.restclient.openweather.model.WeatherApiResponse;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class WeatherMusicServiceTest {

    @Mock
    private WeatherHelper weatherHelper;

    @Mock
    private MusicHelper musicHelper;

    private WeatherMusicService service;

    @BeforeEach
    public void loadContext() {
        service = new WeatherMusicService(weatherHelper, musicHelper, new ValidatorHelper());
    }

    @Test
    public void retrieveRhythmsByCityName_whenCityNameIsInvalid_expectedWeatherMusicException() throws WeatherMusicException {
        // given
        String cityNameInvalid = "cityNameTestInvalid";

        // when
        when(weatherHelper.getCurrentWeatherByCityName(cityNameInvalid))
                .thenThrow(new WeatherMusicException(WeatherMusicErrorReason.CITY_NOT_FOUND, new Exception("City was not found.")));

        // then
        assertThrows(
                WeatherMusicException.class,
                () -> service.retrieveRhythmsByCityName(cityNameInvalid), "Expected the WeatherMusicException.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {303.25, 304.15, 308.15})
    public void retrieveRhythmByCityName_whenTemperatureIsAboveOfThirty_expectedPartyMusic(double temperature) throws WeatherMusicException {
        // given
        String cityName = "cityNameTest";
        MainResponse mainResponse = MainResponse.builder().temp(temperature).build();
        WeatherApiResponse weatherApiResponse = WeatherApiResponse.builder().main(mainResponse).build();

        // when
        when(weatherHelper.getCurrentWeatherByCityName(cityName)).thenReturn(weatherApiResponse);
        when(musicHelper.retrieveMusicsByPartyCategory()).thenReturn(List.of(MusicResponse.builder().build()));

        // then
        WeatherMusicResponse response = service.retrieveRhythmsByCityName(cityName);
        Assert.notEmpty(response.getMusics(), "Expected a list filled with at least one party music.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {288.15, 291.15, 293.15, 298.65, 300.0, 303.15})
    public void retrieveRhythmByCityName_whenTemperatureIsBetweenFifteenAndThirty_expectedPopMusic(double temperature) throws WeatherMusicException {
        // given
        String cityName = "cityNameTest";
        MainResponse mainResponse = MainResponse.builder().temp(temperature).build();
        WeatherApiResponse weatherApiResponse = WeatherApiResponse.builder().main(mainResponse).build();

        // when
        when(weatherHelper.getCurrentWeatherByCityName(cityName)).thenReturn(weatherApiResponse);
        when(musicHelper.retrieveMusicsByPopCategory()).thenReturn(List.of(MusicResponse.builder().build()));

        // then
        WeatherMusicResponse response = service.retrieveRhythmsByCityName(cityName);
        Assert.notEmpty(response.getMusics(), "Expected a list filled with at least one pop music.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {283.15, 285.0, 286.65, 287.15, 288.05})
    public void retrieveRhythmByCityName_whenTemperatureIsBetweenTenAndFourteen_expectedRockMusic(double temperature) throws WeatherMusicException {
        // given
        String cityName = "cityNameTest";
        MainResponse mainResponse = MainResponse.builder().temp(temperature).build();
        WeatherApiResponse weatherApiResponse = WeatherApiResponse.builder().main(mainResponse).build();

        // when
        when(weatherHelper.getCurrentWeatherByCityName(cityName)).thenReturn(weatherApiResponse);
        when(musicHelper.retrieveMusicsByRockCategory()).thenReturn(List.of(MusicResponse.builder().build()));

        // then
        WeatherMusicResponse response = service.retrieveRhythmsByCityName(cityName);
        Assert.notEmpty(response.getMusics(), "Expected a list filled with at least one rock music.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {263.15, 268.15, 273.15, 281.15, 282.65, 283.05})
    public void retrieveRhythmByCityName_whenTemperatureIsBelowTen_expectedClassicMusic(double temperature) throws WeatherMusicException {
        // given
        String cityName = "cityNameTest";
        MainResponse mainResponse = MainResponse.builder().temp(temperature).build();
        WeatherApiResponse weatherApiResponse = WeatherApiResponse.builder().main(mainResponse).build();

        // when
        when(weatherHelper.getCurrentWeatherByCityName(cityName)).thenReturn(weatherApiResponse);
        when(musicHelper.retrieveMusicsByClassicalCategory()).thenReturn(List.of(MusicResponse.builder().build()));

        // then
        WeatherMusicResponse response = service.retrieveRhythmsByCityName(cityName);
        Assert.notEmpty(response.getMusics(), "Expected a list filled with at least one classic music.");
    }
}
