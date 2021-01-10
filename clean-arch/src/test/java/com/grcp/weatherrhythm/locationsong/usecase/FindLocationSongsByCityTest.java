package com.grcp.weatherrhythm.locationsong.usecase;

import com.github.javafaker.Faker;
import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.LocationInfo;
import com.grcp.weatherrhythm.locationsong.domain.LocationSong;
import com.grcp.weatherrhythm.locationsong.domain.LocationWeather;
import com.grcp.weatherrhythm.locationsong.domain.Song;
import com.grcp.weatherrhythm.locationsong.gateway.song.PlaylistSongGateway;
import com.grcp.weatherrhythm.locationsong.gateway.weather.LocationWeatherGateway;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
class FindLocationSongsByCityTest {

    private FindLocationSongsByCity findLocationSongsByCity;
    @Mock
    private LocationWeatherGateway locationWeatherGateway;
    @Mock
    private PlaylistSongGateway playlistSongGateway;

    @BeforeEach
    public void loadContext() {
        findLocationSongsByCity = new FindLocationSongsByCity(locationWeatherGateway, playlistSongGateway);
    }

    @ParameterizedTest
    @ValueSource(doubles = { 9.99, 0.0, -100.00 })
    void givenLocationValid_whenTemperatureIsBelowTenDegrees_thenReturnClassicalMusicPlaylist(double temperature) {
        //given
        Faker faker = Faker.instance();
        String cityName = faker.address().city();

        Double celsiusTemperature = temperature;
        LocationWeather mockedLocationWeather = LocationWeather.builder()
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
        when(locationWeatherGateway.retrieveLocationWeatherByCityName(cityName)).thenReturn(mockedLocationWeather);
        when(playlistSongGateway.findSongsByCategory(Category.CLASSICAL)).thenReturn(mockedSongsByCategory);

        LocationSong locationSong = findLocationSongsByCity.execute(cityName);

        //then
        Assertions.assertNotNull(locationSong);

        LocationInfo locationInfo = locationSong.getLocation();
        Assertions.assertNotNull(locationInfo);
        Assertions.assertEquals(cityName, locationInfo.getCity(), "expected celsius degrees");
        Assertions.assertEquals(celsiusTemperature, locationInfo.getCelsiusTemperature(), "expected celsius degrees");
        Assertions.assertEquals(Category.CLASSICAL, locationInfo.getCategory(), "expected pop category");

        Assertions.assertNotNull(locationSong.getSongs());
        Assertions.assertEquals(3, locationSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, locationSong.getSongs(), "expected songs");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 30.01, 50.00, 35.00 })
    void givenLocationValid_whenTemperatureIsAboveThirtyDegrees_thenReturnPartyMusicPlaylist(double temperature) {
        //given
        Faker faker = Faker.instance();
        String cityName = faker.address().city();

        Double celsiusTemperature = temperature;
        LocationWeather mockedLocationWeather = LocationWeather.builder()
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
        when(locationWeatherGateway.retrieveLocationWeatherByCityName(cityName)).thenReturn(mockedLocationWeather);
        when(playlistSongGateway.findSongsByCategory(Category.PARTY)).thenReturn(mockedSongsByCategory);

        LocationSong locationSong = findLocationSongsByCity.execute(cityName);

        //then
        Assertions.assertNotNull(locationSong);

        LocationInfo locationInfo = locationSong.getLocation();
        Assertions.assertNotNull(locationInfo);
        Assertions.assertEquals(cityName, locationInfo.getCity(), "expected celsius degrees");
        Assertions.assertEquals(celsiusTemperature, locationInfo.getCelsiusTemperature(), "expected celsius degrees");
        Assertions.assertEquals(Category.PARTY, locationInfo.getCategory(), "expected pop category");

        Assertions.assertNotNull(locationSong.getSongs());
        Assertions.assertEquals(3, locationSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, locationSong.getSongs(), "expected songs");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 15.00, 20.00, 29.99 })
    void givenLocationValid_whenTemperatureIsBetweenFifteenAndThirty_thenReturnPopPlaylist(double temperature) {
        //given
        Faker faker = Faker.instance();
        String cityName = faker.address().city();

        Double celsiusTemperature = temperature;
        LocationWeather mockedLocationWeather = LocationWeather.builder()
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
        when(locationWeatherGateway.retrieveLocationWeatherByCityName(cityName)).thenReturn(mockedLocationWeather);
        when(playlistSongGateway.findSongsByCategory(Category.POP)).thenReturn(mockedSongsByCategory);

        LocationSong locationSong = findLocationSongsByCity.execute(cityName);

        //then
        Assertions.assertNotNull(locationSong);

        LocationInfo locationInfo = locationSong.getLocation();
        Assertions.assertNotNull(locationInfo);
        Assertions.assertEquals(cityName, locationInfo.getCity(), "expected celsius degrees");
        Assertions.assertEquals(celsiusTemperature, locationInfo.getCelsiusTemperature(), "expected celsius degrees");
        Assertions.assertEquals(Category.POP, locationInfo.getCategory(), "expected pop category");

        Assertions.assertNotNull(locationSong.getSongs());
        Assertions.assertEquals(3, locationSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, locationSong.getSongs(), "expected songs");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 10.00, 11.05, 14.00 })
    void givenLocationValid_whenTemperatureIsBetweenTenAndFourteenDegrees_thenReturnRockPlaylist(double temperature) {
        //given
        Faker faker = Faker.instance();
        String cityName = faker.address().city();

        Double celsiusTemperature = temperature;
        LocationWeather mockedLocationWeather = LocationWeather.builder()
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
        when(locationWeatherGateway.retrieveLocationWeatherByCityName(cityName)).thenReturn(mockedLocationWeather);
        when(playlistSongGateway.findSongsByCategory(Category.ROCK)).thenReturn(mockedSongsByCategory);

        LocationSong locationSong = findLocationSongsByCity.execute(cityName);

        //then
        Assertions.assertNotNull(locationSong);

        LocationInfo locationInfo = locationSong.getLocation();
        Assertions.assertNotNull(locationInfo);
        Assertions.assertEquals(cityName, locationInfo.getCity(), "expected celsius degrees");
        Assertions.assertEquals(celsiusTemperature, locationInfo.getCelsiusTemperature(), "expected celsius degrees");
        Assertions.assertEquals(Category.ROCK, locationInfo.getCategory(), "expected pop category");

        Assertions.assertNotNull(locationSong.getSongs());
        Assertions.assertEquals(3, locationSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, locationSong.getSongs(), "expected songs");
    }
}