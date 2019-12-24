package com.grcp.weather.rhythm.configuration.feign;

import static java.lang.Math.incrementExact;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.*;

import feign.RetryableException;
import feign.Retryer;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Scope(value = SCOPE_PROTOTYPE)
@Component
public class FeignRetryerConfig implements Retryer {

    private final int maxAttempts;
    private final long backoff;
    private int attempt;

    public FeignRetryerConfig(long backoff, int maxAttempts) {
        this.backoff = backoff;
        this.maxAttempts = maxAttempts;
        this.attempt = 1;
    }

    @Override
    public void continueOrPropagate(RetryableException ex) {
        attempt = incrementExact(attempt);
        if (attempt >= maxAttempts) {
            log.error("Reached the max attempts [{}]..", maxAttempts, ex);
            if (is5xxStatus(ex)) {
                throw new HttpServerErrorException(HttpStatus.resolve(ex.status()), ex.getMessage());
            } else {
                throw new HttpClientErrorException(HttpStatus.PRECONDITION_REQUIRED, ex.getMessage());
            }
        }

        try {
            log.error("Retrying the request in [{}]ms. Attempt: [{}] from MaxAttempts: [{}]..", backoff, attempt, maxAttempts, ex);
            Thread.sleep(backoff);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean is5xxStatus(RetryableException ex) {
        return String.valueOf(ex.status()).startsWith("5");
    }

    @Override
    public Retryer clone() {
        return new FeignRetryerConfig(backoff, maxAttempts);
    }
}
