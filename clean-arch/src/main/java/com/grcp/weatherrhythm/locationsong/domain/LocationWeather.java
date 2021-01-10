package com.grcp.weatherrhythm.locationsong.domain;

import com.grcp.weatherrhythm.locationsong.domain.exception.DomainException;
import com.grcp.weatherrhythm.locationsong.domain.exception.errors.DomainError;
import com.grcp.weatherrhythm.locationsong.domain.songcategory.RetrieveCategory;
import com.grcp.weatherrhythm.locationsong.domain.songcategory.impl.RetrieveClassicalCategory;
import com.grcp.weatherrhythm.locationsong.domain.songcategory.impl.RetrievePartyCategory;
import com.grcp.weatherrhythm.locationsong.domain.songcategory.impl.RetrievePopCategory;
import com.grcp.weatherrhythm.locationsong.domain.songcategory.impl.RetrieveRockCategory;
import java.util.Set;
import javax.validation.Validator;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class LocationWeather {

    private final Set<RetrieveCategory> retrieveCategories = Set.of(
            new RetrievePartyCategory(),
            new RetrievePopCategory(),
            new RetrieveRockCategory(),
            new RetrieveClassicalCategory()
    );

    private double celsiusTemperature;

    public Category retrieveCategoryByTemperature() {
        Category category = this.retrieveCategories.stream()
                .map(retrieveCategory -> {
                    LocationWeather locationWeather = this;
                    return retrieveCategory.execute(locationWeather);
                })
                .filter(categoryOpt -> categoryOpt.isPresent())
                .map(categoryOpt -> categoryOpt.get())
                .findFirst()
                .orElseThrow(() -> new DomainException(DomainError.ANY_CATEGORY_FOUND));

        return category;
    }

    public void validate(Validator validator) {
//        Set<ConstraintViolation<LocationWeather>> constraintViolations = validator.validate(this);
//        if (!constraintViolations.isEmpty()) {
//            throw new DomainException(constraintViolations);
//        }
    }
}
