package com.grcp.weatherrhythm.locationsong.domain;

import com.grcp.weatherrhythm.locationsong.domain.exception.DomainException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Song {

    @NotBlank(message = "002.004")
    private String artistName;
    @NotBlank(message = "002.005")
    private String albumName;
    @NotBlank(message = "002.006")
    private String apiTrack;

    public void validate(Validator validator) {
        Set<ConstraintViolation<Song>> constraintViolations = validator.validate(this);

        if (!constraintViolations.isEmpty()) {
            throw new DomainException(constraintViolations);
        }
    }
}
