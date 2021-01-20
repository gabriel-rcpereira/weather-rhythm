package com.grcp.weatherrhythm.locationsong.gateway.song.impl.mapper;

import com.grcp.weatherrhythm.locationsong.domain.Song;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
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

    public Set<Song> mapToSong(PlaylistTrack[] playlistTracks) {
        return Stream.of(playlistTracks)
                .map(playlistTrack -> (Track)playlistTrack.getTrack())
                .map(track -> mapToSong(track))
                .collect(Collectors.toSet());
    }

    private Song mapToSong(Track track) {
        return Song.builder()
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
