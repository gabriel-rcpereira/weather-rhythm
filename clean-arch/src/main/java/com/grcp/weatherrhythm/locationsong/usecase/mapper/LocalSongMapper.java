package com.grcp.weatherrhythm.locationsong.usecase.mapper;

import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.LocalInfo;
import com.grcp.weatherrhythm.locationsong.domain.LocalSong;
import com.grcp.weatherrhythm.locationsong.domain.LocalWeather;
import com.grcp.weatherrhythm.locationsong.domain.Song;
import java.util.Set;

public class LocalSongMapper {

    public static final LocalSongMapper INSTANCE = new LocalSongMapper();

    private LocalSongMapper() {
    }

    public LocalSong mapToLocationSong(String city,
                                       Category category,
                                       LocalWeather localWeather,
                                       Set<Song> songs) {
        return LocalSong.builder()
                .location(mapToLocationInfo(city, category, localWeather))
                .songs(songs)
                .build();
    }

    private LocalInfo mapToLocationInfo(String city, Category category, LocalWeather localWeather) {
        return LocalInfo.builder()
                .city(city)
                .category(category)
                .celsiusTemperature(localWeather.getCelsiusTemperature())
                .build();
    }
}
