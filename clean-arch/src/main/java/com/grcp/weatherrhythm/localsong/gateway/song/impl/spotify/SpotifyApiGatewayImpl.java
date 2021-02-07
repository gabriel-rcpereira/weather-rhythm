package com.grcp.weatherrhythm.localsong.gateway.song.impl.spotify;

import com.grcp.weatherrhythm.localsong.domain.Category;
import com.grcp.weatherrhythm.localsong.gateway.song.client.SongClient;
import com.grcp.weatherrhythm.localsong.gateway.song.client.model.ErrorDetailClientModel;
import com.grcp.weatherrhythm.localsong.gateway.song.client.model.SongWrapperClientModel;
import com.grcp.weatherrhythm.localsong.gateway.song.impl.spotify.model.SpotifyCategoryId;
import com.grcp.weatherrhythm.localsong.gateway.song.impl.spotify.mapper.PlaylistSongGatewayMapper;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.browse.GetListOfCategoriesRequest;
import java.io.IOException;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpotifyApiGatewayImpl implements SongClient {

    private final String clientId;
    private final String clientSecret;
    private final SpotifyApi spotifyApi;

    public SpotifyApiGatewayImpl(@Value("${spotify.credentials.clientId}")
                                         String clientId,
                                 @Value("${spotify.credentials.clientSecret}")
                                         String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(this.clientId)
                .setClientSecret(this.clientSecret)
                .build();
    }

    @Override
    public SongWrapperClientModel findSongsByCategory(Category category) {
        try {
            this.acquireToken();

            com.wrapper.spotify.model_objects.specification.Category firstCategoryFound = this.retrieveSpotifyCategory(category);
            PlaylistTrack[] playlistTracks = this.findPlaylistTracks(firstCategoryFound);

            return PlaylistSongGatewayMapper.INSTANCE.mapToSong(playlistTracks);
        } catch (IOException | SpotifyWebApiException | ParseException | RuntimeException e) {
            log.error("Find songs by category error [{}].", e);
            return SongWrapperClientModel.builder()
                    .errorDetailClientModel(ErrorDetailClientModel.PLAYLIST_SONG_ERROR_REQUEST_SONG_DETAIL)
                    .build();
        }
    }

    private PlaylistTrack[] findPlaylistTracks(com.wrapper.spotify.model_objects.specification.Category firstCategoryFound) throws IOException, SpotifyWebApiException, ParseException {
        Paging<PlaylistSimplified> simplifiedPaging = this.spotifyApi.getCategorysPlaylists(firstCategoryFound.getId())
                .build()
                .execute();

        PlaylistSimplified playlistSimplified = Stream.of(simplifiedPaging.getItems())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Playlist not found."));

        return spotifyApi.getPlaylistsItems(playlistSimplified.getId())
                .build()
                .execute()
                .getItems();
    }

    private com.wrapper.spotify.model_objects.specification.Category retrieveSpotifyCategory(Category category) throws IOException, SpotifyWebApiException, ParseException {
        GetListOfCategoriesRequest categoriesRequest = spotifyApi.getListOfCategories().build();
        Paging<com.wrapper.spotify.model_objects.specification.Category> categoryPaging = categoriesRequest.execute();
        return Stream.of(categoryPaging.getItems())
                .filter(page -> page.getId().equalsIgnoreCase(SpotifyCategoryId.valueOfCategory(category).getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    private void acquireToken() throws IOException, SpotifyWebApiException, ParseException {
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

        ClientCredentials clientCredentials = clientCredentialsRequest.execute();
        spotifyApi.setAccessToken(clientCredentials.getAccessToken());
    }
}
