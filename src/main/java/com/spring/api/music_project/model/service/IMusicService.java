package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.AlbumSummary;

import java.util.List;

public interface IMusicService {

    List<AlbumSummary> obtaineAlbumThroughName(String nameOfArtist, String titleOfAlbum);

    List<AlbumSummary> obtaineAsyncAlbumThroughName(String nameOfArtist, String titleOfAlbum);

}
