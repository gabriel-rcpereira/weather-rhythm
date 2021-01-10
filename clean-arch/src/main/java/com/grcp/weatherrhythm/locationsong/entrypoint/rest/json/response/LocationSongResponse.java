package com.grcp.weatherrhythm.locationsong.entrypoint.rest.json.response;

import com.grcp.weatherrhythm.locationsong.domain.LocationSong;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationSongResponse {

    private LocationInfoResponse locationInfo;
    private Set<SongResponse> songs = new HashSet<>();

    public LocationSongResponse(LocationSong locationSong) {
        this.locationInfo = new LocationInfoResponse.Builder()
                .withLocationInfo(locationSong.getLocation())
                .build();

        this.songs = locationSong.getSongs().stream()
                .map(song -> new SongResponse.Builder()
                        .withSong(song)
                        .build())
                .collect(Collectors.toSet());
    }
}
