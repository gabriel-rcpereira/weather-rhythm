package com.grcp.weatherrhythm.locationsong.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class LocationSong {

    private LocationWeather locationWeather;
    @Builder.Default
    private Set<Song> songs = new HashSet<>();
}
