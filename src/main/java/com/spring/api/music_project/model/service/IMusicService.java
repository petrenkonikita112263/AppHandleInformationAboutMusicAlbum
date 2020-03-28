package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.AlbumSummary;

import java.util.List;

public interface IMusicService {

    List<AlbumSummary> printAllInfo();

    List<AlbumSummary> obtaineAlbumThroughName(String nameOfArtist, String titleOfAlbum, String typeFormat);

}
