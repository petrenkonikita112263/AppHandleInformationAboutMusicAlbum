package com.spring.api.music_project.model;

import java.util.List;
import java.util.Objects;

public class AlbumSummary {

    private String albumTitle;
    private String artistName;
    private List<PosterImage> listOfPosters;
    private List<Tracks> collectionOfTracks;

    public AlbumSummary() {
    }

    public AlbumSummary(String albumTitle, String artistName, List<PosterImage> listOfPosters, List<Tracks> collectionOfTracks) {
        this.albumTitle = albumTitle;
        this.artistName = artistName;
        this.listOfPosters = listOfPosters;
        this.collectionOfTracks = collectionOfTracks;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public List<PosterImage> getListOfPosters() {
        return listOfPosters;
    }

    public void setListOfPosters(List<PosterImage> listOfPosters) {
        this.listOfPosters = listOfPosters;
    }

    public List<Tracks> getCollectionOfTracks() {
        return collectionOfTracks;
    }

    public void setCollectionOfTracks(List<Tracks> collectionOfTracks) {
        this.collectionOfTracks = collectionOfTracks;
    }

    @Override
    public String toString() {
        return "AlbumSummary{" +
                "albumTitle='" + albumTitle + '\'' +
                ", artistName='" + artistName + '\'' +
                ", listOfPosters=" + listOfPosters +
                ", collectionOfTracks=" + collectionOfTracks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            AlbumSummary objectAlbum = (AlbumSummary) o;
            return Objects.equals(albumTitle, objectAlbum.albumTitle)
                    && Objects.equals(artistName, objectAlbum.artistName)
                    && Objects.equals(listOfPosters, objectAlbum.listOfPosters)
                    && Objects.equals(collectionOfTracks, objectAlbum.collectionOfTracks);
        }
    }

    @Override
    public int hashCode() {
        int primaryNumber = 74;
        int result = primaryNumber * Objects.hash(albumTitle, artistName, listOfPosters, collectionOfTracks);
        return result;
    }
}
