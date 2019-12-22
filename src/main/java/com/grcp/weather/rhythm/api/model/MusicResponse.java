package com.grcp.weather.rhythm.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicResponse {

    private String rhythm;
    private String artistName;
    private String albumName;
    private String track;
}

