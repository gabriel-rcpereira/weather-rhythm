package com.grcp.weatherrhythm.locationsong.gateway.song.impl;

import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.Song;
import com.grcp.weatherrhythm.locationsong.gateway.song.PlaylistSongGateway;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class PlaylistSongGatewayImpl implements PlaylistSongGateway {

    @Override
    public Set<Song> findSongsByCategory(Category category) {
        return null;
    }
}
