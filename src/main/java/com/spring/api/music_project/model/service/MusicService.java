package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.AlbumSummary;
import com.spring.api.music_project.model.convert.ContentConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class MusicService implements IMusicService {

    private static final Logger LOGGER = Logger.getLogger(MusicService.class);

    private final UriComponentsBuilder uriComponentsBuilder;

    private ContentConverter converter;

//    @Autowired
    private ConversionService conversionService;

        public MusicService(@Value("${APIkey}") String key,
                            ConversionService conversionService) {
        this.uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("http://ws.audioscrobbler.com/2.0/")
                .queryParam("APIkey", key);
        this.conversionService = conversionService;
    }

    @Override
    public List<AlbumSummary> printAllInfo() {
        return null;
    }

    @Override
    public List<AlbumSummary> obtaineAlbumThroughName(String nameOfArtist, String titleOfAlbum) {
        String urlLink = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=a222715dd6f605cdd9278e2c0accae39&artist"
                + "=" + nameOfArtist + "&album=" + titleOfAlbum + "&format=json";
//       String urlLink = uriComponentsBuilder.cloneBuilder().queryParam("artist",
//               nameOfArtist).queryParam("album", titleOfAlbum).build().toString();
       return conversionService.convert(urlLink, List.class);
    }

}
