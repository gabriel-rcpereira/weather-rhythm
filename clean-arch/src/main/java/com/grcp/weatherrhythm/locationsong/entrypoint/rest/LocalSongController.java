package com.grcp.weatherrhythm.locationsong.entrypoint.rest;

import com.grcp.weatherrhythm.locationsong.domain.LocalSong;
import com.grcp.weatherrhythm.locationsong.entrypoint.rest.json.response.LocalSongResponse;
import com.grcp.weatherrhythm.locationsong.usecase.FindLocalSongsByCity;
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
public class LocalSongController {

    private static final String CITY_MUST_NOT_BE_BLANK = "001.001";

    private final FindLocalSongsByCity findLocalSongsByCity;

    public LocalSongController(FindLocalSongsByCity findLocalSongsByCity) {
        this.findLocalSongsByCity = findLocalSongsByCity;
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
