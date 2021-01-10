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
import javax.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @Test
    void givenLocationValid_whenTemperatureIsBelowTenDegrees_thenReturnClassicalMusicPlaylist() {
        //given
        Faker faker = Faker.instance();
        String cityName = faker.address().city();

        Double celsiusTemperature = 9.99;
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

    @Test
    void givenLocationValid_whenTemperatureIsAboveThirtyDegrees_thenReturnPartyMusicPlaylist() {
        //given
        Faker faker = Faker.instance();
        String cityName = faker.address().city();

        Double celsiusTemperature = 30.01;
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

    @Test
    void givenLocationValid_whenTemperatureIsBetweenFifteenAndThirty_thenReturnPopPlaylist() {
        //given
        Faker faker = Faker.instance();
        String cityName = faker.address().city();

        Double celsiusTemperature = 30.00;
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

    @Test
    void givenLocationValid_whenTemperatureIsBetweenTenAndFourteenDegrees_thenReturnRockPlaylist() {
        //given
        Faker faker = Faker.instance();
        String cityName = faker.address().city();

        Double celsiusTemperature = 10.01;
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