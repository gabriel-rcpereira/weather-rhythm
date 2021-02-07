package com.grcp.weatherrhythm.localsong.gateway.message;

import com.grcp.weatherrhythm.localsong.domain.message.ErrorMessage;

public interface MessageGateway {

    ErrorMessage retrieveErrorMessage(String key, Object...args);
}
