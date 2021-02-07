package com.grcp.weatherrhythm.localsong.domain.exception;

import com.grcp.weatherrhythm.localsong.domain.exception.errors.DomainError;
import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private final DomainError error;

    public DomainException(DomainError error) {
        super();

        this.error = error;
    }
}
