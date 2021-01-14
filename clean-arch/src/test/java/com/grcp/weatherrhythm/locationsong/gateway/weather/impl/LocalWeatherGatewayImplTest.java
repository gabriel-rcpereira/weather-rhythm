package com.grcp.weatherrhythm.locationsong.gateway.weather.impl;

import com.github.javafaker.Faker;
import com.grcp.weatherrhythm.locationsong.domain.LocalWeather;
import com.grcp.weatherrhythm.locationsong.gateway.weather.restclient.WeatherApi;
import com.grcp.weatherrhythm.locationsong.gateway.weather.restclient.json.WeatherApiResponse;
import com.grcp.weatherrhythm.locationsong.gateway.weather.restclient.json.WeatherMainResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
class LocalWeatherGatewayImplTest {

    private LocalWeatherGatewayImpl localWeatherGateway;

    @Mock
    private WeatherApi weatherApi;

    @BeforeEach
    void setUp() {
        this.localWeatherGateway = new LocalWeatherGatewayImpl(weatherApi);
    }

    @Test
    public void givenValidCity_whenRetrieveLocalWeatherByCityName_thenReturnsValidLocalWeatherWithCelsiusTemperature() {
        //given
        Faker faker = Faker.instance();
        var city = faker.address().city();
        double expectedCelsiusTemperature = 6.95;

        double temp = 280.10;
        WeatherApiResponse weatherApiResponse = WeatherApiResponse.builder()
                .main(WeatherMainResponse.builder()
                        .temp(temp)
                        .build())
                .build();
        //when
        when(weatherApi.getWeatherByCityName(city)).thenReturn(weatherApiResponse);
        LocalWeather localWeather = this.localWeatherGateway.retrieveLocalWeatherByCityName(city);

        //then
        Assertions.assertNotNull(localWeather);
        Assertions.assertEquals(expectedCelsiusTemperature, localWeather.getCelsiusTemperature(), "expected celsius temperature");
    }
}