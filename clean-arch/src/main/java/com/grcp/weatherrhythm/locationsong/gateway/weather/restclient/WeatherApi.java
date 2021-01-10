package com.grcp.weatherrhythm.locationsong.gateway.weather.restclient;

import com.grcp.weatherrhythm.locationsong.gateway.weather.restclient.json.WeatherApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${api.weather.name}", url = "${api.weather.url}")
public interface WeatherApi {

    @RequestMapping(method = RequestMethod.GET, value = "data/2.5/weather?q={cityName}&appid=${api.weather.id}")
    WeatherApiResponse getWeatherByCityName(@RequestParam("cityName") String cityName);
}
