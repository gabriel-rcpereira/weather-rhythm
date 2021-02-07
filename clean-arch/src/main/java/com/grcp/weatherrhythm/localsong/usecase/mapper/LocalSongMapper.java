package com.grcp.weatherrhythm.localsong.usecase.mapper;

import com.grcp.weatherrhythm.localsong.domain.Category;
import com.grcp.weatherrhythm.localsong.domain.LocalInfo;
import com.grcp.weatherrhythm.localsong.domain.LocalSong;
import com.grcp.weatherrhythm.localsong.domain.LocalWeather;
import com.grcp.weatherrhythm.localsong.domain.Song;
import java.util.Set;

public class LocalSongMapper {

    public static final LocalSongMapper INSTANCE = new LocalSongMapper();

    private LocalSongMapper() {
    }

    public LocalSong mapToLocationSong(LocalWeather localWeather,
                                       Set<Song> songs) {
        return LocalSong.builder()
                .location(mapToLocationInfo(localWeather))
                .songs(songs)
                .build();
    }

    private LocalInfo mapToLocationInfo(LocalWeather localWeather) {
        return LocalInfo.builder()
                .celsiusTemperature(localWeather.getCelsiusTemperature())
                .build();
    }
}
