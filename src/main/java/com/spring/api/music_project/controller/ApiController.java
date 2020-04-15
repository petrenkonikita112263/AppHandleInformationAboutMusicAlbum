package com.spring.api.music_project.controller;

import com.spring.api.music_project.model.AlbumSummary;
import com.spring.api.music_project.model.service.IMusicService;
import com.spring.api.music_project.model.service.Savable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This calss is REST controller, where written implementation of
 * logic processing client's requests.
 */
@RestController
public class ApiController {

    protected IMusicService musicService;
    protected Savable albumStorage;

    public ApiController(IMusicService musicService, Savable albumStorage) {
        this.musicService = musicService;
        this.albumStorage = albumStorage;
    }

    /**
     * Method that handles information through GET request on specific address.
     *
     * @param signerName - send signer name or band bane
     * @param albumTitle - send name of the album
     * @return - return the response (answer) in list
     */
    @GetMapping(value = "/album/{signerName}/{albumTitle}")
    public ResponseEntity<List<AlbumSummary>> getAlbum(@PathVariable(name = "signerName") String signerName,
                                                       @PathVariable(name = "albumTitle") String albumTitle) {
        List<AlbumSummary> summaryList = musicService.obtaineAlbumThroughName(signerName, albumTitle);
        return new ResponseEntity<>(summaryList, HttpStatus.OK);
    }

    /**
     * Asynchronic method that handles information through GET request on specific address.
     *
     * @param signerName - send signer name or band bane
     * @param albumTitle - send name of the album
     * @return - return the response (answer) in list
     * @throws ExecutionException   - got error during retrieve the result from ExecutorService
     * @throws InterruptedException - got error when some of the threads were interrupted
     */
    @GetMapping(value = "/async/album/{signerName}/{albumTitle}")
    public ResponseEntity<List<AlbumSummary>> getAsynchAlbum(@PathVariable(name = "signerName") String signerName,
                                                             @PathVariable(name = "albumTitle") String albumTitle)
            throws ExecutionException, InterruptedException {
        List<AlbumSummary> summaryList = musicService.obtaineAsyncAlbumThroughName(signerName, albumTitle);
        return new ResponseEntity<>(summaryList, HttpStatus.OK);
    }

    /**
     * Method that handles information through GET request on specific address.
     *
     * @param signerName - send signer name or band bane
     * @param albumTitle - send name of the album
     * @return - return generated word document with the content in byte
     */
    @GetMapping("download/album/{signerName}/{albumTitle}")
    public ResponseEntity<byte[]> downloadWordDocument(@PathVariable(name = "signerName") String signerName,
                                                       @PathVariable(name = "albumTitle") String albumTitle) {
        byte[] document = albumStorage.saveAlbum(signerName, albumTitle).toByteArray();
        ;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename = Collections_Of_Albums.docx")
                .contentLength(document.length)
                .body(document);
    }

}
