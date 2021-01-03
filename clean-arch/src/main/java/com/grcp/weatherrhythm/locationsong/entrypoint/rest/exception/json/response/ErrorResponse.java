package com.grcp.weatherrhythm.locationsong.entrypoint.rest.exception.json.response;

import com.grcp.weatherrhythm.locationsong.domain.message.ErrorMessage;
import com.grcp.weatherrhythm.locationsong.gateway.message.MessageGateway;
import javax.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private String message;

    public static class Builder {

        private String message;
        private MessageGateway messageGateway;

        public Builder(MessageGateway messageGateway) {
            this.messageGateway = messageGateway;
        }

        public Builder withConstraintViolation(ConstraintViolation<?> e) {
            ErrorMessage message = messageGateway.retrieveErrorMessage(e.getMessage());
            this.message = message.getMessage();

            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this.message);
        }
    }
}
