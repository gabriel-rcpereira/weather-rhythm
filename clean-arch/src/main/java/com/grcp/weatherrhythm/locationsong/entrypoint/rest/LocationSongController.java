package com.grcp.weatherrhythm.locationsong.entrypoint.rest;

import com.grcp.weatherrhythm.locationsong.domain.LocationSong;
import com.grcp.weatherrhythm.locationsong.entrypoint.rest.json.response.LocationSongResponse;
import com.grcp.weatherrhythm.locationsong.usecase.FindLocationSongsByCityName;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
public class LocationSongController {

    private static final String CITY_MUST_NOT_BE_BLANK = "001.001";

    private final FindLocationSongsByCityName findLocationSongsByCityName;

    public LocationSongController(FindLocationSongsByCityName findLocationSongsByCityName) {
        this.findLocationSongsByCityName = findLocationSongsByCityName;
    }

    @GetMapping("/api/v1/cities/songs")
    public ResponseEntity<LocationSongResponse> getFindLocationSongsByCityName(@RequestParam("city")
                                                                                   @NotBlank(message = CITY_MUST_NOT_BE_BLANK) String city) {
        log.info("Getting location songs by City [{}].", city);

        LocationSong locationSong = findLocationSongsByCityName.execute(city);
        LocationSongResponse response = new LocationSongResponse.Builder()
                .withLocationSong(locationSong)
                .build();

        log.info("Getting location songs by City [{}] executed with success.", city);
        return ResponseEntity.ok(response);
    }
}
