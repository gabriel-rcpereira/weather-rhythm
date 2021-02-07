package com.grcp.weatherrhythm.localsong.domain.exception.errors;

import lombok.Getter;

@Getter
public enum DomainError {

    ANY_CATEGORY_FOUND("003.001");

    private String errorCode;

    DomainError(String errorCode) {
        this.errorCode = errorCode;
    }
}
