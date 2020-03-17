package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.AlbumSummary;

public interface IMusicService {

    AlbumSummary printAllInfo();

    AlbumSummary obtaineAlbumThroughName(String titleOfAlbum);

    AlbumSummary obtaineAlbumThroughSigner(String nameOfArtist);



}
