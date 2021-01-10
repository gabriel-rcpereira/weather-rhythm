package com.grcp.weatherrhythm.locationsong.entrypoint.rest.exception.handler;

import com.grcp.weatherrhythm.locationsong.domain.exception.DomainException;
import com.grcp.weatherrhythm.locationsong.domain.exception.GatewayException;
import com.grcp.weatherrhythm.locationsong.domain.message.ErrorMessage;
import com.grcp.weatherrhythm.locationsong.entrypoint.rest.exception.json.response.ServiceErrorResponse;
import com.grcp.weatherrhythm.locationsong.gateway.message.MessageGateway;
import java.util.Set;
import java.util.stream.Collectors;
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

        Set<ErrorMessage> errorMessages = e.getConstraintViolations().stream()
                .map(constraintViolation -> retrieveErrorMessage(constraintViolation.getMessage()))
                .collect(Collectors.toSet());

        ServiceErrorResponse serviceErrorResponse = new ServiceErrorResponse.Builder()
                .errorMessages(errorMessages)
                .build();

        return ResponseEntity.badRequest().body(serviceErrorResponse);
    }

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    ResponseEntity<ServiceErrorResponse> handleDomainException(DomainException e) {
        log.error("An domain error occurred.", e);

        ErrorMessage errorMessage = retrieveErrorMessage(e.getError().getErrorCode());
        ServiceErrorResponse serviceErrorResponse = new ServiceErrorResponse.Builder()
                .errorMessage(errorMessage)
                .build();

        return ResponseEntity.unprocessableEntity().body(serviceErrorResponse);
    }

    @ExceptionHandler(GatewayException.class)
    ResponseEntity<ServiceErrorResponse> handleGatewayException(GatewayException e) {
        log.error("An domain error occurred.", e);

        ErrorMessage errorMessage = retrieveErrorMessage(e.getError().getErrorCode());
        ServiceErrorResponse serviceErrorResponse = new ServiceErrorResponse.Builder()
                .errorMessage(errorMessage)
                .build();
        HttpStatus statusError = e.getError().getStatus();

        return ResponseEntity.status(statusError).body(serviceErrorResponse);
    }

    private ErrorMessage retrieveErrorMessage(String message) {
        return messageGateway.retrieveErrorMessage(message);
    }
}
