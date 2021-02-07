package com.grcp.weatherrhythm.localsong.gateway.weather.impl;

import com.github.javafaker.Faker;
import com.grcp.weatherrhythm.localsong.domain.LocalWeather;
import com.grcp.weatherrhythm.localsong.domain.exception.GatewayException;
import com.grcp.weatherrhythm.localsong.domain.exception.errors.GatewayError;
import com.grcp.weatherrhythm.localsong.gateway.weather.client.WeatherClient;
import com.grcp.weatherrhythm.localsong.gateway.weather.client.model.WeatherClientModel;
import com.grcp.weatherrhythm.localsong.gateway.weather.client.model.WeatherMainModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class LocalWeatherGatewayImplTest {

    private LocalWeatherGatewayImpl localWeatherGateway;

    @Mock
    private WeatherClient weatherClient;

    @BeforeEach
    void setUp() {
        this.localWeatherGateway = new LocalWeatherGatewayImpl(weatherClient);
    }

    @Test
    public void givenValidCity_whenRetrieveLocalWeatherByCityName_thenReturnsValidLocalWeatherWithCelsiusTemperature() {
        //given
        Faker faker = Faker.instance();
        var city = faker.address().city();
        double expectedCelsiusTemperature = 6.95;

        double temp = 280.10;
        WeatherClientModel weatherClientModel = WeatherClientModel.builder()
                .main(WeatherMainModel.builder()
                        .temp(temp)
                        .build())
                .build();
        //when
        when(weatherClient.getWeatherByCityName(city)).thenReturn(weatherClientModel);
        LocalWeather localWeather = this.localWeatherGateway.retrieveLocalWeatherByCityName(city);

        //then
        Assertions.assertNotNull(localWeather);
        Assertions.assertEquals(expectedCelsiusTemperature, localWeather.getCelsiusTemperature(), "expected celsius temperature");
    }

    @Test
    public void givenValidCity_whenRetrieveLocalWeatherByCityNameThrowsClientExceptionFromNotFoundError_thenThrowsGatewayException() {
        //given
        Faker faker = Faker.instance();
        var city = faker.address().city();

        //when
        when(weatherClient.getWeatherByCityName(city)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        Executable executableMethod = () -> this.localWeatherGateway.retrieveLocalWeatherByCityName(city);

        //then
        GatewayException gatewayException = assertThrows(GatewayException.class, executableMethod);
        Assertions.assertEquals(GatewayError.LOCAL_WEATHER_CITY_NAME_WAS_NOT_FOUND, gatewayException.getError());
    }

    @Test
    public void givenValidCity_whenRetrieveLocalWeatherByCityNameThrowsServerExceptionFromNotFoundError_thenThrowsGatewayException() {
        //given
        Faker faker = Faker.instance();
        var city = faker.address().city();

        //when
        when(weatherClient.getWeatherByCityName(city)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        Executable executableMethod = () -> this.localWeatherGateway.retrieveLocalWeatherByCityName(city);

        //then
        GatewayException gatewayException = assertThrows(GatewayException.class, executableMethod);
        Assertions.assertEquals(GatewayError.LOCAL_WEATHER_SERVER_ERROR, gatewayException.getError());
    }

    @Test
    public void givenValidCity_whenRetrieveLocalWeatherByCityNameThrowsRuntimeException_thenThrowsGatewayException() {
        //given
        Faker faker = Faker.instance();
        var city = faker.address().city();

        //when
        when(weatherClient.getWeatherByCityName(city)).thenThrow(new RuntimeException("An error occurred"));
        Executable executableMethod = () -> this.localWeatherGateway.retrieveLocalWeatherByCityName(city);

        //then
        GatewayException gatewayException = assertThrows(GatewayException.class, executableMethod);
        Assertions.assertEquals(GatewayError.LOCAL_WEATHER_FAILED_REQUEST, gatewayException.getError());
    }
}