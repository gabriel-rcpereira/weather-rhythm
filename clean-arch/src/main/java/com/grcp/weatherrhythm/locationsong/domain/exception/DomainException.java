package com.grcp.weatherrhythm.locationsong.domain.exception;

import java.util.Set;
import javax.validation.ConstraintViolation;

public class DomainException extends RuntimeException {

    private Set<? extends ConstraintViolation<?>> constraintViolations;

    public DomainException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super();

        this.constraintViolations = constraintViolations;
    }
}
