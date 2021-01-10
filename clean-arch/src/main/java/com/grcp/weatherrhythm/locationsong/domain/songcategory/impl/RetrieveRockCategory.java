package com.grcp.weatherrhythm.locationsong.domain.songcategory.impl;

import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.LocationWeather;
import com.grcp.weatherrhythm.locationsong.domain.songcategory.RetrieveCategory;
import java.util.Optional;

public class RetrieveRockCategory implements RetrieveCategory {

    private static final double TEN_DEGREES = 10D;
    private static final double FOURTEEN_DEGREES = 14D;

    @Override
    public Optional<Category> execute(LocationWeather locationWeather) {
        if (locationWeather.getCelsiusTemperature() >= TEN_DEGREES && locationWeather.getCelsiusTemperature() <= FOURTEEN_DEGREES) {
            return Optional.of(Category.ROCK);
        }
        return Optional.empty();
    }
}
