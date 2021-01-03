package com.grcp.weatherrhythm.locationsong.entrypoint.rest.exception.handler;

import com.grcp.weatherrhythm.locationsong.entrypoint.rest.exception.json.response.ServiceErrorResponse;
import com.grcp.weatherrhythm.locationsong.gateway.message.MessageGateway;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @Autowired
    private MessageGateway messageGateway;

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ServiceErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("An entrypoint constraint was violated.", e);

        ServiceErrorResponse serviceErrorResponse = new ServiceErrorResponse.Builder(messageGateway)
                .withConstraintViolationException(e)
                .build();

        return ResponseEntity.badRequest().body(serviceErrorResponse);
    }
}
