package com.grcp.weatherrhythm.localsong.gateway.song.adapter;

import com.grcp.weatherrhythm.localsong.domain.Category;
import com.grcp.weatherrhythm.localsong.gateway.song.adapter.model.SongWrapperClientModel;

public interface SongAdapter {

    SongWrapperClientModel findSongsByCategory(Category category);
}
