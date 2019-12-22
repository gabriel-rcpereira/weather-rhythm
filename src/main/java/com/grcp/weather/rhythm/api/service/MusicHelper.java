package com.grcp.weather.rhythm.api.service;

import com.grcp.weather.rhythm.api.model.MusicResponse;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Category;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MusicHelper {

    private static final String PARTY = "party";
    private static final String POP = "pop";

    private final String clientId;
    private final String clientSecret;

    public List<MusicResponse> retrieveMusicsByPartyCategory() throws IOException, SpotifyWebApiException {
        PlaylistTrack[] tracks = getPlaylistTracksByCategoryId(PARTY);
        return buildMusicsResponse(tracks);
    }

    public List<MusicResponse> retrieveMusicsByPopCategory() throws IOException, SpotifyWebApiException {
        PlaylistTrack[] tracks = getPlaylistTracksByCategoryId(POP);
        return buildMusicsResponse(tracks);
    }

    private PlaylistTrack[] getPlaylistTracksByCategoryId(String categoryId) throws IOException, SpotifyWebApiException {
        SpotifyApi spotifyApi = buildSpotifyApi();
        Optional<Category> partyCategoryOpt = getCategoryById(spotifyApi, categoryId);
        Paging<PlaylistSimplified> partyPlaylists = getPlaylistByCategory(spotifyApi, partyCategoryOpt.get());
        Optional<PlaylistSimplified> firstPlaylistOpt = Stream.of(partyPlaylists.getItems()).findFirst();
        return getPlaylistsTrack(spotifyApi, firstPlaylistOpt.get());
    }

    private PlaylistTrack[] getPlaylistsTrack(SpotifyApi spotifyApi, PlaylistSimplified playlist) throws IOException, SpotifyWebApiException {
        return spotifyApi.getPlaylistsTracks(playlist.getId())
                .build()
                .execute()
                .getItems();
    }

    private Paging<PlaylistSimplified> getPlaylistByCategory(SpotifyApi spotifyApi, Category partyCategory) throws IOException, SpotifyWebApiException {
        return spotifyApi.getCategorysPlaylists(partyCategory.getId()).build().execute();
    }

    private Optional<Category> getCategoryById(SpotifyApi spotifyApi, String id) throws IOException, SpotifyWebApiException {
        return Arrays.stream(spotifyApi.getListOfCategories().build().execute().getItems())
                    .filter(category -> id.equals(category.getId()))
                    .findFirst();
    }

    private List<MusicResponse> buildMusicsResponse(PlaylistTrack[] playlistTracks) {
        return Arrays.stream(playlistTracks)
                .map(playlistTrack -> MusicResponse.builder()
                        .rhythm("party")
                        .albumName(playlistTrack.getTrack().getAlbum().getName())
                        .artistName(playlistTrack.getTrack().getName())
                        .track(playlistTrack.getTrack().getUri())
                        .build())
                .collect(Collectors.toList());
    }

    private SpotifyApi buildSpotifyApi() throws IOException, SpotifyWebApiException {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

        ClientCredentials clientCredentials = clientCredentialsRequest.execute();
        spotifyApi.setAccessToken(clientCredentials.getAccessToken());

        return spotifyApi;
    }
}
