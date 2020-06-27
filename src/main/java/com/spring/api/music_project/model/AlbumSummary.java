package com.spring.api.music_project.model;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(includeFieldNames = true)
public class AlbumSummary {

    private String albumTitle;
    private String artistName;
    private List<PosterImage> listOfPosters;
    private List<Tracks> collectionOfTracks;
    private List<Tags> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if ((o == null) || (getClass() != o.getClass())) {
            return false;
        } else {
            AlbumSummary objectAlbum = (AlbumSummary) o;
            return Objects.equals(albumTitle, objectAlbum.albumTitle)
                    && Objects.equals(artistName, objectAlbum.artistName)
                    && Objects.equals(listOfPosters, objectAlbum.listOfPosters)
                    && Objects.equals(collectionOfTracks, objectAlbum.collectionOfTracks)
                    && Objects.equals(tags, objectAlbum.tags);
        }
    }

    @Override
    public int hashCode() {
        int primaryNumber = 74;
        int result = primaryNumber * Objects.hash(albumTitle, artistName, listOfPosters, collectionOfTracks, tags);
        return result;
    }
}
