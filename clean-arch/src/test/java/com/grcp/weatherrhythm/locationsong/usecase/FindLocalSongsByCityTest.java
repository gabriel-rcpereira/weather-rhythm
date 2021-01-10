package com.grcp.weatherrhythm.locationsong.usecase;

import com.github.javafaker.Faker;
import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.LocalInfo;
import com.grcp.weatherrhythm.locationsong.domain.LocalSong;
import com.grcp.weatherrhythm.locationsong.domain.LocalWeather;
import com.grcp.weatherrhythm.locationsong.domain.Song;
import com.grcp.weatherrhythm.locationsong.gateway.song.PlaylistSongGateway;
import com.grcp.weatherrhythm.locationsong.gateway.weather.LocalWeatherGateway;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
class FindLocalSongsByCityTest {

    private FindLocalSongsByCity findLocalSongsByCity;
    @Mock
    private LocalWeatherGateway localWeatherGateway;
    @Mock
    private PlaylistSongGateway playlistSongGateway;

    @BeforeEach
    public void loadContext() {
        findLocalSongsByCity = new FindLocalSongsByCity(localWeatherGateway, playlistSongGateway);
    }

    @ParameterizedTest
    @ValueSource(doubles = { 9.99, 0.0, -100.00 })
    void givenLocationValid_whenTemperatureIsBelowTenDegrees_thenReturnClassicalMusicPlaylist(double temperature) {
        //given
        Faker faker = Faker.instance();
        String cityName = faker.address().city();

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
        when(localWeatherGateway.retrieveLocationWeatherByCityName(cityName)).thenReturn(mockedLocalWeather);
        when(playlistSongGateway.findSongsByCategory(Category.CLASSICAL)).thenReturn(mockedSongsByCategory);

        LocalSong localSong = findLocalSongsByCity.execute(cityName);

        //then
        Assertions.assertNotNull(localSong);

        LocalInfo localInfo = localSong.getLocation();
        Assertions.assertNotNull(localInfo);
        Assertions.assertEquals(cityName, localInfo.getCity(), "expected celsius degrees");
        Assertions.assertEquals(celsiusTemperature, localInfo.getCelsiusTemperature(), "expected celsius degrees");
        Assertions.assertEquals(Category.CLASSICAL, localInfo.getCategory(), "expected pop category");

        Assertions.assertNotNull(localSong.getSongs());
        Assertions.assertEquals(3, localSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, localSong.getSongs(), "expected songs");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 30.01, 50.00, 35.00 })
    void givenLocationValid_whenTemperatureIsAboveThirtyDegrees_thenReturnPartyMusicPlaylist(double temperature) {
        //given
        Faker faker = Faker.instance();
        String cityName = faker.address().city();

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
        when(localWeatherGateway.retrieveLocationWeatherByCityName(cityName)).thenReturn(mockedLocalWeather);
        when(playlistSongGateway.findSongsByCategory(Category.PARTY)).thenReturn(mockedSongsByCategory);

        LocalSong localSong = findLocalSongsByCity.execute(cityName);

        //then
        Assertions.assertNotNull(localSong);

        LocalInfo localInfo = localSong.getLocation();
        Assertions.assertNotNull(localInfo);
        Assertions.assertEquals(cityName, localInfo.getCity(), "expected celsius degrees");
        Assertions.assertEquals(celsiusTemperature, localInfo.getCelsiusTemperature(), "expected celsius degrees");
        Assertions.assertEquals(Category.PARTY, localInfo.getCategory(), "expected pop category");

        Assertions.assertNotNull(localSong.getSongs());
        Assertions.assertEquals(3, localSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, localSong.getSongs(), "expected songs");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 15.00, 20.00, 29.99 })
    void givenLocationValid_whenTemperatureIsBetweenFifteenAndThirty_thenReturnPopPlaylist(double temperature) {
        //given
        Faker faker = Faker.instance();
        String cityName = faker.address().city();

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
        when(localWeatherGateway.retrieveLocationWeatherByCityName(cityName)).thenReturn(mockedLocalWeather);
        when(playlistSongGateway.findSongsByCategory(Category.POP)).thenReturn(mockedSongsByCategory);

        LocalSong localSong = findLocalSongsByCity.execute(cityName);

        //then
        Assertions.assertNotNull(localSong);

        LocalInfo localInfo = localSong.getLocation();
        Assertions.assertNotNull(localInfo);
        Assertions.assertEquals(cityName, localInfo.getCity(), "expected celsius degrees");
        Assertions.assertEquals(celsiusTemperature, localInfo.getCelsiusTemperature(), "expected celsius degrees");
        Assertions.assertEquals(Category.POP, localInfo.getCategory(), "expected pop category");

        Assertions.assertNotNull(localSong.getSongs());
        Assertions.assertEquals(3, localSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, localSong.getSongs(), "expected songs");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 10.00, 11.05, 14.00 })
    void givenLocationValid_whenTemperatureIsBetweenTenAndFourteenDegrees_thenReturnRockPlaylist(double temperature) {
        //given
        Faker faker = Faker.instance();
        String cityName = faker.address().city();

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
        when(localWeatherGateway.retrieveLocationWeatherByCityName(cityName)).thenReturn(mockedLocalWeather);
        when(playlistSongGateway.findSongsByCategory(Category.ROCK)).thenReturn(mockedSongsByCategory);

        LocalSong localSong = findLocalSongsByCity.execute(cityName);

        //then
        Assertions.assertNotNull(localSong);

        LocalInfo localInfo = localSong.getLocation();
        Assertions.assertNotNull(localInfo);
        Assertions.assertEquals(cityName, localInfo.getCity(), "expected celsius degrees");
        Assertions.assertEquals(celsiusTemperature, localInfo.getCelsiusTemperature(), "expected celsius degrees");
        Assertions.assertEquals(Category.ROCK, localInfo.getCategory(), "expected pop category");

        Assertions.assertNotNull(localSong.getSongs());
        Assertions.assertEquals(3, localSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, localSong.getSongs(), "expected songs");
    }
}