package com.grcp.weatherrhythm.locationsong.domain.songcategory.impl;

import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.LocalWeather;
import com.grcp.weatherrhythm.locationsong.domain.songcategory.RetrieveCategory;
import java.util.Optional;

public class RetrieveClassicalCategory implements RetrieveCategory {

    private static final double TEN_DEGREES = 10D;

    @Override
    public Optional<Category> execute(LocalWeather localWeather) {
        if (localWeather.getCelsiusTemperature() < TEN_DEGREES) {
            return Optional.of(Category.CLASSICAL);
        }
        return Optional.empty();
    }
}
