package com.grcp.weatherrhythm.locationsong.entrypoint.rest.exception.json.response;

import com.grcp.weatherrhythm.locationsong.gateway.message.MessageGateway;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceErrorResponse {

    private final String service = "001";
    private Set<ErrorResponse> errors = new HashSet<>();

    public static class Builder {

        private Set<ErrorResponse> errors = new HashSet<>();
        private MessageGateway messageGateway;

        public Builder(MessageGateway messageGateway) {
            this.messageGateway = messageGateway;
        }

        public Builder withConstraintViolationException(ConstraintViolationException e) {
            this.errors = e.getConstraintViolations().stream()
                    .map(constraintViolation -> new ErrorResponse.Builder(messageGateway)
                            .withConstraintViolation(constraintViolation)
                            .build())
                    .collect(Collectors.toSet());

            return this;
        }

        public ServiceErrorResponse build() {
            return new ServiceErrorResponse(errors);
        }
    }
}
