package com.grcp.weatherrhythm.locationsong.usecase;

import com.github.javafaker.Faker;
import com.grcp.weatherrhythm.locationsong.domain.Category;
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
    @Mock
    private Validator validator;

    @BeforeEach
    public void loadContext() {
        findLocationSongsByCity = new FindLocationSongsByCity(locationWeatherGateway, playlistSongGateway, validator);
    }

    @Test
    void givenLocationValid_whenTemperatureBelowTenDegrees_thenReturnClassicalMusicPlaylist() {
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

        Assertions.assertNotNull(locationSong.getLocationWeather());
        Assertions.assertEquals(locationSong.getLocationWeather().getCelsiusTemperature(), celsiusTemperature);

        Assertions.assertNotNull(locationSong.getSongs());
        Assertions.assertTrue(locationSong.getSongs().size() == 3);
    }
}