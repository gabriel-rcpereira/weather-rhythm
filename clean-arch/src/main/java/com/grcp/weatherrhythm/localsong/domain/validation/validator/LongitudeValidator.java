package com.grcp.weatherrhythm.localsong.domain.validation.validator;

import com.grcp.weatherrhythm.localsong.domain.validation.annotation.Longitude;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongitudeValidator implements ConstraintValidator<Longitude, Double> {

    private static final double MIN_LONGITUDE_VALUE = -180D;
    private static final double MAX_LONGITUDE_VALUE = 180D;

    @Override
    public boolean isValid(Double longitude, ConstraintValidatorContext context) {
        return Optional.ofNullable(longitude).isEmpty() ||
                (longitude > MIN_LONGITUDE_VALUE && longitude < MAX_LONGITUDE_VALUE);
    }
}
