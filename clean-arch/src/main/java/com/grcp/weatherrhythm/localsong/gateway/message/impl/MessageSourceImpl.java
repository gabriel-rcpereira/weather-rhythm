package com.grcp.weatherrhythm.localsong.gateway.message.impl;

import com.grcp.weatherrhythm.localsong.domain.message.ErrorMessage;
import com.grcp.weatherrhythm.localsong.gateway.message.MessageGateway;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageSourceImpl implements MessageGateway {

    private final MessageSource messageSource;

    public MessageSourceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public ErrorMessage retrieveErrorMessage(String key, Object...args) {
        log.info("Retrieving error message from key [{}].", key);

        String message = messageSource.getMessage(key, args, Locale.getDefault());
        return new ErrorMessage(key, message);
    }
}
