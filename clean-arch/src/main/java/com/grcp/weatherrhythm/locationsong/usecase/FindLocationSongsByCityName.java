package com.grcp.weatherrhythm.locationsong.usecase;

import com.grcp.weatherrhythm.locationsong.domain.LocationSong;
import org.springframework.stereotype.Component;

@Component
public class FindLocationSongsByCityName {

    public LocationSong execute(String cityName) {
        return LocationSong.builder().build();
    }
}
