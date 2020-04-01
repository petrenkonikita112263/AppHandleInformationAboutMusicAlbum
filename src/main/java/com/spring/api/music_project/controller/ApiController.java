package com.spring.api.music_project.controller;

import com.spring.api.music_project.model.AlbumSummary;
import com.spring.api.music_project.model.service.ConverterService;
import com.spring.api.music_project.model.service.IMusicService;
import com.spring.api.music_project.model.service.Savable;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

    private static final Logger LOGGER = Logger.getLogger(ApiController.class);

    protected IMusicService musicService;
    protected ConverterService converterService;
    protected Savable albumStorage;

    public ApiController(IMusicService musicService, Savable albumStorage) {
        this.musicService = musicService;
        this.albumStorage = albumStorage;
    }

    //    @GetMapping(value = "/album")
//    public List<AlbumSummary> searchAll() {
//        return musicService.printAllInfo();
//    }

    @GetMapping(value = "/album/{signerName}/{albumTitle}")
    public ResponseEntity<List<AlbumSummary>> getAlbum(@PathVariable(name = "signerName") String signerName,
                                                 @PathVariable(name = "albumTitle") String albumTitle) {
        List<AlbumSummary> summaryList = musicService.obtaineAlbumThroughName(signerName, albumTitle);
        return new ResponseEntity<>(summaryList, HttpStatus.OK);
    }

//    @GetMapping("/download/{signerName}/{albumTitle}/word")
//    public ResponseEntity<byte[]> downloadWordDocument(@PathVariable(name = "signerName") String signerName,
//                                               @PathVariable(name = "albumTitle") String albumTitle) {
//        byte[] document = albumStorage.generateDoc();
//        return ResponseEntity.ok().body(document);
//    }

//    @GetMapping("/album/{signerName}/{albumTitle}/word")
//    public ResponseEntity<byte[]> downloadWordDocument(@PathVariable(name = "signerName") String signerName,
//                                                       @PathVariable(name = "albumTitle") String albumTitle) {
//        ByteArrayOutputStream baos;
//        byte[] document = albumStorage.saveAlbum(signerName, albumTitle).toByteArray();;
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename = Collections_Of_Albums.docx")
//                .contentLength(document.length)
//                .body(document);
//    }

}
