package com.grcp.weatherrhythm.localsong.usecase.service;

import com.grcp.weatherrhythm.localsong.domain.Category;
import com.grcp.weatherrhythm.localsong.domain.LocalSong;
import com.grcp.weatherrhythm.localsong.domain.LocalWeather;
import com.grcp.weatherrhythm.localsong.domain.Song;
import com.grcp.weatherrhythm.localsong.domain.exception.DomainException;
import com.grcp.weatherrhythm.localsong.domain.exception.errors.DomainError;
import com.grcp.weatherrhythm.localsong.gateway.song.PlaylistSongGateway;
import com.grcp.weatherrhythm.localsong.usecase.mapper.LocalSongMapper;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FindLocalSongByCelsiusTemperature {

    private final PlaylistSongGateway playlistSongGateway;

    public FindLocalSongByCelsiusTemperature(PlaylistSongGateway playlistSongGateway) {
        this.playlistSongGateway = playlistSongGateway;
    }

    public LocalSong execute(double celsiusTemperature) {
        log.info("Executing finding local songs by celsiusTemperature [{}].", celsiusTemperature);

        Category category = retrieveCategory(celsiusTemperature);
        Set<Song> songsByCategory = playlistSongGateway.findSongsByCategory(category);
        LocalSong localSong = LocalSongMapper.INSTANCE.mapToLocationSong(celsiusTemperature, songsByCategory);

        log.info("Executing finding local songs by celsiusTemperature [{}] with success.", celsiusTemperature);
        return localSong;
    }

    private Category retrieveCategory(double celsiusTemperature) {
        return Category.retrieveCategory(celsiusTemperature)
                .orElseThrow(() -> new DomainException(DomainError.ANY_CATEGORY_FOUND));
    }
}
