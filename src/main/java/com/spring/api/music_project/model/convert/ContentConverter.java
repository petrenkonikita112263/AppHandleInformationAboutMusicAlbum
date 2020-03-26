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
//            JSONArray album = rootObject.getJSONArray("album");
//            for (int i = 0; i < album.length(); i++) {
//
////                json data of album
//                JSONObject jsonAlbum = album.getJSONObject(i);

//                newAlbum object that will create AlbumSummary


//                take one more array
                JSONArray poster = rootObject.getJSONArray("image");
                for (int j = 0; j < poster.length(); j++) {
                    JSONObject jsonPoster = poster.getJSONObject(j);
                    PosterImage newPoster = new PosterImage();
                    String urlStorage = jsonPoster.getString("#text");
                    String scalePicture = jsonPoster.getString("size");
                    newPoster.setStorageUrl(urlStorage);
                    newPoster.setSizeFormat(scalePicture);
                    imageList.add(newPoster);
                }

//                take last array
                JSONArray track = rootObject.getJSONArray("tracks");
                for (int k = 0; k < track.length(); k++) {
                    JSONObject jsonTrack = track.getJSONObject(k);
                    Tracks newTrack = new Tracks();
                    String trackName = jsonTrack.getString("name");
                    String trackDuration = jsonTrack.getString("duration");
                    newTrack.setTrackName(trackName);
                    newTrack.setDuration(trackDuration);
                    tracksList.add(newTrack);
                }

            AlbumSummary newAlbum = new AlbumSummary();
            String albumName = rootObject.getString("name");
            String artistName = rootObject.getString("artist");

//                setting
            newAlbum.setAlbumTitle(albumName);
            newAlbum.setArtistName(artistName);

                newAlbum.setListOfPosters(imageList);
                newAlbum.setCollectionOfTracks(tracksList);
                summaryList.add(newAlbum);

        } catch (JSONException e) {
            LOGGER.error("Text text text .....", e);
        }
        return summaryList;
    }
}
