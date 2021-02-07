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
public class FindLocalSongsByLatitudeAndLongitude {

    private final LocalWeatherGateway localWeatherGateway;
    private final PlaylistSongGateway playlistSongGateway;

    public FindLocalSongsByLatitudeAndLongitude(LocalWeatherGateway localWeatherGateway,
                                                PlaylistSongGateway playlistSongGateway) {
        this.localWeatherGateway = localWeatherGateway;
        this.playlistSongGateway = playlistSongGateway;
    }

    public LocalSong execute(double latitude, double longitude) {
        log.info("Executing finding local songs by latitude [{}] and longitude [{}].", latitude, longitude);

        LocalWeather localWeather = this.localWeatherGateway.retrieveLocalWeatherByLatitudeAndLongitude(latitude, longitude);
        Category category = localWeather.retrieveCategoryByTemperature();
        Set<Song> songsByCategory = this.playlistSongGateway.findSongsByCategory(category);
        LocalSong localSong = LocalSongMapper.INSTANCE.mapToLocationSong(localWeather, songsByCategory);

        log.info("Executing finding local songs by latitude [{}] and longitude [{}] with success.", latitude, longitude);
        return localSong;
    }
}
