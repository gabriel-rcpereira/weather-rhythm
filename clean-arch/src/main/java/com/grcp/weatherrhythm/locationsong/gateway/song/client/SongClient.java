package com.grcp.weatherrhythm.locationsong.gateway.song.client;

import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.gateway.song.client.model.SongWrapperClientModel;

public interface SongClient {

    SongWrapperClientModel findSongsByCategory(Category category);
}
