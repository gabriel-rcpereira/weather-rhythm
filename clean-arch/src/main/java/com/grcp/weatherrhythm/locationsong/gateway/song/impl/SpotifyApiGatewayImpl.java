package com.grcp.weatherrhythm.locationsong.gateway.song.impl;

import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.Song;
import com.grcp.weatherrhythm.locationsong.domain.exception.GatewayException;
import com.grcp.weatherrhythm.locationsong.domain.exception.errors.GatewayError;
import com.grcp.weatherrhythm.locationsong.gateway.song.PlaylistSongGateway;
import com.grcp.weatherrhythm.locationsong.gateway.song.impl.domain.SpotifyCategoryId;
import com.grcp.weatherrhythm.locationsong.gateway.song.impl.mapper.PlaylistSongGatewayMapper;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.browse.GetListOfCategoriesRequest;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpotifyApiGatewayImpl implements PlaylistSongGateway {

    private final String clientId;
    private final String clientSecret;
    private final SpotifyApi spotifyApi;

    public SpotifyApiGatewayImpl(@Value("${spotify.credentials.clientId}") String clientId,
                                 @Value("${spotify.credentials.clientSecret}") String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(this.clientId)
                .setClientSecret(this.clientSecret)
                .build();
    }

    @SneakyThrows
    @Override
    public Set<Song> findSongsByCategory(Category category) {
        acquireToken();

        com.wrapper.spotify.model_objects.specification.Category firstCategoryFound = retrieveSpotifyCategory(category);
        PlaylistTrack[] playlistTracks = findPlaylistTracks(firstCategoryFound);

        return PlaylistSongGatewayMapper.INSTANCE.mapToSong(playlistTracks);
    }

    private PlaylistTrack[] findPlaylistTracks(com.wrapper.spotify.model_objects.specification.Category firstCategoryFound) throws IOException, SpotifyWebApiException, ParseException {
        Paging<PlaylistSimplified> simplifiedPaging = spotifyApi.getCategorysPlaylists(firstCategoryFound.getId())
                .build()
                .execute();

        PlaylistSimplified playlistSimplified = Stream.of(simplifiedPaging.getItems())
                .findFirst()
                .orElseThrow(() -> new GatewayException(GatewayError.PLAYLIST_SONG_ERROR_PLAYLIST_DOES_NOT_EXIST));

        return spotifyApi.getPlaylistsItems(playlistSimplified.getId())
                .build()
                .execute()
                .getItems();
    }

    private com.wrapper.spotify.model_objects.specification.Category retrieveSpotifyCategory(Category category) throws IOException, SpotifyWebApiException, ParseException {
        GetListOfCategoriesRequest categoriesRequest = spotifyApi.getListOfCategories().build();
        Paging<com.wrapper.spotify.model_objects.specification.Category> categoriesPaged = categoriesRequest.execute();
        return Stream.of(categoriesPaged.getItems())
                .filter(page -> page.getId().equalsIgnoreCase(SpotifyCategoryId.valueOfCategory(category).getId()))
                .findFirst()
                .orElseThrow(() -> new GatewayException(GatewayError.PLAYLIST_SONG_ERROR_CATEGORY_DOES_NOT_EXIST));
    }

    private void acquireToken() throws IOException, SpotifyWebApiException, ParseException {
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

        ClientCredentials clientCredentials = clientCredentialsRequest.execute();
        spotifyApi.setAccessToken(clientCredentials.getAccessToken());
    }
}
