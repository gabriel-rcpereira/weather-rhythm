package com.grcp.weather.rhythm.api.service;

import org.springframework.stereotype.Component;

@Component
public class ValidatorHelper {

    private static final double THIRTY_DEGREES = 30.0;
    private static final double FIFTEEN_DEGREES = 15.0;
    private static final double TEN_DEGREES = 10.0;

    public boolean isBetweenTenAndFourteenDegrees(double celsiusDegree) {
        return celsiusDegree >= TEN_DEGREES && celsiusDegree < FIFTEEN_DEGREES;
    }

    public boolean isBetweenFifteenAndThirtyDegrees(double celsiusDegree) {
        return celsiusDegree >= FIFTEEN_DEGREES && celsiusDegree <= THIRTY_DEGREES;
    }

    public boolean isAboveThirtyDegrees(double celsiusDegree) {
        return celsiusDegree > THIRTY_DEGREES;
    }
}
