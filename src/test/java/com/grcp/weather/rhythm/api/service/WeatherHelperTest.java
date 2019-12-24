package com.grcp.weather.rhythm.api.service;

import com.grcp.weather.rhythm.api.exception.WeatherMusicException;
import com.grcp.weather.rhythm.restclient.openweather.api.WeatherApi;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;

@SpringBootTest
public class WeatherHelperTest {

    private final static String APP_ID = "testApp";

    @Mock
    private WeatherApi weatherApi;

    private WeatherHelper weatherHelper;

    @BeforeEach
    public void loadContext() {
        weatherHelper = new WeatherHelper(APP_ID, weatherApi);
    }

    @Test
    public void getCurrentWeatherByCityName_whenCityWasNotFound_expectedWeatherMusicExceptionNotFoundError() {
        // when
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        String cityNameNotFound = "cityNameNotFound";

        // given
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND)).when(weatherApi).getWeather(cityNameNotFound, APP_ID);

        // then
        HttpStatus actualHttpStatus = null;
        try {
            weatherHelper.getCurrentWeatherByCityName(cityNameNotFound);
        } catch (WeatherMusicException e) {
            actualHttpStatus = e.getHttpStatus();
        }

        assertEquals(expectedStatus.value(),
                actualHttpStatus.value(),
                "Expected WeatherMusicException with not found status.");
    }

    @Test
    public void getCurrentWeatherByCityName_whenMusicApiReturnsBadRequest_expectedWeatherMusicExceptionPreConditionalFailed() {
        // when
        HttpStatus expectedStatus = HttpStatus.PRECONDITION_FAILED;
        String cityName = "cityNameValid";

        // given
        doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(weatherApi).getWeather(cityName, APP_ID);

        // then
        HttpStatus actualHttpStatus = null;
        try {
            weatherHelper.getCurrentWeatherByCityName(cityName);
        } catch (WeatherMusicException e) {
            actualHttpStatus = e.getHttpStatus();
        }

        assertEquals(expectedStatus.value(),
                actualHttpStatus.value(),
                "Expected WeatherMusicException with pre conditional failed status.");
    }
}
