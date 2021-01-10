package com.grcp.weatherrhythm.locationsong.gateway.weather.restclient;

import com.grcp.weatherrhythm.locationsong.gateway.weather.restclient.json.WeatherApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherApiIT {

    @Autowired
    private WeatherApi weatherApi;

    @Test
    void givenValidCity_whenGetWeatherByCityName_thenReturnsLocalWeather() {
        //given
        var city = "Campinas";

        //when
        WeatherApiResponse weatherApiResponse = weatherApi.getWeather(city);

        //then
        Assertions.assertNotNull(weatherApiResponse, "expected response from api");
    }

    @Test
    void givenInvalidCity_whenGetWeatherByCityName_thenReturnsResponseWithError() {
        //given
        var city = "BluBlu";

        //when
        WeatherApiResponse weatherApiResponse = weatherApi.getWeather(city);

        //then
        Assertions.assertNotNull(weatherApiResponse, "expected response from api");
        Assertions.assertNotNull(weatherApiResponse.getErrorDetail(), "expected error detail response from api");
        Assertions.assertEquals(404, weatherApiResponse.getErrorDetail().getStatus(), "expected error detail response from api");
        Assertions.assertEquals("city not found", weatherApiResponse.getErrorDetail().getMessage(), "expected error detail response from api");
    }

}