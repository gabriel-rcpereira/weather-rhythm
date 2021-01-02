package com.grcp.weatherrhythm.locationsong.entrypoint.rest.json.response;

import com.grcp.weatherrhythm.locationsong.domain.LocationWeather;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationWeatherResponse {

    private double celsiusTemperature;

    public static class Builder {

        private double celsiusTemperature;

        public Builder() {
        }

        public Builder withLocationWeather(LocationWeather locationWeather) {
            this.celsiusTemperature = locationWeather.getCelsiusTemperature();
            return this;
        }

        public LocationWeatherResponse build() {
            return new LocationWeatherResponse(this.celsiusTemperature);
        }
    }
}
