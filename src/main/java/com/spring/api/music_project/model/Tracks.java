package com.spring.api.music_project.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(includeFieldNames = true)
public class Tracks {

    private String trackName;
    private String duration;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if ((o == null) || (getClass() != o.getClass())) {
            return false;
        } else {
            Tracks tracks = (Tracks) o;
            return duration == tracks.duration &&
                    Objects.equals(trackName, tracks.trackName);
        }
    }

    @Override
    public int hashCode() {
        int primaryNumber = 545;
        int result = primaryNumber * Objects.hash(trackName, duration);
        return result;
    }
}
