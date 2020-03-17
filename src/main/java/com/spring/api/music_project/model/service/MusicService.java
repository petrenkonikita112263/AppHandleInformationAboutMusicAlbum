package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.AlbumSummary;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MusicService implements IMusicService {

    private static final Logger LOGGER = Logger.getLogger(MusicService.class);

    private final UriComponentsBuilder uriComponentsBuilder;

    public MusicService(@Value("${APIkey}") String key) {
        this.uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("http://ws.audioscrobbler.com/2.0/").queryParam("APIkey", key);
    }

    @Override
    public AlbumSummary printAllInfo() {
        return null;
    }

    @Override
    public AlbumSummary obtaineAlbumThroughName(String titleOfAlbum) {
        return null;
    }

    @Override
    public AlbumSummary obtaineAlbumThroughSigner(String nameOfArtist) {
        return null;
    }

}
