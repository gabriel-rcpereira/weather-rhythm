package com.grcp.weatherrhythm.localsong.gateway.song.client;

import com.grcp.weatherrhythm.localsong.domain.Category;
import com.grcp.weatherrhythm.localsong.gateway.song.client.model.SongWrapperClientModel;

public interface SongClient {

    SongWrapperClientModel findSongsByCategory(Category category);
}
