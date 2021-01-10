package com.grcp.weatherrhythm.locationsong.usecase;

import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.LocalSong;
import com.grcp.weatherrhythm.locationsong.domain.LocalWeather;
import com.grcp.weatherrhythm.locationsong.domain.Song;
import com.grcp.weatherrhythm.locationsong.gateway.song.PlaylistSongGateway;
import com.grcp.weatherrhythm.locationsong.gateway.weather.LocalWeatherGateway;
import com.grcp.weatherrhythm.locationsong.usecase.mapper.LocationSongMapper;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FindLocalSongsByCity {

    private final LocalWeatherGateway localWeatherGateway;
    private final PlaylistSongGateway playlistSongGateway;

    public FindLocalSongsByCity(LocalWeatherGateway localWeatherGateway,
                                PlaylistSongGateway playlistSongGateway) {
        this.localWeatherGateway = localWeatherGateway;
        this.playlistSongGateway = playlistSongGateway;
    }

    public LocalSong execute(String city) {
        log.info("Finding location songs by city [{}].", city);

        LocalWeather localWeather = localWeatherGateway.retrieveLocalWeatherByCityName(city);
        Category category = localWeather.retrieveCategoryByTemperature();
        Set<Song> songsByCategory = playlistSongGateway.findSongsByCategory(category);
        LocalSong localSong = LocationSongMapper.INSTANCE.mapToLocationSong(city, category, localWeather, songsByCategory);

        log.info("Finding location songs by city [{}] executed with success.", city);

        return localSong;
    }
}
