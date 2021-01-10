package com.grcp.weatherrhythm.locationsong.domain.exception;

import com.grcp.weatherrhythm.locationsong.domain.exception.errors.DomainError;

public class DomainException extends RuntimeException {

    private final DomainError domainError;

    public DomainException(DomainError domainError) {
        super();

        this.domainError = domainError;
    }
}
