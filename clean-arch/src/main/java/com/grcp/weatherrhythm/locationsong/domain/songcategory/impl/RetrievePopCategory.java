package com.grcp.weatherrhythm.locationsong.domain.songcategory.impl;

import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.LocationWeather;
import com.grcp.weatherrhythm.locationsong.domain.songcategory.RetrieveCategory;
import java.util.Optional;

public class RetrievePopCategory implements RetrieveCategory {

    private static final double FIFTEEN_DEGREES = 15D;
    private static final double THIRTY_DEGREES = 30D;

    @Override
    public Optional<Category> execute(LocationWeather locationWeather) {
        if (locationWeather.getCelsiusTemperature() >= FIFTEEN_DEGREES && locationWeather.getCelsiusTemperature() <= THIRTY_DEGREES) {
            return Optional.of(Category.POP);
        }
        return Optional.empty();
    }
}
