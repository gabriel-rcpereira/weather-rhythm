package com.grcp.weatherrhythm.locationsong.usecase.mapper;

import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.LocationInfo;
import com.grcp.weatherrhythm.locationsong.domain.LocationSong;
import com.grcp.weatherrhythm.locationsong.domain.LocationWeather;
import com.grcp.weatherrhythm.locationsong.domain.Song;
import java.util.Set;

public class LocationSongMapper {

    public static final LocationSongMapper INSTANCE = new LocationSongMapper();

    private LocationSongMapper() {
    }

    public LocationSong mapToLocationSong(String city,
                                          Category category,
                                          LocationWeather locationWeather,
                                          Set<Song> songs) {
        return LocationSong.builder()
                .location(mapToLocationInfo(city, category, locationWeather))
                .songs(songs)
                .build();
    }

    private LocationInfo mapToLocationInfo(String city, Category category, LocationWeather locationWeather) {
        return LocationInfo.builder()
                .city(city)
                .category(category)
                .celsiusTemperature(locationWeather.getCelsiusTemperature())
                .build();
    }
}
