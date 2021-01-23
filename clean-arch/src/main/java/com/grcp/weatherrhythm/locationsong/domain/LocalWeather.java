package com.grcp.weatherrhythm.locationsong.domain;

import com.grcp.weatherrhythm.locationsong.domain.exception.DomainException;
import com.grcp.weatherrhythm.locationsong.domain.exception.errors.DomainError;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class LocalWeather {

    private double celsiusTemperature;

    public Category retrieveCategoryByTemperature() {
        return Category.retrieveCategory(this.celsiusTemperature)
                .orElseThrow(() -> new DomainException(DomainError.ANY_CATEGORY_FOUND));
    }
}
