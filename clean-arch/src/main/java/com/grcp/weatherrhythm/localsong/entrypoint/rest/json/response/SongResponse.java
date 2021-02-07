package com.grcp.weatherrhythm.localsong.entrypoint.rest.json.response;

import com.grcp.weatherrhythm.localsong.domain.Song;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SongResponse {

    private String artistName;
    private String albumName;
    private String apiTrack;

    public static class Builder {

        private String artistName;
        private String albumName;
        private String apiTrack;

        public Builder() {
        }

        public Builder withSong(Song song) {
            this.artistName = song.getArtistName();
            this.albumName = song.getAlbumName();
            this.apiTrack = song.getApiTrack();

            return this;
        }

        public SongResponse build() {
            return new SongResponse(this.artistName, this.albumName, this.apiTrack);
        }
    }
}
