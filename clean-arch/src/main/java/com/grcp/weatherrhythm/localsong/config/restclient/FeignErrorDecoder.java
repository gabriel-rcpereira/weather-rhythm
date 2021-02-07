package com.grcp.weatherrhythm.localsong.config.restclient;

import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public class FeignErrorDecoder implements ErrorDecoder {

    private static final int DEFAULT_STATUS_ERROR = 0;

    @Override
    public Exception decode(String methodKey, Response response) {
        Optional<HttpStatus> solvedStatusOpt = Optional.ofNullable(HttpStatus.resolve(response.status()));

        if (solvedStatusOpt.isPresent()) {
            HttpStatus solvedStatus = solvedStatusOpt.get();
            if (solvedStatus.is4xxClientError()) {
                return new HttpClientErrorException(
                        solvedStatus,
                        solvedStatus.name(),
                        retrieveBodyAsByte(response),
                        Charset.defaultCharset());
            }

            if (solvedStatus.is5xxServerError()) {
                return new HttpServerErrorException(
                        solvedStatus,
                        solvedStatus.name(),
                        retrieveBodyAsByte(response),
                        Charset.defaultCharset());
            }
        }

        return new RuntimeException("An error occurred: " + response.reason());
    }

    private byte[] retrieveBodyAsByte(Response response) {
        try {
            return response.body().asInputStream().readAllBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("Feign Decoder Error when tried to convert request body to bytes.", e);
        }
    }
}
