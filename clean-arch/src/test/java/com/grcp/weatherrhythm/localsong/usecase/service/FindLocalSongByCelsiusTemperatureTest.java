package com.grcp.weatherrhythm.localsong.usecase.service;

import com.github.javafaker.Faker;
import com.grcp.weatherrhythm.localsong.domain.Category;
import com.grcp.weatherrhythm.localsong.domain.LocalInfo;
import com.grcp.weatherrhythm.localsong.domain.LocalSong;
import com.grcp.weatherrhythm.localsong.domain.LocalWeather;
import com.grcp.weatherrhythm.localsong.domain.Song;
import com.grcp.weatherrhythm.localsong.gateway.song.PlaylistSongGateway;
import com.grcp.weatherrhythm.localsong.gateway.weather.LocalWeatherGateway;
import com.grcp.weatherrhythm.localsong.usecase.FindLocalSongsByLatitudeAndLongitude;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class FindLocalSongByCelsiusTemperatureTest {

    private final Faker faker = Faker.instance();
    private final PlaylistSongGateway playlistSongGateway = Mockito.mock(PlaylistSongGateway.class);

    private FindLocalSongByCelsiusTemperature findLocalSongByCelsiusTemperature;

    @BeforeEach
    void setUp() {
        this.findLocalSongByCelsiusTemperature = new FindLocalSongByCelsiusTemperature(playlistSongGateway);
    }

    @ParameterizedTest
    @ValueSource(doubles = { 9.99, 0.0, -100.00 })
    void givenCelsiusTemperatureLessThanTenDegrees_whenCategoryMatchedIsClassical_thenReturnsClassicalMusicPlaylist(double temperature) {
        //given
        Set<Song> mockedSongsByCategory = Set.of(
                Song.builder()
                        .apiTrack(faker.internet().url())
                        .albumName(faker.pokemon().name())
                        .artistName(faker.artist().name())
                        .build(),
                Song.builder()
                        .apiTrack(faker.internet().url())
                        .albumName(faker.pokemon().name())
                        .artistName(faker.artist().name())
                        .build(),
                Song.builder()
                        .apiTrack(faker.internet().url())
                        .albumName(faker.pokemon().name())
                        .artistName(faker.artist().name())
                        .build()
        );

        //when
        when(playlistSongGateway.findSongsByCategory(Category.CLASSICAL)).thenReturn(mockedSongsByCategory);

        LocalSong localSong = this.findLocalSongByCelsiusTemperature.execute(temperature);

        //then
        Assertions.assertNotNull(localSong);

        LocalInfo localInfo = localSong.getLocation();
        Assertions.assertNotNull(localInfo);
        Assertions.assertEquals(temperature, localInfo.getCelsiusTemperature(), "expected celsius degrees");

        Assertions.assertNotNull(localSong.getSongs());
        Assertions.assertEquals(3, localSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, localSong.getSongs(), "expected songs");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 30.01, 50.00, 35.00 })
    void givenCelsiusTemperatureGreaterThanThirtyDegrees_whenCategoryMatchedParty_thenReturnPartyMusicPlaylist(double temperature) {
        //given
        Set<Song> mockedSongsByCategory = Set.of(
                Song.builder()
                        .apiTrack(faker.internet().url())
                        .albumName(faker.pokemon().name())
                        .artistName(faker.artist().name())
                        .build(),
                Song.builder()
                        .apiTrack(faker.internet().url())
                        .albumName(faker.pokemon().name())
                        .artistName(faker.artist().name())
                        .build(),
                Song.builder()
                        .apiTrack(faker.internet().url())
                        .albumName(faker.pokemon().name())
                        .artistName(faker.artist().name())
                        .build()
        );

        //when
        when(playlistSongGateway.findSongsByCategory(Category.PARTY)).thenReturn(mockedSongsByCategory);

        LocalSong localSong = this.findLocalSongByCelsiusTemperature.execute(temperature);

        //then
        Assertions.assertNotNull(localSong);

        LocalInfo localInfo = localSong.getLocation();
        Assertions.assertNotNull(localInfo);
        Assertions.assertEquals(temperature, localInfo.getCelsiusTemperature(), "expected celsius degrees");

        Assertions.assertNotNull(localSong.getSongs());
        Assertions.assertEquals(3, localSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, localSong.getSongs(), "expected songs");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 15.00, 20.00, 29.99 })
    void givenCelsiusTemperatureBetweenFifteenAndThirtyDegrees_whenMatchedPop_thenReturnPopPlaylist(double temperature) {
        //given
        Set<Song> mockedSongsByCategory = Set.of(
                Song.builder()
                        .apiTrack(faker.internet().url())
                        .albumName(faker.pokemon().name())
                        .artistName(faker.artist().name())
                        .build(),
                Song.builder()
                        .apiTrack(faker.internet().url())
                        .albumName(faker.pokemon().name())
                        .artistName(faker.artist().name())
                        .build(),
                Song.builder()
                        .apiTrack(faker.internet().url())
                        .albumName(faker.pokemon().name())
                        .artistName(faker.artist().name())
                        .build()
        );

        //when
        when(playlistSongGateway.findSongsByCategory(Category.POP)).thenReturn(mockedSongsByCategory);

        LocalSong localSong = this.findLocalSongByCelsiusTemperature.execute(temperature);

        //then
        Assertions.assertNotNull(localSong);

        LocalInfo localInfo = localSong.getLocation();
        Assertions.assertNotNull(localInfo);
        Assertions.assertEquals(temperature, localInfo.getCelsiusTemperature(), "expected celsius degrees");

        Assertions.assertNotNull(localSong.getSongs());
        Assertions.assertEquals(3, localSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, localSong.getSongs(), "expected songs");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 10.00, 11.05, 14.00 })
    void givenCelsiusTemperatureBetweenTenAndFourteenDegrees_whenCategoryMatchedRock_thenReturnRockPlaylist(double temperature) {
        //given
        Set<Song> mockedSongsByCategory = Set.of(
                Song.builder()
                        .apiTrack(faker.internet().url())
                        .albumName(faker.pokemon().name())
                        .artistName(faker.artist().name())
                        .build(),
                Song.builder()
                        .apiTrack(faker.internet().url())
                        .albumName(faker.pokemon().name())
                        .artistName(faker.artist().name())
                        .build(),
                Song.builder()
                        .apiTrack(faker.internet().url())
                        .albumName(faker.pokemon().name())
                        .artistName(faker.artist().name())
                        .build()
        );

        //when
        when(playlistSongGateway.findSongsByCategory(Category.ROCK)).thenReturn(mockedSongsByCategory);

        LocalSong localSong = this.findLocalSongByCelsiusTemperature.execute(temperature);

        //then
        Assertions.assertNotNull(localSong);

        LocalInfo localInfo = localSong.getLocation();
        Assertions.assertNotNull(localInfo);
        Assertions.assertEquals(temperature, localInfo.getCelsiusTemperature(), "expected celsius degrees");

        Assertions.assertNotNull(localSong.getSongs());
        Assertions.assertEquals(3, localSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, localSong.getSongs(), "expected songs");
    }
}