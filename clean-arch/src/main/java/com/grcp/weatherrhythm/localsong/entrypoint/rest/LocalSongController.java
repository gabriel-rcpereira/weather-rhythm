package com.grcp.weatherrhythm.localsong.entrypoint.rest;

import com.grcp.weatherrhythm.localsong.domain.LocalSong;
import com.grcp.weatherrhythm.localsong.domain.validation.annotation.Latitude;
import com.grcp.weatherrhythm.localsong.domain.validation.annotation.Longitude;
import com.grcp.weatherrhythm.localsong.entrypoint.rest.json.response.LocalSongResponse;
import com.grcp.weatherrhythm.localsong.usecase.FindLocalSongsByCity;
import com.grcp.weatherrhythm.localsong.usecase.FindLocalSongsByLatitudeAndLongitude;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
    @Operation(summary = "Get Playlist Songs by Latitude and Longitude")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LocalSongResponse.class))
            })
    })
    public ResponseEntity<LocalSongResponse> getFindLocationSongsByLatitudeAndLongitude(@RequestParam(value = "lat", required = false)
                                                                                            @NotNull(message = LATITUDE_MUST_NOT_BE_BLANK)
                                                                                            @Latitude(message = LATITUDE_IS_INVALID)
                                                                                            @Parameter(required = true, description = "Latitude accepts value between -94.999 and 94.999")
                                                                                                Double latitude,
                                                                                        @RequestParam(value = "long", required = false)
                                                                                            @NotNull(message = LONGITUDE_MUST_NOT_BE_BLANK)
                                                                                            @Longitude(message = LONGITUDE_IS_INVALID)
                                                                                            @Parameter(required = true, description = "Longitude accepts value between -179.999 and 179.999")
                                                                                                Double longitude) {
        log.info("Getting location songs by Latitude [{}] and Longitude [{}].", latitude, longitude);

        LocalSong localSong = findLocalSongsByLatitudeAndLongitude.execute(latitude, longitude);
        LocalSongResponse response = new LocalSongResponse(localSong);

        log.info("Getting location songs by Latitude [{}] and Longitude [{}] with success.", latitude, longitude);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/cities/songs")
    @Operation(summary = "Get Playlist Songs by City name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LocalSongResponse.class))
            })
    })
    public ResponseEntity<LocalSongResponse> getFindLocationSongsByCityName(@RequestParam("city")
                                                                                   @NotBlank(message = CITY_MUST_NOT_BE_BLANK) String city) {
        log.info("Getting location songs by City [{}].", city);

        LocalSong localSong = findLocalSongsByCity.execute(city);
        LocalSongResponse response = new LocalSongResponse(localSong);

        log.info("Getting location songs by City [{}] with success.", city);
        return ResponseEntity.ok(response);
    }
}
