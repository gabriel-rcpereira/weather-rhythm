package com.grcp.weatherrhythm.localsong.usecase;

import com.github.javafaker.Faker;
import com.grcp.weatherrhythm.localsong.domain.LocalInfo;
import com.grcp.weatherrhythm.localsong.domain.LocalSong;
import com.grcp.weatherrhythm.localsong.domain.LocalWeather;
import com.grcp.weatherrhythm.localsong.domain.Song;
import com.grcp.weatherrhythm.localsong.gateway.weather.LocalWeatherGateway;
import com.grcp.weatherrhythm.localsong.usecase.service.FindLocalSongByCelsiusTemperature;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindLocalSongsByCityTest {

    private final Faker faker = Faker.instance();
    private final LocalWeatherGateway localWeatherGateway = mock(LocalWeatherGateway.class);
    private final FindLocalSongByCelsiusTemperature findLocalSongByCelsiusTemperature = mock(FindLocalSongByCelsiusTemperature.class);

    private FindLocalSongsByCity findLocalSongsByCity;

    @BeforeEach
    public void loadContext() {
        findLocalSongsByCity = new FindLocalSongsByCity(localWeatherGateway, findLocalSongByCelsiusTemperature);
    }

    @Test
    void givenCityName_whenReturnsLocalWeatherAndLocalSongs_thenReturnsLocalSong() {
        //given
        String cityName = faker.address().city();

        double celsiusTemperature = faker.random().nextDouble();
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

        LocalSong mockedLocalSongs = LocalSong.builder()
                .songs(mockedSongsByCategory)
                .location(LocalInfo.builder()
                        .celsiusTemperature(celsiusTemperature)
                        .build())
                .build();

        //when
        when(localWeatherGateway.retrieveLocalWeatherByCityName(cityName)).thenReturn(mockedLocalWeather);
        when(findLocalSongByCelsiusTemperature.execute(celsiusTemperature)).thenReturn(mockedLocalSongs);

        LocalSong actualLocalSong = findLocalSongsByCity.execute(cityName);

        //then
        Assertions.assertNotNull(actualLocalSong);

        LocalInfo localInfo = actualLocalSong.getLocation();
        Assertions.assertNotNull(localInfo);
        Assertions.assertEquals(mockedLocalSongs.getLocation().getCelsiusTemperature(), localInfo.getCelsiusTemperature(), "expected celsius degrees");

        Assertions.assertNotNull(actualLocalSong.getSongs());
        Assertions.assertEquals(3, actualLocalSong.getSongs().size(), "expected number of songs");
        Assertions.assertEquals(mockedSongsByCategory, actualLocalSong.getSongs(), "expected songs");
    }

}