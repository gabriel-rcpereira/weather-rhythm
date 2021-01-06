package com.grcp.weatherrhythm.locationsong.domain;

import com.grcp.weatherrhythm.locationsong.domain.exception.DomainException;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
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
public class LocationSong {

    @NotNull
    private LocationWeather locationWeather;
    @Builder.Default
    private Set<Song> songs = new HashSet<>();

    public void validate(Validator validator) {
        Set<ConstraintViolation<LocationSong>> locationSongViolations = validator.validate(this);
        if (!locationSongViolations.isEmpty()) {
            throw new DomainException(locationSongViolations);
        }

        this.locationWeather.validate(validator);
        this.songs.forEach(song -> song.validate(validator));
    }
}
