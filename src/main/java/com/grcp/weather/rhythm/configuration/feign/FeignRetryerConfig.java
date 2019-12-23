package com.grcp.weather.rhythm.configuration.feign;

import lombok.extern.slf4j.Slf4j;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.*;

import feign.RetryableException;
import feign.Retryer;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

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
    public void continueOrPropagate(RetryableException e) {
        if (attempt++ >= maxAttempts) {
            log.error("Reached the max attempts..");
            throw new HttpServerErrorException(HttpStatus.resolve(e.status()));
        }

        try {
            log.info("Retrying the request in [{}]ms. Attempt: [].", backoff, attempt);
            Thread.sleep(backoff);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Retryer clone() {
        return new FeignRetryerConfig(backoff, maxAttempts);
    }
}
