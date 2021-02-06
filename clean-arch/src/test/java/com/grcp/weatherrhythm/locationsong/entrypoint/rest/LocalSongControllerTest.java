package com.grcp.weatherrhythm.locationsong.entrypoint.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.grcp.weatherrhythm.locationsong.config.message.MessageConfiguration;
import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.LocalInfo;
import com.grcp.weatherrhythm.locationsong.domain.LocalSong;
import com.grcp.weatherrhythm.locationsong.domain.Song;
import com.grcp.weatherrhythm.locationsong.entrypoint.rest.exception.handler.CustomExceptionHandler;
import com.grcp.weatherrhythm.locationsong.gateway.message.impl.MessageSourceImpl;
import com.grcp.weatherrhythm.locationsong.usecase.FindLocalSongsByCity;
import com.grcp.weatherrhythm.locationsong.usecase.FindLocalSongsByLatitudeAndLongitude;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = { MessageConfiguration.class, MessageSourceImpl.class, LocalSongController.class, CustomExceptionHandler.class })
class LocalSongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private FindLocalSongsByCity findLocalSongsByCity;

    @MockBean
    private FindLocalSongsByLatitudeAndLongitude findLocalSongsByLatitudeAndLongitude;

    Faker faker;

    @BeforeEach
    void setUp() {
        faker = Faker.instance();
    }

    @Test
    public void givenValidCity_whenRequestApiByCity_thenExpectsSuccessResponse() throws Exception {
        //given
        var city = "Campinas";
        LocalInfo mockedLocalInfo = LocalInfo.builder()
                .celsiusTemperature(32.0)
                .city(city)
                .category(Category.PARTY)
                .build();
        Set<Song> mockedSongs = Set.of(
                Song.builder().artistName("artistOne").albumName("albumOne").apiTrack("trackOne").build(),
                Song.builder().artistName("artistTwo").albumName("albumTwo").apiTrack("trackTwo").build()
        );
        LocalSong mockedLocalSong = LocalSong.builder()
                .location(mockedLocalInfo)
                .songs(mockedSongs)
                .build();

        //when
        when(findLocalSongsByCity.execute(city)).thenReturn(mockedLocalSong);

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

    @Test
    public void givenValidLatLong_whenRequestApiByLatitudeAndLongitude_thenExpectsSuccessResponse() throws Exception {
        // given
        Double latitude = faker.number().randomDouble(5, -180, 180);
        Double longitude = faker.number().randomDouble(5, -45, 45);

        double celsiusTemperature = faker.number().randomDouble(2, -20, 45);
        LocalInfo mockedLocalInfo = LocalInfo.builder()
                .city(faker.address().city())
                .celsiusTemperature(celsiusTemperature)
                .category(Category.PARTY)
                .build();

        Song mockedSongOne = Song.builder()
                .apiTrack(faker.internet().url())
                .albumName(faker.pokemon().name())
                .artistName(faker.artist().name())
                .build();

        Set<Song> mockedSongs = Set.of(mockedSongOne);

        LocalSong mockedLocalSong = LocalSong.builder()
                .location(mockedLocalInfo)
                .songs(mockedSongs)
                .build();

        // when
        when(findLocalSongsByLatitudeAndLongitude.execute(latitude, longitude)).thenReturn(mockedLocalSong);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/cities/lat-long/songs")
                        .param("lat", latitude.toString())
                        .param("long", longitude.toString()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))

                .andExpect(jsonPath("$.locationInfo.celsiusTemperature", is(celsiusTemperature)))

                .andExpect(jsonPath("$.songs.length()", is(1)))
                .andExpect(jsonPath("$.songs[*].artistName", hasItem(mockedSongOne.getArtistName())))
                .andExpect(jsonPath("$.songs[*].albumName", hasItem(mockedSongOne.getAlbumName())))
                .andExpect(jsonPath("$.songs[*].apiTrack", hasItem(mockedSongOne.getApiTrack())));
    }

    @ParameterizedTest
    @ValueSource(doubles = { 180.00000001, -180.00000001, 200.000, -200.00000001 })
    public void givenInvalidLatAndValidLong_whenRequestApiByLatitudeAndLongitude_thenExpectsBadRequestResponse(Double latitude) throws Exception {
        // given
        Double longitude = faker.number().randomDouble(5, -45, 45);

        // when
        inOrder(findLocalSongsByLatitudeAndLongitude).verifyNoMoreInteractions();

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/cities/lat-long/songs")
                        .param("lat", latitude.toString())
                        .param("long", longitude.toString()));

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.service", is("001")))
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[*].code", hasItem("001.003")))
                .andExpect(jsonPath("$.errors[*].message", hasItem("The Latitude is invalid. A valid value needs to be between -180.00 and 180.00.")));
    }

    @ParameterizedTest
    @ValueSource(doubles = { 45.000001, -45.000001, 100.100000, -200.100000 })
    public void givenValidLatAndInvalidLong_whenRequestApiByLatitudeAndLongitude_thenExpectsBadRequestResponse(Double longitude) throws Exception {
        // given
        Double latitude = faker.number().randomDouble(5, -180, 180);

        // when
        inOrder(findLocalSongsByLatitudeAndLongitude).verifyNoMoreInteractions();

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/cities/lat-long/songs")
                        .param("lat", latitude.toString())
                        .param("long", longitude.toString()));

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.service", is("001")))
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[*].code", hasItem("001.005")))
                .andExpect(jsonPath("$.errors[*].message", hasItem("The Longitude is invalid. A valid value needs to be between -45.00 and 45.00.")));
    }

    @Test
    public void givenNoLatParameterAndInvalidLong_whenRequestApiByLatitudeAndLongitude_thenExpectsBadRequestResponse() throws Exception {
        // given
        Double longitude = faker.number().randomDouble(5, -45, 45);

        // when
        inOrder(findLocalSongsByLatitudeAndLongitude).verifyNoMoreInteractions();

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/cities/lat-long/songs")
                        .param("long", longitude.toString()));

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.service", is("001")))
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[*].code", hasItem("001.002")))
                .andExpect(jsonPath("$.errors[*].message", hasItem("The Latitude must not be blank.")));
    }

    @Test
    public void givenValidLatAndNoLongParameter_whenRequestApiByLatitudeAndLongitude_thenExpectsBadRequestResponse() throws Exception {
        // given
        Double latitude = faker.number().randomDouble(5, -180, 180);

        // when
        inOrder(findLocalSongsByLatitudeAndLongitude).verifyNoMoreInteractions();

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/cities/lat-long/songs")
                        .param("lat", latitude.toString()));

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.service", is("001")))
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[*].code", hasItem("001.004")))
                .andExpect(jsonPath("$.errors[*].message", hasItem("The Longitude must not be blank.")));
    }
}