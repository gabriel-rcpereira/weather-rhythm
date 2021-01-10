package com.grcp.weatherrhythm.locationsong.entrypoint.rest.exception.json.response;

import com.grcp.weatherrhythm.locationsong.domain.message.ErrorMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private String code;
    private String message;

    public static class Builder {

        private String code;
        private String message;

        public Builder errorMessage(ErrorMessage errorMessage) {
            this.code = errorMessage.getCode();
            this.message = errorMessage.getMessage();
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this.code, this.message);
        }
    }
}
