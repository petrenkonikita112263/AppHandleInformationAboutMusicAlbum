package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.AlbumSummary;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"summaryList"})
public class MusicService implements IMusicService {

    private static final Logger LOGGER = Logger.getLogger(MusicService.class);

    private final UriComponentsBuilder uriComponentsBuilder;

    private ConversionService conversionService;

    public MusicService(@Value("${api_key}") String key,
                        ConversionService conversionService) {
        this.uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl("http://ws.audioscrobbler.com/2.0/?method=album.getinfo")
                .queryParam("api_key", key);
        this.conversionService = conversionService;
    }

    @Override
    public List<AlbumSummary> printAllInfo() {
        return null;
    }

    @Override
    @Cacheable
    public List<AlbumSummary> obtaineAlbumThroughName(String nameOfArtist, String titleOfAlbum) {
        String urlLink = uriComponentsBuilder.cloneBuilder()
                .queryParam("format", "json")
                .queryParam("artist", nameOfArtist)
                .queryParam("album", titleOfAlbum)
                .build().toString();
        return conversionService.convert(urlLink, List.class);
    }

}
