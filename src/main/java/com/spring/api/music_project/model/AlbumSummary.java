package com.spring.api.music_project.model;

import java.util.Objects;

public class AlbumSummary {

    private String albumTitle;
    private Artist artistName;
    private int albumId;
    private PosterImage posterImage;

    public AlbumSummary() {
    }

    public AlbumSummary(String albumTitle, Artist artistName, int albumId, PosterImage posterImage) {
        this.albumTitle = albumTitle;
        this.artistName = artistName;
        this.albumId = albumId;
        this.posterImage = posterImage;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public Artist getArtistName() {
        return artistName;
    }

    public void setArtistName(Artist artistName) {
        this.artistName = artistName;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public PosterImage getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(PosterImage posterImage) {
        this.posterImage = posterImage;
    }

    @Override
    public String toString() {
        return "AlbumSummary{"
                + "albumTitle='" + albumTitle + '\''
                + ", artistName=" + artistName
                + ", albumId=" + albumId
                + ", posterImage=" + posterImage
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            AlbumSummary objectAlbum = (AlbumSummary) o;
            return albumId == objectAlbum.albumId
                    && Objects.equals(albumTitle, objectAlbum.albumTitle)
                    && Objects.equals(artistName, objectAlbum.artistName)
                    && Objects.equals(posterImage, objectAlbum.posterImage);
        }
    }

    @Override
    public int hashCode() {
        int primaryNumber = 74;
        int result = primaryNumber * Objects.hash(albumTitle, artistName, albumId, posterImage);
        return result;
    }
}
