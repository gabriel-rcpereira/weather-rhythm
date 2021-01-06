package com.grcp.weatherrhythm.locationsong.usecase;

import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.LocationSong;
import com.grcp.weatherrhythm.locationsong.domain.LocationWeather;
import com.grcp.weatherrhythm.locationsong.domain.Song;
import com.grcp.weatherrhythm.locationsong.gateway.song.PlaylistSongGateway;
import com.grcp.weatherrhythm.locationsong.gateway.weather.LocationWeatherGateway;
import com.grcp.weatherrhythm.locationsong.usecase.mapper.LocationSongMapper;
import java.util.Set;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FindLocationSongsByCity {

    private final LocationWeatherGateway locationWeatherGateway;
    private final PlaylistSongGateway playlistSongGateway;
    private final Validator validator;

    public FindLocationSongsByCity(LocationWeatherGateway locationWeatherGateway,
                                   PlaylistSongGateway playlistSongGateway,
                                   Validator validator) {
        this.locationWeatherGateway = locationWeatherGateway;
        this.playlistSongGateway = playlistSongGateway;
        this.validator = validator;
    }

    public LocationSong execute(String city) {
        log.info("Finding location songs by city [{}].", city);

        LocationWeather locationWeather = locationWeatherGateway.retrieveLocationWeatherByCityName(city);

        Category category = locationWeather.retrieveCategoryByTemperature();
        Set<Song> songsByCategory = playlistSongGateway.findSongsByCategory(category);

        LocationSong locationSong = LocationSongMapper.INSTANCE.mapToLocationSong(locationWeather, songsByCategory);
        locationSong.validate(validator);

        log.info("Finding location songs by city [{}] executed with success.", city);

        return locationSong;
    }
}
