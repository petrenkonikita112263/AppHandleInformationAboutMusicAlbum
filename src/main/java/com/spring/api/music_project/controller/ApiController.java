package com.spring.api.music_project.controller;

import com.spring.api.music_project.model.AlbumSummary;
import com.spring.api.music_project.model.service.MusicService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiController {

    private static final Logger LOGGER = Logger.getLogger(ApiController.class);

    protected MusicService musicService;

    @GetMapping(value = "/")
    public ResponseEntity getAll() {
        AlbumSummary albums = musicService.printAllInfo();
        return ResponseEntity.ok(albums);
    }

    @GetMapping(value = "album/{album}")
    public ResponseEntity getAlbumByName(@RequestParam(name = "albumTitle") String albumTitle) {
        AlbumSummary albums = musicService.obtaineAlbumThroughName(albumTitle);
//        List<AlbumSummary> albums = new ArrayList<>();
        return ResponseEntity.ok(albums);
    }

    @GetMapping(value = "album/{artist}")
    public ResponseEntity getAlbumBySigner(@RequestParam(name = "artistName") String artistName) {
        AlbumSummary albums = musicService.obtaineAlbumThroughSigner(artistName);
//        List<AlbumSummary> albums = new ArrayList<>();
        return ResponseEntity.ok(albums);
    }

}
