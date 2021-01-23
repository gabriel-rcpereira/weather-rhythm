package com.grcp.weatherrhythm.locationsong.gateway.song.impl.spotify.model;

import com.grcp.weatherrhythm.locationsong.domain.Category;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum SpotifyCategoryId {

    PARTY(Category.PARTY, "party"),
    POP(Category.POP, "pop"),
    ROCK(Category.ROCK, "rock"),
    CLASSICAL(Category.CLASSICAL, "45o9GyQis9HRPTYUXuS1hQ");

    private Category fromCategory;
    private String id;

    SpotifyCategoryId(Category fromCategory, String spotifyId) {
        this.fromCategory = fromCategory;
        this.id = spotifyId;
    }

    public static SpotifyCategoryId valueOfCategory(Category category) {
        return Stream.of(values())
                .filter(spotifyCategoryId -> spotifyCategoryId.fromCategory == category)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid category " + category));
    }
}
