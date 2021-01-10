package com.grcp.weatherrhythm.locationsong.domain.songcategory.impl;

import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.LocationWeather;
import com.grcp.weatherrhythm.locationsong.domain.songcategory.RetrieveCategory;
import java.util.Optional;

public class RetrievePartyCategory implements RetrieveCategory {

    private static final double THIRTY_DEGREES = 30D;

    @Override
    public Optional<Category> execute(LocationWeather locationWeather) {
        if (locationWeather.getCelsiusTemperature() > THIRTY_DEGREES) {
            return Optional.of(Category.PARTY);
        }
        return Optional.empty();
    }
}
