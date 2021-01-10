package com.grcp.weatherrhythm.locationsong.domain.exception;

import com.grcp.weatherrhythm.locationsong.domain.exception.errors.GatewayError;
import lombok.Getter;

@Getter
public class GatewayException extends RuntimeException {

    private final GatewayError error;

    public GatewayException(GatewayError error, RuntimeException e) {
        super(e);

        this.error = error;
    }
}
