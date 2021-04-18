package com.grcp.weatherrhythm.localsong.usecase;

import com.grcp.weatherrhythm.localsong.domain.LocalSong;
import com.grcp.weatherrhythm.localsong.domain.LocalWeather;
import com.grcp.weatherrhythm.localsong.gateway.weather.LocalWeatherGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FindLocalSongsByCity {

    private final LocalWeatherGateway localWeatherGateway;
    private final FindLocalSongByCelsiusTemperature findLocalSongByCelsiusTemperature;

    public FindLocalSongsByCity(LocalWeatherGateway localWeatherGateway,
                                FindLocalSongByCelsiusTemperature findLocalSongByCelsiusTemperature) {
        this.localWeatherGateway = localWeatherGateway;
        this.findLocalSongByCelsiusTemperature = findLocalSongByCelsiusTemperature;
    }

    public LocalSong execute(String city) {
        log.info("Executing finding local songs by city [{}].", city);

        LocalWeather localWeather = this.localWeatherGateway.retrieveLocalWeatherByCityName(city);
        LocalSong localSong = this.findLocalSongByCelsiusTemperature.execute(localWeather.getCelsiusTemperature());

        log.info("Executing finding local songs by city [{}] with success.", city);
        return localSong;
    }
}
