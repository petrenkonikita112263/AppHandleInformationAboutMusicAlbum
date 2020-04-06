package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.AlbumSummary;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface Savable {

    ByteArrayOutputStream createTemplateDocument(List<AlbumSummary> summaryList);

    ByteArrayOutputStream saveAlbum(String nameOfArtist, String titleOfAlbum);

}
