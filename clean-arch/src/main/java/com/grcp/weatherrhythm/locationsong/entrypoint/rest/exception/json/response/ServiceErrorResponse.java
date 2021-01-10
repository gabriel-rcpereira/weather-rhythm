package com.grcp.weatherrhythm.locationsong.entrypoint.rest.exception.json.response;

import com.grcp.weatherrhythm.locationsong.domain.message.ErrorMessage;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
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

        private final Set<ErrorMessage> errorMessages;

        public Builder() {
            this.errorMessages = new HashSet<>();
        }

        public Builder errorMessages(Set<ErrorMessage> errorMessages) {
            this.errorMessages.addAll(errorMessages);
            return this;
        }

        public Builder errorMessage(ErrorMessage errorMessage) {
            this.errorMessages.add(errorMessage);
            return this;
        }

        public ServiceErrorResponse build() {
            Set<ErrorResponse> errorsResponse = this.errorMessages.stream().map(this::createErrorResponse).collect(Collectors.toSet());
            return new ServiceErrorResponse(errorsResponse);
        }

        private ErrorResponse createErrorResponse(ErrorMessage errorMessage) {
            return new ErrorResponse.Builder()
                    .errorMessage(errorMessage)
                    .build();
        }
    }
}
