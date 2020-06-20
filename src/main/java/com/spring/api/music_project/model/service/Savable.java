package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.AlbumSummary;

import java.util.List;

/**
 * Interface class for saving content of the album into docx document.
 */
public interface Savable {

    /**
     * Method that created docx file with the content of information.
     *
     * @param summaryList - reads information about list of album and
     *                    writes it to the docx document
     * @return - stream of data that was sent to the buffer
     */
    byte[] createTemplateDocument(List<AlbumSummary> summaryList);

    /**
     * Method that save album into stream.
     *
     * @param nameOfArtist - string value with the name of the band|signer
     * @param titleOfAlbum - string value with the name of the album
     * @return - sent buffer data to the stream
     */
    byte[] saveAlbum(String nameOfArtist, String titleOfAlbum);

}
