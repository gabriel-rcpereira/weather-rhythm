package com.grcp.weatherrhythm.localsong.domain.validation.validator;

import com.grcp.weatherrhythm.localsong.domain.validation.annotation.Latitude;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatitudeValidator implements ConstraintValidator<Latitude, Double> {

    private static final double MIN_LATITUDE_VALUE = -180D;
    private static final double MAX_LATITUDE_VALUE = 180D;

    @Override
    public boolean isValid(Double latitude, ConstraintValidatorContext context) {
        return Optional.ofNullable(latitude).isEmpty() ||
                (latitude > MIN_LATITUDE_VALUE && latitude < MAX_LATITUDE_VALUE);
    }
}
