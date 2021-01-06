package com.grcp.weatherrhythm.locationsong.usecase.mapper;

import com.grcp.weatherrhythm.locationsong.domain.LocationSong;
import com.grcp.weatherrhythm.locationsong.domain.LocationWeather;
import com.grcp.weatherrhythm.locationsong.domain.Song;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class LocationSongMapper {

    public static final LocationSongMapper INSTANCE = Mappers.getMapper(LocationSongMapper.class);

    public LocationSong mapToLocationSong(LocationWeather locationWeather, Set<Song> songs) {
        return LocationSong.builder()
                .locationWeather(locationWeather)
                .songs(songs)
                .build();
    }
}
