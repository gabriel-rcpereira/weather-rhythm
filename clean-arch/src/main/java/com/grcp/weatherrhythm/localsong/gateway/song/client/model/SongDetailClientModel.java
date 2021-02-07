package com.grcp.weatherrhythm.localsong.gateway.song.client.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class SongDetailClientModel {

    private String artistName;
    private String albumName;
    private String apiTrack;
}
