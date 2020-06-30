package com.spring.api.music_project.controller;

import com.spring.api.music_project.model.AlbumSummary;
import com.spring.api.music_project.model.service.Musicable;
import com.spring.api.music_project.model.service.Savable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This class is REST controller, where written implementation of
 * logic processing client's requests uses automatic data conversion
 * from JAVA format to JSON (by default).
 */
@RestController
public class ApiController {

    /**
     * Private field with instance of the interface.
     */
    private Musicable musicService;
    /**
     * Private field with instance of the interface.
     */
    private Savable albumStorage;

    /**
     * Constructor with arguments.
     *
     * @param musicService instance of MusicService class
     * @param albumStorage instance of AlbumStorage class
     */
    public ApiController(Musicable musicService, Savable albumStorage) {
        this.musicService = musicService;
        this.albumStorage = albumStorage;
    }

    /**
     * Method that handles with GetMapping of the type RequestMapping that
     * listens or catches HTTP client requests and binds the address to the handler method,
     * value - describes the URL that will be processed in this controller or controller method.
     *
     * @param signerName as pathVariable allows us to enter a signer name or band bane from the URL as a parameter
     * @param albumTitle as pathVariable allows us to enter a name of the album from the URL as a parameter
     * @return return the response (answer) in list
     */
    @GetMapping(value = "/album/{signerName}/{albumTitle}")
    public ResponseEntity<List<AlbumSummary>> getAlbum(@PathVariable(name = "signerName") String signerName,
                                                       @PathVariable(name = "albumTitle") String albumTitle) {
        List<AlbumSummary> summaryList = musicService.obtaineAlbumThroughName(signerName, albumTitle);
        return new ResponseEntity<>(summaryList, HttpStatus.OK);
    }

    /**
     * Asynchronous method that handles with GetMapping of the type RequestMapping that
     * listens or catches HTTP client requests and binds the address to the handler method,
     * value - describes the URL that will be processed in this controller or controller method.
     *
     * @param signerName as pathVariable allows us to enter a signer name or band bane from the URL as a parameter
     * @param albumTitle as pathVariable allows us to enter a name of the album from the URL as a parameter
     * @return return the response (answer) in list
     * @throws ExecutionException   got error during retrieve the result from ExecutorService
     * @throws InterruptedException got error when some of the threads were interrupted
     */
    @GetMapping(value = "/async/album/{signerName}/{albumTitle}")
    public ResponseEntity<List<AlbumSummary>> getAsynchAlbum(@PathVariable(name = "signerName") String signerName,
                                                             @PathVariable(name = "albumTitle") String albumTitle)
            throws ExecutionException, InterruptedException {
        List<AlbumSummary> summaryList = musicService.obtaineAsyncAlbumThroughName(signerName, albumTitle);
        return new ResponseEntity<>(summaryList, HttpStatus.OK);
    }

    /**
     * Method that handles with GetMapping of the type RequestMapping that
     * listens or catches HTTP client requests and binds the address to the handler method,
     * value - describes the URL that will be processed in this controller or controller method.
     *
     * @param signerName as pathVariable allows us to enter a signer name or band bane from the URL as a parameter
     * @param albumTitle as pathVariable allows us to enter a name of the album from the URL as a parameter
     * @return return generated word document with the content in byte
     */
    @GetMapping("download/album/{signerName}/{albumTitle}")
    public ResponseEntity<Resource> downloadWordDocument(@PathVariable(name = "signerName") String signerName,
                                                         @PathVariable(name = "albumTitle") String albumTitle) {
        byte[] document = albumStorage.saveAlbum(signerName, albumTitle);
        ByteArrayResource resource = new ByteArrayResource(document);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename = Collections_Of_Albums.docx")
                .contentLength(document.length)
                .body(resource);
    }

}
