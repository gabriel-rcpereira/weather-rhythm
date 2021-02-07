package com.grcp.weatherrhythm.localsong.usecase;

import com.grcp.weatherrhythm.localsong.domain.Category;
import com.grcp.weatherrhythm.localsong.domain.LocalSong;
import com.grcp.weatherrhythm.localsong.domain.LocalWeather;
import com.grcp.weatherrhythm.localsong.domain.Song;
import com.grcp.weatherrhythm.localsong.gateway.song.PlaylistSongGateway;
import com.grcp.weatherrhythm.localsong.gateway.weather.LocalWeatherGateway;
import com.grcp.weatherrhythm.localsong.usecase.mapper.LocalSongMapper;
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
        log.info("Finding local songs by city [{}].", city);

        LocalWeather localWeather = localWeatherGateway.retrieveLocalWeatherByCityName(city);
        Category category = localWeather.retrieveCategoryByTemperature();
        Set<Song> songsByCategory = playlistSongGateway.findSongsByCategory(category);
        LocalSong localSong = LocalSongMapper.INSTANCE.mapToLocationSong(city, category, localWeather, songsByCategory);

        log.info("Finding local songs by city [{}] executed with success.", city);
        return localSong;
    }
}
