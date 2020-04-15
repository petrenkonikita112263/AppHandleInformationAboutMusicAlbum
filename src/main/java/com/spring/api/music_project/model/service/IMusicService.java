package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.AlbumSummary;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Interface with business logic work of application.
 */
public interface IMusicService {

    /**
     * Method that builds url based on user's request and sends it to converter.
     * if the request repeats, it works with cache-copy.
     *
     * @param nameOfArtist - signer name or band bane
     * @param titleOfAlbum - name of the album
     * @return - after converting brings back list of album in json or xml response
     */
    List<AlbumSummary> obtaineAlbumThroughName(String nameOfArtist, String titleOfAlbum);

    /**
     * Method that builds url based on user's request and sends it to converter.
     * if the request repeats, it works with cache-copy.
     *
     * @param nameOfArtist - signer name or band bane
     * @param titleOfAlbum - name of the album
     * @return - send build url to private method
     */
    List<AlbumSummary> obtaineAsyncAlbumThroughName(String nameOfArtist, String titleOfAlbum) throws ExecutionException, InterruptedException;

}
