package com.spring.api.music_project.model;

public class AlbumSummary {

    private String albumTitle;
    private Artist artistName;
    private int albumId;
    private String albumPosterUrl;

    public AlbumSummary() {
    }

    public AlbumSummary(String albumTitle, Artist artistName, int albumId, String albumPosterUrl) {
        this.albumTitle = albumTitle;
        this.artistName = artistName;
        this.albumId = albumId;
        this.albumPosterUrl = albumPosterUrl;
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

    public String getAlbumPosterUrl() {
        return albumPosterUrl;
    }

    public void setAlbumPosterUrl(String albumPosterUrl) {
        this.albumPosterUrl = albumPosterUrl;
    }
}
