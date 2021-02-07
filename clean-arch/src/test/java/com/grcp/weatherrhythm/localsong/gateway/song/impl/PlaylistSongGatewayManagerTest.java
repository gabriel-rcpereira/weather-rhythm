package com.grcp.weatherrhythm.localsong.gateway.song.impl;

import com.github.javafaker.Faker;
import com.grcp.weatherrhythm.localsong.domain.Category;
import com.grcp.weatherrhythm.localsong.domain.Song;
import com.grcp.weatherrhythm.localsong.domain.exception.GatewayException;
import com.grcp.weatherrhythm.localsong.domain.exception.errors.GatewayError;
import com.grcp.weatherrhythm.localsong.gateway.song.client.model.ErrorDetailClientModel;
import com.grcp.weatherrhythm.localsong.gateway.song.client.model.SongDetailClientModel;
import com.grcp.weatherrhythm.localsong.gateway.song.client.model.SongWrapperClientModel;
import com.grcp.weatherrhythm.localsong.gateway.song.impl.spotify.SpotifyApiGatewayImpl;
import java.util.Set;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PlaylistSongGatewayManagerTest {

    private PlaylistSongGatewayManager playlistSongGatewayManager;

    @Mock
    private SpotifyApiGatewayImpl songClient;

    @BeforeEach
    void setUp() {
        playlistSongGatewayManager = new PlaylistSongGatewayManager(songClient);
    }

    @Test
    public void givenCategory_whenFindSongsByCategory_thenReturnsTwoSongs() {
        //given
        Faker faker = Faker.instance();
        Category category = Category.ROCK;

        SongDetailClientModel songDetailModel_one = SongDetailClientModel.builder()
                .albumName(faker.pokemon().name())
                .artistName(faker.artist().name())
                .apiTrack(faker.internet().url())
                .build();
        SongDetailClientModel songDetailModel_two = SongDetailClientModel.builder()
                .albumName(faker.pokemon().name())
                .artistName(faker.artist().name())
                .apiTrack(faker.internet().url())
                .build();
        Set<SongDetailClientModel> songsModel = Set.of(songDetailModel_one, songDetailModel_two);
        SongWrapperClientModel songsWrapperModel = SongWrapperClientModel.builder()
                .songs(songsModel)
                .build();

        //when
        when(songClient.findSongsByCategory(category)).thenReturn(songsWrapperModel);
        Set<Song> songs = playlistSongGatewayManager.findSongsByCategory(category);

        //then
        Assertions.assertEquals(2, songs.size());

        Assertions.assertTrue(songs.stream().filter(isValidSong(songDetailModel_one)).findFirst().isPresent(), "expected song one");
        Assertions.assertTrue(songs.stream().filter(isValidSong(songDetailModel_two)).findFirst().isPresent(), "expected song two");
    }

    @Test
    public void givenCategory_whenFindSongsByCategory_thenReturnsEmptyList() {
        //given
        Category category = Category.CLASSICAL;

        SongWrapperClientModel songsWrapperModel = SongWrapperClientModel.builder().build();

        //when
        when(songClient.findSongsByCategory(category)).thenReturn(songsWrapperModel);
        Set<Song> songs = playlistSongGatewayManager.findSongsByCategory(category);

        //then
        Assertions.assertEquals(0, songs.size());
    }

    @Test
    public void givenCategory_whenFindSongsByCategory_thenThrowsGatewayException() {
        //given
        Category category = Category.CLASSICAL;

        SongWrapperClientModel songsWrapperModel = SongWrapperClientModel.builder()
                .errorDetailClientModel(ErrorDetailClientModel.PLAYLIST_SONG_ERROR_REQUEST_SONG_DETAIL)
                .build();

        //when
        when(songClient.findSongsByCategory(category)).thenReturn(songsWrapperModel);
        Executable executableMethod = () -> playlistSongGatewayManager.findSongsByCategory(category);

        //then
        GatewayException gatewayException = assertThrows(GatewayException.class, executableMethod);
        Assertions.assertEquals(GatewayError.PLAYLIST_SONG_ERROR_REQUEST_SONG_DETAIL, gatewayException.getError());
    }

    private Predicate<Song> isValidSong(SongDetailClientModel songDetailModel) {
        return song -> song.getAlbumName().equalsIgnoreCase(songDetailModel.getAlbumName()) &&
                song.getApiTrack().equalsIgnoreCase(songDetailModel.getApiTrack()) &&
                song.getArtistName().equalsIgnoreCase(songDetailModel.getArtistName());
    }
}