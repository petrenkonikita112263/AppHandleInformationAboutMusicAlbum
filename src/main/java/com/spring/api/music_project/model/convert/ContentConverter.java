package com.spring.api.music_project.model.convert;

import com.spring.api.music_project.model.AlbumSummary;
import com.spring.api.music_project.model.PosterImage;
import com.spring.api.music_project.model.Tags;
import com.spring.api.music_project.model.Tracks;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * This class marks as component - helps bean-searching.
 * Implements interface Converter<S, T> allow us to specify this class
 * for converting the Entity Base Attribute to another List<AlbumSummary> type.
 */
@Component
@Log4j2
public class ContentConverter implements Converter<String, List<AlbumSummary>> {

    /**
     * Implementing method convert, that allow us to parse json document.
     *
     * @param urlAddress link that stores json response answer about album
     * @return since were parsing album information in json, we're returning
     * the list of this album
     */
    @Override
    public List<AlbumSummary> convert(String urlAddress) {
        RestTemplate restTemplate = new RestTemplate();
        String responseAnswer = restTemplate.getForObject(urlAddress, String.class);
        List<AlbumSummary> summaryList = new ArrayList<>();
        List<PosterImage> imageList = new ArrayList<>();
        List<Tracks> tracksList = new ArrayList<>();
        List<Tags> tagsList = new ArrayList<>();
        try {
            JSONObject rootObject = new JSONObject(responseAnswer);
            JSONObject jsonAlbum = null;
            if (rootObject.isNull("album")) {
                try {
                    return summaryList;
                } catch (NullPointerException e) {
                    log.error("Nothing exists with that name", e);
                }
            }
            if (rootObject.has("album")) {
                jsonAlbum = rootObject.getJSONObject("album");
            }
            AlbumSummary newAlbum = new AlbumSummary();
            String albumName = null;
            if (jsonAlbum != null && jsonAlbum.has("name")) {
                albumName = jsonAlbum.getString("name");
            }
            String artistName = null;
            if (jsonAlbum != null && jsonAlbum.has("artist")) {
                artistName = jsonAlbum.getString("artist");
            }
            newAlbum.setAlbumTitle(albumName);
            newAlbum.setArtistName(artistName);
            JSONArray poster = jsonAlbum.getJSONArray("image");
            for (int j = 0; j < poster.length(); j++) {
                JSONObject jsonPoster = poster.getJSONObject(j);
                PosterImage newPoster = new PosterImage();
                String urlStorage = jsonPoster.getString("#text");
                String scalePicture = jsonPoster.getString("size");
                newPoster.setStorageUrl(urlStorage);
                newPoster.setSizeFormat(scalePicture);
                imageList.add(newPoster);
            }
            JSONObject jsonTrack = jsonAlbum.getJSONObject("tracks");
            JSONArray jsonArrayTracks = jsonTrack.getJSONArray("track");
            for (int k = 0; k < jsonArrayTracks.length(); k++) {
                JSONObject newJsonTrack = jsonArrayTracks.getJSONObject(k);
                Tracks newTrack = new Tracks();
                String trackName = newJsonTrack.getString("name");
                String trackDuration = newJsonTrack.getString("duration");
                newTrack.setTrackName(trackName);
                newTrack.setDuration(trackDuration);
                tracksList.add(newTrack);
            }
            newAlbum.setListOfPosters(imageList);
            newAlbum.setCollectionOfTracks(tracksList);
            JSONObject jsonTag = jsonAlbum.getJSONObject("tags");
            JSONArray jsonArrayTag = jsonTag.getJSONArray("tag");
            for (int l = 0; l < jsonArrayTag.length(); l++) {
                JSONObject newJsonTag = jsonArrayTag.getJSONObject(l);
                Tags newTag = new Tags();
                String name = newJsonTag.getString("name");
                newTag.setName(name);
                tagsList.add(newTag);
            }
            newAlbum.setTags(tagsList);
            summaryList.add(newAlbum);
        } catch (JSONException e) {
            log.error("Something went wrong, "
                    + "can't value for object or object's not found", e);
        }
        return summaryList;
    }
}
