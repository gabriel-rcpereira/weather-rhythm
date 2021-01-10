package com.grcp.weatherrhythm.locationsong.domain.songcategory;

import com.grcp.weatherrhythm.locationsong.domain.Category;
import com.grcp.weatherrhythm.locationsong.domain.LocalWeather;
import java.util.Optional;

public interface RetrieveCategory {

    Optional<Category> execute(LocalWeather localWeather);
}
