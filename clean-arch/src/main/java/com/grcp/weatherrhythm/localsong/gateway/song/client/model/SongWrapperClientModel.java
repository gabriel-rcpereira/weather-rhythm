package com.grcp.weatherrhythm.localsong.gateway.song.client.model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class SongWrapperClientModel {

    @Builder.Default
    private Set<SongDetailClientModel> songs = new HashSet<>();
    private ErrorDetailClientModel errorDetailClientModel;

    public boolean hasError() {
        return Optional.ofNullable(this.errorDetailClientModel)
                .isPresent();
    }
}
