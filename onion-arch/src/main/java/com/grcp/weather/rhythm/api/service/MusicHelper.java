package com.grcp.weather.rhythm.api.service;

import static com.grcp.weather.rhythm.api.exception.WeatherMusicErrorReason.GET_CATEGORY_FAILED;
import static com.grcp.weather.rhythm.api.exception.WeatherMusicErrorReason.ACQUIRE_MUSIC_API_CREDENTIALS_FAILED;
import static com.grcp.weather.rhythm.api.exception.WeatherMusicErrorReason.GET_PLAYLIST_TRACKS;
import static com.grcp.weather.rhythm.api.exception.WeatherMusicErrorReason.GET_PLAYLISTS_FAILED;

import com.grcp.weather.rhythm.api.exception.WeatherMusicException;
import com.grcp.weather.rhythm.api.model.MusicResponse;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Category;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.browse.GetListOfCategoriesRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MusicHelper {

    private static final String PARTY_CATEGORY_ID = "party";
    private static final String POP_CATEGORY_ID = "pop";
    private static final String ROCK_CATEGORY_ID = "rock";
    private static final String CLASSICAL_PLAYLIST_ID = "45o9GyQis9HRPTYUXuS1hQ";

    private final String clientId;
    private final String clientSecret;

    public List<MusicResponse> retrieveMusicsByPartyCategory() throws WeatherMusicException {
        PlaylistTrack[] tracks = getPlaylistTracksByCategoryId(PARTY_CATEGORY_ID);
        return buildMusicsResponse(tracks);
    }

    public List<MusicResponse> retrieveMusicsByPopCategory() throws WeatherMusicException {
        PlaylistTrack[] tracks = getPlaylistTracksByCategoryId(POP_CATEGORY_ID);
        return buildMusicsResponse(tracks);
    }

    public List<MusicResponse> retrieveMusicsByRockCategory() throws WeatherMusicException {
        PlaylistTrack[] tracks = getPlaylistTracksByCategoryId(ROCK_CATEGORY_ID);
        return buildMusicsResponse(tracks);
    }

    public List<MusicResponse> retrieveMusicsByClassicalCategory() throws WeatherMusicException {
        PlaylistTrack[] tracks = getPlaylistTracksByPlaylistId(CLASSICAL_PLAYLIST_ID);
        return buildMusicsResponse(tracks);
    }

    private PlaylistTrack[] getPlaylistTracksByPlaylistId(String playlistId) throws WeatherMusicException {
        SpotifyApi spotifyApi = acquireSpotifyApiWithCredential();
        return getPlaylistTracks(spotifyApi, playlistId);
    }

    private PlaylistTrack[] getPlaylistTracksByCategoryId(String categoryId) throws WeatherMusicException {
        SpotifyApi spotifyApi = acquireSpotifyApiWithCredential();
        Optional<Category> partyCategoryOpt = getCategoryById(spotifyApi, categoryId);
        Paging<PlaylistSimplified> partyPlaylists = getPlaylistByCategory(spotifyApi, partyCategoryOpt.get());
        Optional<PlaylistSimplified> firstPlaylistOpt = Stream.of(partyPlaylists.getItems()).findFirst();
        return getPlaylistTracks(spotifyApi, firstPlaylistOpt.get().getId());
    }

    private PlaylistTrack[] getPlaylistTracks(SpotifyApi spotifyApi, String playlistId) throws WeatherMusicException {
        try {
            return spotifyApi.getPlaylistsTracks(playlistId)
                    .build()
                    .execute()
                    .getItems();
        } catch (IOException | SpotifyWebApiException e) {
            log.error(GET_PLAYLIST_TRACKS.getDescription());
            throw new WeatherMusicException(GET_PLAYLIST_TRACKS, e);
        }
    }

    private Paging<PlaylistSimplified> getPlaylistByCategory(SpotifyApi spotifyApi, Category partyCategory) throws WeatherMusicException {
        try {
            return spotifyApi.getCategorysPlaylists(partyCategory.getId()).build().execute();
        } catch (IOException | SpotifyWebApiException e) {
            log.error(GET_PLAYLISTS_FAILED.getDescription());
            throw new WeatherMusicException(GET_PLAYLISTS_FAILED, e);
        }
    }

    private Optional<Category> getCategoryById(SpotifyApi spotifyApi, String categoryId) throws WeatherMusicException {
        try {
            GetListOfCategoriesRequest categoriesRequest = spotifyApi.getListOfCategories().build();
            Paging<Category> categoriesPaged = categoriesRequest.execute();
            return Arrays.stream(categoriesPaged.getItems())
                    .filter(category -> categoryId.equals(category.getId()))
                    .findFirst();
        } catch (IOException | SpotifyWebApiException e) {
            log.error(GET_CATEGORY_FAILED.getDescription());
            throw new WeatherMusicException(GET_CATEGORY_FAILED, e);
        }
    }

    private List<MusicResponse> buildMusicsResponse(PlaylistTrack[] playlistTracks) {
        return Arrays.stream(playlistTracks)
                .filter(isValidTrackPredicate())
                .map(playlistTrack -> MusicResponse.builder()
                        .albumName(playlistTrack.getTrack().getAlbum().getName())
                        .artistName(playlistTrack.getTrack().getName())
                        .apiTrack(playlistTrack.getTrack().getHref())
                        .build())
                .collect(Collectors.toList());
    }

    private SpotifyApi acquireSpotifyApiWithCredential() throws WeatherMusicException {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

        ClientCredentials clientCredentials = acquireClientCredentials(spotifyApi);
        spotifyApi.setAccessToken(clientCredentials.getAccessToken());

        return spotifyApi;
    }

    private ClientCredentials acquireClientCredentials(SpotifyApi spotifyApi) throws WeatherMusicException {
        try {
            ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                    .build();
            return clientCredentialsRequest
                    .execute();
        } catch (IOException | SpotifyWebApiException e) {
            log.error(ACQUIRE_MUSIC_API_CREDENTIALS_FAILED.getDescription(), e);
            throw new WeatherMusicException(ACQUIRE_MUSIC_API_CREDENTIALS_FAILED, e);
        }
    }

    private Predicate<PlaylistTrack> isValidTrackPredicate() {
        return playlistTrack -> playlistTrack.getTrack() != null &&
                playlistTrack.getTrack().getHref() != null &&
                playlistTrack.getTrack().getAlbum() != null &&
                playlistTrack.getTrack().getAlbum().getName() != null;
    }
}
