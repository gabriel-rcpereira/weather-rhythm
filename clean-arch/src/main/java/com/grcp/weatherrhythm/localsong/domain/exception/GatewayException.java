package com.grcp.weatherrhythm.localsong.domain.exception;

import com.grcp.weatherrhythm.localsong.domain.exception.errors.GatewayError;
import lombok.Getter;

@Getter
public class GatewayException extends RuntimeException {

    private final GatewayError error;

    public GatewayException(GatewayError error, RuntimeException e) {
        super(e);

        this.error = error;
    }

    public GatewayException(GatewayError error) {
        this(error, null);
    }
}
