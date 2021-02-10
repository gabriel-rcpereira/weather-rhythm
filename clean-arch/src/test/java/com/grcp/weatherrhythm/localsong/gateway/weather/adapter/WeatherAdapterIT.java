package com.grcp.weatherrhythm.localsong.gateway.weather.adapter;

import com.github.javafaker.Faker;
import com.grcp.weatherrhythm.localsong.gateway.weather.adapter.model.WeatherClientModel;
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
class WeatherAdapterIT {

    private final Faker faker = Faker.instance();

    @Autowired
    private WeatherAdapter weatherAdapter;

    @Test
    void givenValidCity_whenRetrieveWeatherByCityName_thenReturnsLocalWeather() {
        //given
        var city = "Campinas";

        //when
        WeatherClientModel weatherClientModel = weatherAdapter.retrieveWeatherByCityName(city);

        //then
        Assertions.assertNotNull(weatherClientModel, "expected response from api");
    }

    @Test
    void givenInvalidCity_whenRetrieveWeatherByCityName_thenReturnsResponseWithError() {
        //given
        var city = Faker.instance().pokemon().name();

        //when
        Executable executableApi = () -> weatherAdapter.retrieveWeatherByCityName(city);

        //then
        assertThrows(HttpClientErrorException.class, executableApi, "expected HttpClientErrorException from api execution");
    }

    @Test
    void givenValidLatitudeAndLongitude_whenRetrieveWeatherByLatitudeAndLongitude_thenReturnsLocalWeather() {
        //given
        Double latitude = faker.number().randomDouble(5, -95, 95);
        Double longitude = faker.number().randomDouble(5, -180, 180);

        //when
        WeatherClientModel weatherClientModel = weatherAdapter.retrieveWeatherByLatitudeAndLongitude(latitude, longitude);

        //then
        Assertions.assertNotNull(weatherClientModel, "expected response from api");
    }

    @Test
    void givenInvalidLatitudeAndLongitude_whenRetrieveWeatherByLatitudeAndLongitude_thenReturnsLocalWeather() {
        //given
        Double latitude = faker.number().randomDouble(5, 95, 180);
        Double longitude = faker.number().randomDouble(5, -180, 180);

        //when
        Executable executableApi = () -> weatherAdapter.retrieveWeatherByLatitudeAndLongitude(latitude, longitude);

        //then
        assertThrows(HttpClientErrorException.class, executableApi, "expected HttpClientErrorException from api execution");
    }
}