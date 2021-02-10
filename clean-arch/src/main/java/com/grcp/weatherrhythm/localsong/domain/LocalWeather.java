package com.grcp.weatherrhythm.localsong.domain;

import com.grcp.weatherrhythm.localsong.domain.exception.DomainException;
import com.grcp.weatherrhythm.localsong.domain.exception.errors.DomainError;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@ToString
@Value
@Builder(toBuilder = true)
public class LocalWeather {

    private double celsiusTemperature;

    public Category retrieveCategoryByTemperature() {
        return Category.retrieveCategory(this.celsiusTemperature)
                .orElseThrow(() -> new DomainException(DomainError.ANY_CATEGORY_FOUND));
    }
}
