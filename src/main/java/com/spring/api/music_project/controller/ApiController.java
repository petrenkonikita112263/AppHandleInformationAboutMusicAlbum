package com.spring.api.music_project.controller;

import com.spring.api.music_project.model.AlbumSummary;
import com.spring.api.music_project.model.service.ConverterService;
import com.spring.api.music_project.model.service.IMusicService;
import com.spring.api.music_project.model.service.MusicService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class ApiController {

    private static final Logger LOGGER = Logger.getLogger(ApiController.class);

    protected IMusicService musicService;
    protected ConverterService converterService;

    public ApiController(IMusicService musicService) {
        this.musicService = musicService;
    }

    //    @GetMapping(value = "/album")
//    public List<AlbumSummary> searchAll() {
//        return musicService.printAllInfo();
//    }

    @GetMapping(value = "/album/{signerName}/{albumTitle}/{format}")
    public ResponseEntity<List<AlbumSummary>> getAlbum(@PathVariable(name = "signerName") String signerName,
                                                 @PathVariable(name = "albumTitle") String albumTitle,
                                                       @PathVariable(name = "format") String responseFormat) {
        List<AlbumSummary> summaryList = musicService.obtaineAlbumThroughName(signerName, albumTitle, responseFormat);
        return new ResponseEntity<>(summaryList, HttpStatus.OK);
//        return musicService.obtaineAlbumThroughName(albumTitle);
    }

//    @GetMapping("/download/{fileName:.+}")
//    public ResponseEntity downloadWordDocument(@PathVariable String fileName,
//                                               @RequestParam(name = "albumTitle") String albumTitle) {
//        Path path = Paths.get("name of folder and next sub-folder with file" + fileName);
//        byte[] doc = null;
//        try {
//            doc = converterService.saveAlbum(albumTitle).readAllBytes();
//        } catch (Exception e) {
//            LOGGER.error("Something went wrong", e);
//        }
//        return ResponseEntity.ok().body(doc);
//    }

}
