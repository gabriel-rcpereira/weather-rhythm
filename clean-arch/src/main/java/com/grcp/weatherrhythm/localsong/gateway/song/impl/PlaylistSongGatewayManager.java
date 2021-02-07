package com.grcp.weatherrhythm.localsong.gateway.song.impl;

import com.grcp.weatherrhythm.localsong.domain.Category;
import com.grcp.weatherrhythm.localsong.domain.Song;
import com.grcp.weatherrhythm.localsong.domain.exception.GatewayException;
import com.grcp.weatherrhythm.localsong.domain.exception.errors.GatewayError;
import com.grcp.weatherrhythm.localsong.gateway.song.PlaylistSongGateway;
import com.grcp.weatherrhythm.localsong.gateway.song.client.SongClient;
import com.grcp.weatherrhythm.localsong.gateway.song.client.model.SongDetailClientModel;
import com.grcp.weatherrhythm.localsong.gateway.song.client.model.SongWrapperClientModel;
import com.grcp.weatherrhythm.localsong.gateway.song.impl.spotify.SpotifyApiGatewayImpl;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PlaylistSongGatewayManager implements PlaylistSongGateway {

    private final SongClient songClient;

    public PlaylistSongGatewayManager(SpotifyApiGatewayImpl songClient) {
        this.songClient = songClient;
    }

    @Override
    public Set<Song> findSongsByCategory(Category category) {
        log.info("Finding songs by category [{}].", category);
        SongWrapperClientModel songsByCategory = songClient.findSongsByCategory(category);

        if (songsByCategory.hasError()) {
            log.info("Finding songs by category [{}] executed with error.", category);
            throw new GatewayException(GatewayError.PLAYLIST_SONG_ERROR_REQUEST_SONG_DETAIL);
        }

        Set<Song> songs = songsByCategory.getSongs().stream()
                .map(this::createSong)
                .collect(Collectors.toSet());
        log.info("Finding songs by category [{}] executed with success.", category);
        return songs;
    }

    private Song createSong(SongDetailClientModel response) {
        return Song.builder()
                .albumName(response.getAlbumName())
                .apiTrack(response.getApiTrack())
                .artistName(response.getArtistName())
                .build();
    }
}
