package com.grcp.weather.rhythm.restclient;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (HttpStatus.resolve(response.status()).is5xxServerError()) {
            log.error("Retrying the request. [{}] status. Error reason: {}", response.status(), response.reason());
            return new RetryableException(
                    response.status(),
                    response.reason(),
                    response.request().httpMethod(),
                    null,
                    response.request());
        } else {
            log.error("Reached the [{}] status. Error reason: {}", response.status(), response.reason());
            return new HttpClientErrorException(HttpStatus.resolve(response.status()));
        }
    }
}
