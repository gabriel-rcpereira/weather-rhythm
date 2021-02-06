package com.grcp.weatherrhythm.locationsong.entrypoint.rest;

import com.grcp.weatherrhythm.locationsong.domain.LocalSong;
import com.grcp.weatherrhythm.locationsong.domain.validation.annotation.Latitude;
import com.grcp.weatherrhythm.locationsong.domain.validation.annotation.Longitude;
import com.grcp.weatherrhythm.locationsong.entrypoint.rest.json.response.LocalSongResponse;
import com.grcp.weatherrhythm.locationsong.usecase.FindLocalSongsByCity;
import com.grcp.weatherrhythm.locationsong.usecase.FindLocalSongsByLatitudeAndLongitude;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
public class LocalSongController {

    private static final String CITY_MUST_NOT_BE_BLANK = "001.001";
    private static final String LATITUDE_MUST_NOT_BE_BLANK = "001.002";
    private static final String LATITUDE_IS_INVALID = "001.003";
    private static final String LONGITUDE_MUST_NOT_BE_BLANK = "001.004";
    private static final String LONGITUDE_IS_INVALID = "001.005";

    private final FindLocalSongsByCity findLocalSongsByCity;
    private final FindLocalSongsByLatitudeAndLongitude findLocalSongsByLatitudeAndLongitude;

    @GetMapping("/api/v1/cities/lat-long/songs")
    public ResponseEntity<LocalSongResponse> getFindLocationSongsByLatitudeAndLongitude(@RequestParam(value = "lat", required = false)
                                                                                            @NotNull(message = LATITUDE_MUST_NOT_BE_BLANK)
                                                                                            @Latitude(message = LATITUDE_IS_INVALID)
                                                                                                Double latitude,
                                                                                        @RequestParam(value = "long", required = false)
                                                                                            @NotNull(message = LONGITUDE_MUST_NOT_BE_BLANK)
                                                                                            @Longitude(message = LONGITUDE_IS_INVALID)
                                                                                                Double longitude) {
        log.info("Getting location songs by Latitude [{}] and Longitude [{}].", latitude, longitude);

        LocalSong localSong = findLocalSongsByLatitudeAndLongitude.execute(latitude, longitude);
        LocalSongResponse response = new LocalSongResponse(localSong);

        log.info("Getting location songs by Latitude [{}] and Longitude [{}].", latitude, longitude);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/cities/songs")
    public ResponseEntity<LocalSongResponse> getFindLocationSongsByCityName(@RequestParam("city")
                                                                                   @NotBlank(message = CITY_MUST_NOT_BE_BLANK) String city) {
        log.info("Getting location songs by City [{}].", city);

        LocalSong localSong = findLocalSongsByCity.execute(city);
        LocalSongResponse response = new LocalSongResponse(localSong);

        log.info("Getting location songs by City [{}] executed with success.", city);
        return ResponseEntity.ok(response);
    }
}
