package com.spring.api.music_project.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(includeFieldNames = true)
public class PosterImage {

    private String storageUrl;
    private String sizeFormat;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if ((o == null) || (getClass() != o.getClass())) {
            return false;
        } else {
            PosterImage objectPoster = (PosterImage) o;
            return Objects.equals(storageUrl, objectPoster.storageUrl)
                    && Objects.equals(sizeFormat, objectPoster.sizeFormat);
        }
    }

    @Override
    public int hashCode() {
        int primaryNumber = 214;
        int result = primaryNumber * Objects.hash(storageUrl, sizeFormat);
        return result;
    }
}
