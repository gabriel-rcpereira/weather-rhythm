package com.grcp.weatherrhythm.localsong.domain.message;

import lombok.Value;

@Value
public class ErrorMessage {

    private String code;
    private String message;
}
