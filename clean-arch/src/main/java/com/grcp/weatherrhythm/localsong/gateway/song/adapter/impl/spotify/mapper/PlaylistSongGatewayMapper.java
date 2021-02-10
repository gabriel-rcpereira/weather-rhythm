package com.grcp.weatherrhythm.localsong.gateway.song.adapter.impl.spotify.mapper;

import com.grcp.weatherrhythm.localsong.gateway.song.adapter.model.SongDetailClientModel;
import com.grcp.weatherrhythm.localsong.gateway.song.adapter.model.SongWrapperClientModel;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.model_objects.specification.Track;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlaylistSongGatewayMapper {

    public static final PlaylistSongGatewayMapper INSTANCE = new PlaylistSongGatewayMapper();

    private PlaylistSongGatewayMapper() {
    }

    public SongWrapperClientModel mapToSong(PlaylistTrack[] playlistTracks) {
        Set<SongDetailClientModel> songs = Stream.of(playlistTracks)
                .map(playlistTrack -> (Track) playlistTrack.getTrack())
                .map(track -> mapToSong(track))
                .collect(Collectors.toSet());

        return SongWrapperClientModel.builder()
                .songs(songs)
                .build();
    }

    private SongDetailClientModel mapToSong(Track track) {
        return SongDetailClientModel.builder()
                .artistName(formatArtistNames(track))
                .apiTrack(track.getHref())
                .albumName(track.getAlbum().getName())
                .build();
    }

    private String formatArtistNames(Track track) {
        return Arrays.stream(track.getAlbum().getArtists())
                .map(artistSimplified -> artistSimplified.getName())
                .collect(Collectors.joining(","));
    }
}
