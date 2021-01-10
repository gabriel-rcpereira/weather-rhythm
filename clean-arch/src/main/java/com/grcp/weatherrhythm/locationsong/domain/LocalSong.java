package com.grcp.weatherrhythm.locationsong.domain;

import java.util.HashSet;
import java.util.Set;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalSong {

    @NotNull
    private LocalInfo location;
    @Builder.Default
    private Set<Song> songs = new HashSet<>();

    public void validate(Validator validator) {
//        Set<ConstraintViolation<LocationSong>> locationSongViolations = validator.validate(this);
//        if (!locationSongViolations.isEmpty()) {
//            throw new DomainException(locationSongViolations);
//        }
//
//        this.locationWeather.validate(validator);
//        this.songs.forEach(song -> song.validate(validator));
    }
}
