package com.grcp.weatherrhythm.locationsong.entrypoint.rest.json.response;

import com.grcp.weatherrhythm.locationsong.domain.LocalSong;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalSongResponse {

    private LocalInfoResponse locationInfo;
    private Set<SongResponse> songs = new HashSet<>();

    public LocalSongResponse(LocalSong localSong) {
        this.locationInfo = new LocalInfoResponse.Builder()
                .withLocationInfo(localSong.getLocation())
                .build();

        this.songs = localSong.getSongs().stream()
                .map(song -> new SongResponse.Builder()
                        .withSong(song)
                        .build())
                .collect(Collectors.toSet());
    }
}
