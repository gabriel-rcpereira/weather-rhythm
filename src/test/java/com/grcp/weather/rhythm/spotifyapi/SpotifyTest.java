package com.grcp.weather.rhythm.spotifyapi;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Category;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class SpotifyTest {

    private static final String CLIENT_ID = "cb899a53393f43e8ab7451e405316849";
    private static final String CLIENT_SECRET = "7bbec175a89449d48f6f5a5c50fac488";

    @Test
    public void acquireToken_whenClientIdAndClientSecretAreRight() throws IOException, SpotifyWebApiException {
        // given
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .build();
        final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

        // when
        final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

        // then
        Assert.hasLength(clientCredentials.getAccessToken(), "Expected the access token");
    }

    @Test
    public void getAllCategories() throws IOException, SpotifyWebApiException {
        // given
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .build();
        final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

        // when
        final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
        spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        final Paging<Category> categories = spotifyApi.getListOfCategories().build()
                .execute();

        // then
        Assert.noNullElements(categories.getItems(), "Expected the list of all categories.");
    }

    @Test
    public void getCategories_whenPartyAndPopAndRockAndClassicalAreFiltered() throws IOException, SpotifyWebApiException {
        // given
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .build();
        final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

        // when
        final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
        spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        final Paging<Category> categories = spotifyApi.getListOfCategories().build()
                .execute();
        List<Category> categoriesFiltered = List.of(categories.getItems()).stream()
                .filter(category -> "pop".equals(category.getId()) ||
                        "rock".equals(category.getId()) ||
                        "party".equals(category.getId()) ||
                        "classical".equals(category.getId()))
                .collect(Collectors.toList());

        // then
//        Assert.isTrue(categoriesFiltered.size() == 4, "Expected 4 categories filtered.");
    }

    @Test
    public void getPlaylistByCategory_whenSetPopCategory() throws IOException, SpotifyWebApiException {
        // given
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .build();
        final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

        // when
        final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
        spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        final Paging<Category> categories = spotifyApi.getListOfCategories().build()
                .execute();
        Optional<Category> categoryPop = List.of(categories.getItems()).stream()
                .filter(category -> "pop".equals(category.getId()))
                .findFirst();
        Paging<PlaylistSimplified> playlistPop = spotifyApi.getCategorysPlaylists(categoryPop.get().getId()).build().execute();

        // then
        Assert.isTrue(playlistPop.getItems().length > 0, "Expected the playlist pop.");
    }

    @Test
    public void getPlaylistMusics_whenSetPopCategoryAndGettingFirstPlaylist() throws IOException, SpotifyWebApiException {
        // given
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .build();
        final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

        // when
        final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
        spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        final Paging<Category> categories = spotifyApi.getListOfCategories().build()
                .execute();
        Optional<Category> categoryPop = List.of(categories.getItems()).stream()
                .filter(category -> "pop".equals(category.getId()))
                .findFirst();
        Paging<PlaylistSimplified> playlistPop = spotifyApi.getCategorysPlaylists(categoryPop.get().getId()).build().execute();
        Optional<PlaylistSimplified> firstPlaylist = Stream.of(playlistPop.getItems()).findFirst();
        Paging<PlaylistTrack> playlistTrackPaging = spotifyApi.getPlaylistsTracks(firstPlaylist.get().getId()).build().execute();

        // then
        Assert.isTrue(playlistTrackPaging.getItems().length > 0, "Expected musics related to playlist pop.");
    }
}
