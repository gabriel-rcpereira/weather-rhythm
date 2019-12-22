package com.grcp.weather.rhythm.restclient.api;

import com.grcp.weather.rhythm.restclient.model.WeatherApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "weatherApi", url = "https://api.openweathermap.org")
public interface WeatherApi {

    @RequestMapping(method = RequestMethod.GET, value = "data/2.5/weather?q={cityName}&appid={appId}")
    WeatherApiResponse getWeather(@RequestParam("cityName") String cityName, @RequestParam("appId") String appId);

}
