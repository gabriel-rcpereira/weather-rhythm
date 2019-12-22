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
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class WeatherRhythmTestService {

    private static final String APP_ID = "appIdTest";
//    private static final String CLIENT_ID = "clientId";
//    private static final String CLIENT_SECRET = "clientSecret";

    @Mock
    private WeatherApi weatherApi;

    @Mock
    private MusicHelper musicHelper;

    private WeatherRhythmService service;

    @BeforeEach
    public void loadContext() {
        service = new WeatherRhythmService(APP_ID, weatherApi, musicHelper);
    }

    @Test
    public void retrieveRhythmByCityName_whenTemperatureIsAboveOfThirty() throws IOException, SpotifyWebApiException {
        // given
        String cityName = "cityNameTest";
        MainResponse mainResponse = MainResponse.builder().temp(31).build();
        WeatherApiResponse weatherApiResponse = WeatherApiResponse.builder().main(mainResponse).build();

        // when
        when(weatherApi.getWeather(cityName, APP_ID)).thenReturn(weatherApiResponse);
        when(musicHelper.retrieveMusicsByPartyCategory()).thenReturn(List.of(MusicResponse.builder().build()));

        // then
        WeatherRhythmResponse response = service.retrieveRhythmsByCityName(cityName);
        Assert.notEmpty(response.getMusics(), "Expected a list filled with at least one party music.");
    }
}
