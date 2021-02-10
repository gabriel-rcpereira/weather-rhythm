package com.grcp.weatherrhythm.localsong.gateway.weather.adapter;

import com.grcp.weatherrhythm.localsong.gateway.weather.adapter.model.WeatherClientModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${api.weather.name}", url = "${api.weather.url}")
public interface WeatherAdapter {

    @RequestMapping(method = RequestMethod.GET, value = "data/2.5/weather?q={cityName}&appid=${api.weather.id}")
    WeatherClientModel getWeatherByCityName(@RequestParam("cityName") String cityName);
}