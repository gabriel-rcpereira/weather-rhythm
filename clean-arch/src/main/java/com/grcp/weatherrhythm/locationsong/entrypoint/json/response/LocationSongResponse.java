package com.grcp.weatherrhythm.locationsong.entrypoint.json.response;

import com.grcp.weatherrhythm.locationsong.domain.LocationSong;
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
public class LocationSongResponse {

    private LocationWeatherResponse locationWeather;
    private Set<SongResponse> songs = new HashSet<>();

    public static class Builder {

        private LocationWeatherResponse locationWeather;
        private Set<SongResponse> songs = new HashSet<>();

        public Builder() {
        }

        public Builder withLocationSong(LocationSong locationSong) {
                this.locationWeather = new LocationWeatherResponse.Builder()
                        .withLocationWeather(locationSong.getLocationWeather())
                        .build();

            this.songs = locationSong.getSongs().stream()
                    .map(song -> new SongResponse.Builder()
                            .withSong(song)
                            .build())
                    .collect(Collectors.toSet());

            return this;
        }

        public LocationSongResponse build() {
            return new LocationSongResponse(this.locationWeather, this.songs);
        }
    }
}
