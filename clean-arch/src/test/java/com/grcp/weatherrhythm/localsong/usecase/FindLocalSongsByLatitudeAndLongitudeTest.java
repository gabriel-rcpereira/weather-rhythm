package com.grcp.weatherrhythm.localsong.usecase;

import com.github.javafaker.Faker;
import com.grcp.weatherrhythm.localsong.domain.Category;
import com.grcp.weatherrhythm.localsong.domain.LocalInfo;
import com.grcp.weatherrhythm.localsong.domain.LocalSong;
import com.grcp.weatherrhythm.localsong.domain.LocalWeather;
import com.grcp.weatherrhythm.localsong.domain.Song;
import com.grcp.weatherrhythm.localsong.gateway.song.PlaylistSongGateway;
import com.grcp.weatherrhythm.localsong.gateway.weather.LocalWeatherGateway;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

class FindLocalSongsByLatitudeAndLongitudeTest {

    private FindLocalSongsByLatitudeAndLongitude findLocalSongsByLatitudeAndLongitude;
    private Faker faker = Faker.instance();

    private LocalWeatherGateway localWeatherGateway = Mockito.mock(LocalWeatherGateway.class);
    private PlaylistSongGateway playlistSongGateway = Mockito.mock(PlaylistSongGateway.class);

    @BeforeEach
    void setUp() {
        this.findLocalSongsByLatitudeAndLongitude = new FindLocalSongsByLatitudeAndLongitude(localWeatherGateway, playlistSongGateway);
        this.faker = Faker.instance();
    }

    @ParameterizedTest
    @ValueSource(doubles = { 9.99, 0.0, -100.00 })
    void givenValidLatitudeAndLongitude_whenTemperatureIsBelowTenDegrees_thenReturnClassicalMusicPlaylist(double temperature) {
        //given
        Double latitude = faker.number().randomDouble(5, -180, 180);
        Double longitude = faker.number().randomDouble(5, -45, 45);

        Double celsiusTemperature = temperature;
        LocalWeather mockedLocalWeather = LocalWeather.builder()
                .celsiusTemperature(celsiusTemperature)
                .build();

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
        when(localWeatherGateway.retrieveLocalWeatherByLatitudeAndLongitude(latitude, longitude)).thenReturn(mockedLocalWeather);
        when(playlistSongGateway.findSongsByCategory(Category.CLASSICAL)).thenReturn(mockedSongsByCategory);

        LocalSong localSong = findLocalSongsByLatitudeAndLongitude.execute(latitude, longitude);

        //then
        Assertions.assertNotNull(localSong);

        LocalInfo localInfo = localSong.getLocation();
        Assertions.assertNotNull(localInfo);
        Assertions.assertEquals(celsiusTemperature, localInfo.getCelsiusTemperature(), "expected celsius degrees");

        Assertions.assertNotNull(localSong.getSongs());
        Assertions.assertEquals(3, localSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, localSong.getSongs(), "expected songs");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 30.01, 50.00, 35.00 })
    void givenValidLocal_whenTemperatureIsAboveThirtyDegrees_thenReturnPartyMusicPlaylist(double temperature) {
        //given
        Double latitude = faker.number().randomDouble(5, -180, 180);
        Double longitude = faker.number().randomDouble(5, -45, 45);

        Double celsiusTemperature = temperature;
        LocalWeather mockedLocalWeather = LocalWeather.builder()
                .celsiusTemperature(celsiusTemperature)
                .build();

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
        when(localWeatherGateway.retrieveLocalWeatherByLatitudeAndLongitude(latitude, longitude)).thenReturn(mockedLocalWeather);
        when(playlistSongGateway.findSongsByCategory(Category.PARTY)).thenReturn(mockedSongsByCategory);

        LocalSong localSong = findLocalSongsByLatitudeAndLongitude.execute(latitude, longitude);

        //then
        Assertions.assertNotNull(localSong);

        LocalInfo localInfo = localSong.getLocation();
        Assertions.assertNotNull(localInfo);
        Assertions.assertEquals(celsiusTemperature, localInfo.getCelsiusTemperature(), "expected celsius degrees");

        Assertions.assertNotNull(localSong.getSongs());
        Assertions.assertEquals(3, localSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, localSong.getSongs(), "expected songs");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 15.00, 20.00, 29.99 })
    void givenValidLocal_whenTemperatureIsBetweenFifteenAndThirty_thenReturnPopPlaylist(double temperature) {
        //given
        Double latitude = faker.number().randomDouble(5, -180, 180);
        Double longitude = faker.number().randomDouble(5, -45, 45);

        Double celsiusTemperature = temperature;
        LocalWeather mockedLocalWeather = LocalWeather.builder()
                .celsiusTemperature(celsiusTemperature)
                .build();

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
        when(localWeatherGateway.retrieveLocalWeatherByLatitudeAndLongitude(latitude, longitude)).thenReturn(mockedLocalWeather);
        when(playlistSongGateway.findSongsByCategory(Category.POP)).thenReturn(mockedSongsByCategory);

        LocalSong localSong = findLocalSongsByLatitudeAndLongitude.execute(latitude, longitude);

        //then
        Assertions.assertNotNull(localSong);

        LocalInfo localInfo = localSong.getLocation();
        Assertions.assertNotNull(localInfo);
        Assertions.assertEquals(celsiusTemperature, localInfo.getCelsiusTemperature(), "expected celsius degrees");

        Assertions.assertNotNull(localSong.getSongs());
        Assertions.assertEquals(3, localSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, localSong.getSongs(), "expected songs");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 10.00, 11.05, 14.00 })
    void givenValidLocal_whenTemperatureIsBetweenTenAndFourteenDegrees_thenReturnRockPlaylist(double temperature) {
        //given
        Double latitude = faker.number().randomDouble(5, -180, 180);
        Double longitude = faker.number().randomDouble(5, -45, 45);

        Double celsiusTemperature = temperature;
        LocalWeather mockedLocalWeather = LocalWeather.builder()
                .celsiusTemperature(celsiusTemperature)
                .build();

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
        when(localWeatherGateway.retrieveLocalWeatherByLatitudeAndLongitude(latitude, longitude)).thenReturn(mockedLocalWeather);
        when(playlistSongGateway.findSongsByCategory(Category.ROCK)).thenReturn(mockedSongsByCategory);

        LocalSong localSong = findLocalSongsByLatitudeAndLongitude.execute(latitude, longitude);

        //then
        Assertions.assertNotNull(localSong);

        LocalInfo localInfo = localSong.getLocation();
        Assertions.assertNotNull(localInfo);
        Assertions.assertEquals(celsiusTemperature, localInfo.getCelsiusTemperature(), "expected celsius degrees");

        Assertions.assertNotNull(localSong.getSongs());
        Assertions.assertEquals(3, localSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, localSong.getSongs(), "expected songs");
    }

}