package com.grcp.weatherrhythm.locationsong.entrypoint.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grcp.weatherrhythm.locationsong.config.message.MessageConfiguration;
import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.LocationInfo;
import com.grcp.weatherrhythm.locationsong.domain.LocationSong;
import com.grcp.weatherrhythm.locationsong.domain.Song;
import com.grcp.weatherrhythm.locationsong.entrypoint.rest.exception.handler.CustomExceptionHandler;
import com.grcp.weatherrhythm.locationsong.gateway.message.impl.MessageSourceImpl;
import com.grcp.weatherrhythm.locationsong.usecase.FindLocationSongsByCity;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = { MessageConfiguration.class, MessageSourceImpl.class, LocationSongController.class, CustomExceptionHandler.class })
class LocationSongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private FindLocationSongsByCity findLocationSongsByCity;

    @Test
    public void givenValidCity_whenRequestApiByCity_thenExpectsSuccessResponse() throws Exception {
        //given
        var city = "Campinas";
        LocationInfo mockedLocationInfo = LocationInfo.builder()
                .celsiusTemperature(32.0)
                .city(city)
                .category(Category.PARTY)
                .build();
        Set<Song> mockedSongs = Set.of(
                Song.builder().artistName("artistOne").albumName("albumOne").apiTrack("trackOne").build(),
                Song.builder().artistName("artistTwo").albumName("albumTwo").apiTrack("trackTwo").build()
        );
        LocationSong mockedLocationSong = LocationSong.builder()
                .location(mockedLocationInfo)
                .songs(mockedSongs)
                .build();

        //when
        when(findLocationSongsByCity.execute(city)).thenReturn(mockedLocationSong);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/cities/songs")
                        .param("city", city));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))

                .andExpect(jsonPath("$.locationInfo.celsiusTemperature", is(32.0)))

                .andExpect(jsonPath("$.songs.length()", is(2)))
                .andExpect(jsonPath("$.songs[*].artistName", hasItem("artistOne")))
                .andExpect(jsonPath("$.songs[*].albumName", hasItem("albumOne")))
                .andExpect(jsonPath("$.songs[*].apiTrack", hasItem("trackOne")))

                .andExpect(jsonPath("$.songs[*].artistName", hasItem("artistTwo")))
                .andExpect(jsonPath("$.songs[*].albumName", hasItem("albumTwo")))
                .andExpect(jsonPath("$.songs[*].apiTrack", hasItem("trackTwo")));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " " })
    public void givenInvalidCity_whenRequestApiByCity_thenExpectsBadRequest(String city) throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/cities/songs")
                        .param("city", city));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.service", is("001")))
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[*].code", hasItem("001.001")))
                .andExpect(jsonPath("$.errors[*].message", hasItem("The City must not be blank.")));
    }
}