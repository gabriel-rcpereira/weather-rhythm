package com.grcp.weatherrhythm.locationsong.domain.exception.errors;

import lombok.Getter;

@Getter
public enum DomainError {
    ANY_CATEGORY_FOUND("001.001");

    private String errorCode;

    DomainError(String errorCode) {
        this.errorCode = errorCode;
    }
}
