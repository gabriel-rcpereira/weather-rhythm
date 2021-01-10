package com.grcp.weatherrhythm.locationsong.entrypoint.rest.json.response;

import com.grcp.weatherrhythm.locationsong.domain.LocationInfo;
import com.grcp.weatherrhythm.locationsong.domain.LocationWeather;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationInfoResponse {

    private double celsiusTemperature;

    public static class Builder {

        private double celsiusTemperature;

        public Builder() {
        }

        public Builder withLocationInfo(LocationInfo locationInfo) {
            this.celsiusTemperature = locationInfo.getCelsiusTemperature();
            return this;
        }

        public LocationInfoResponse build() {
            return new LocationInfoResponse(this.celsiusTemperature);
        }
    }
}
