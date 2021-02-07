package com.grcp.weatherrhythm.localsong.gateway.weather.client;

import com.github.javafaker.Faker;
import com.grcp.weatherrhythm.localsong.gateway.weather.client.model.WeatherClientModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The tests were created to make sure how api works and how errors we would catch.
 * They are not running during the usually test task.
 * @author gabrielp
 */
@SpringBootTest
class WeatherClientIT {

    @Autowired
    private WeatherClient weatherClient;

    @Test
    void givenValidCity_whenGetWeatherByCityName_thenReturnsLocalWeather() {
        //given
        var city = "Campinas";

        //when
        WeatherClientModel weatherClientModel = weatherClient.getWeatherByCityName(city);

        //then
        Assertions.assertNotNull(weatherClientModel, "expected response from api");
    }

    @Test
    void givenInvalidCity_whenGetWeatherByCityName_thenReturnsResponseWithError() {
        //given
        var city = Faker.instance().pokemon().name();

        //when
        Executable executableApi = () -> weatherClient.getWeatherByCityName(city);

        //then
        assertThrows(HttpClientErrorException.class, executableApi, "expected HttpClientErrorException from api execution");
    }

}