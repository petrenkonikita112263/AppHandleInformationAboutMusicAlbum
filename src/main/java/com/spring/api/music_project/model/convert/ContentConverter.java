package com.spring.api.music_project.model.convert;

import com.spring.api.music_project.model.AlbumSummary;
import com.spring.api.music_project.model.PosterImage;
import com.spring.api.music_project.model.Tracks;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContentConverter implements Converter<String, List<AlbumSummary>> {

    private static final Logger LOGGER = Logger.getLogger(ContentConverter.class);

    @Override
    public List<AlbumSummary> convert(String urlAddress) {
        RestTemplate restTemplate = new RestTemplate();
        String responseAnswer = restTemplate.getForObject(urlAddress, String.class);
        List<AlbumSummary> summaryList = new ArrayList<>();
        List<PosterImage> imageList = new ArrayList<>();
        List<Tracks> tracksList = new ArrayList<>();
        try {
            JSONObject rootObject = new JSONObject(responseAnswer);
            JSONObject jsonAlbum = null;
            if (rootObject.has("album")) {
                jsonAlbum = rootObject.getJSONObject("album");
            }
                AlbumSummary newAlbum = new AlbumSummary();
                String albumName = null;
                if (jsonAlbum.has("name")) {
                    albumName = jsonAlbum.getString("name");
                }
                String artistName = null;
                if (jsonAlbum.has("artist")) {
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
                summaryList.add(newAlbum);
        } catch (JSONException e) {
            LOGGER.error("Something went wrong, "
                    + "can't value for object or object's not found", e);
        }
        return summaryList;
    }
}
