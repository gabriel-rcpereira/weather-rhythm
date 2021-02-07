package com.grcp.weatherrhythm.localsong.gateway.song;

import com.grcp.weatherrhythm.localsong.domain.Category;
import com.grcp.weatherrhythm.localsong.domain.Song;
import java.util.Set;

public interface PlaylistSongGateway {

    Set<Song> findSongsByCategory(Category category);
}
