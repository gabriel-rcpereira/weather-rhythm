package com.grcp.weatherrhythm.locationsong.domain.message;

import lombok.Value;

@Value
public class ErrorMessage {

    private String code;
    private String message;
}
