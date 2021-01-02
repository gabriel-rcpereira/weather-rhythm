package com.grcp.weatherrhythm.locationsong.gateway.song;

import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.Song;
import java.util.Set;

public interface PlaylistSongGateway {

    Set<Song> findSongsByCategory(Category category);
}
