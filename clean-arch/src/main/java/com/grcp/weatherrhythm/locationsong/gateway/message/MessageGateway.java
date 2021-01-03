package com.grcp.weatherrhythm.locationsong.gateway.message;

import com.grcp.weatherrhythm.locationsong.domain.message.ErrorMessage;

public interface MessageGateway {

    ErrorMessage retrieveErrorMessage(String key, Object...args);
}
