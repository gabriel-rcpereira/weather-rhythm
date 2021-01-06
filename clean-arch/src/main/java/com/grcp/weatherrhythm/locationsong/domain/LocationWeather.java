package com.grcp.weatherrhythm.locationsong.domain;

import com.grcp.weatherrhythm.locationsong.domain.exception.DomainException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class LocationWeather {

    private double celsiusTemperature;

    public Category retrieveCategoryByTemperature() {
        if (this.isAboveThirtyDegrees()) {
            return Category.PARTY;
        }

        if (this.isBetweenFifteenAndThirtyDegrees()) {
            return Category.POP;
        }

        if (this.isBetweenTenAndFourteenDegrees()) {
            return Category.ROCK;
        }

        return Category.CLASSICAL;
    }

    public void validate(Validator validator) {
        Set<ConstraintViolation<LocationWeather>> constraintViolations = validator.validate(this);

        if (!constraintViolations.isEmpty()) {
            throw new DomainException(constraintViolations);
        }
    }

    private boolean isAboveThirtyDegrees() {
        return this.celsiusTemperature > 30;
    }

    private boolean isBetweenFifteenAndThirtyDegrees() {
        return this.celsiusTemperature >= 15 && this.celsiusTemperature <= 30;
    }

    private boolean isBetweenTenAndFourteenDegrees() {
        return this.celsiusTemperature >= 10 && this.celsiusTemperature <= 14;
    }
}
