package com.grcp.weatherrhythm.localsong.domain.exception;

import java.util.Set;
import javax.validation.ConstraintViolation;

public class UseCaseValidationException extends RuntimeException {

    private final Set<? extends ConstraintViolation<?>> constraintViolations;

    public UseCaseValidationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super();

        this.constraintViolations = constraintViolations;
    }
}
