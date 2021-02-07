package com.grcp.weatherrhythm.localsong.entrypoint.rest.json.response;

import com.grcp.weatherrhythm.localsong.domain.LocalInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalInfoResponse {

    private double celsiusTemperature;

    public static class Builder {

        private double celsiusTemperature;

        public Builder() {
        }

        public Builder withLocationInfo(LocalInfo localInfo) {
            this.celsiusTemperature = localInfo.getCelsiusTemperature();
            return this;
        }

        public LocalInfoResponse build() {
            return new LocalInfoResponse(this.celsiusTemperature);
        }
    }
}
