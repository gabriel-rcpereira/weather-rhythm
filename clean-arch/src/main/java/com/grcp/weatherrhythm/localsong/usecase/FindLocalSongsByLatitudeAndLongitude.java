package com.grcp.weatherrhythm.localsong.usecase;

import com.grcp.weatherrhythm.localsong.domain.LocalSong;
import com.grcp.weatherrhythm.localsong.domain.LocalWeather;
import com.grcp.weatherrhythm.localsong.gateway.weather.LocalWeatherGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FindLocalSongsByLatitudeAndLongitude {

    private final LocalWeatherGateway localWeatherGateway;
    private final FindLocalSongByCelsiusTemperature findLocalSongByCelsiusTemperature;

    public FindLocalSongsByLatitudeAndLongitude(LocalWeatherGateway localWeatherGateway,
                                                FindLocalSongByCelsiusTemperature findLocalSongByCelsiusTemperature) {
        this.localWeatherGateway = localWeatherGateway;
        this.findLocalSongByCelsiusTemperature = findLocalSongByCelsiusTemperature;
    }

    public LocalSong execute(double latitude, double longitude) {
        log.info("Executing finding local songs by latitude [{}] and longitude [{}].", latitude, longitude);

        LocalWeather localWeather = this.localWeatherGateway.retrieveLocalWeatherByLatitudeAndLongitude(latitude, longitude);
        LocalSong localSong = this.findLocalSongByCelsiusTemperature.execute(localWeather.getCelsiusTemperature());

        log.info("Executing finding local songs by latitude [{}] and longitude [{}] with success.", latitude, longitude);
        return localSong;
    }
}
