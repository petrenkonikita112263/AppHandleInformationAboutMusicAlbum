package com.spring.api.music_project.model.convert;

import com.spring.api.music_project.model.AlbumSummary;
import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContentConverter implements Converter<String, List<AlbumSummary>> {

    private static final Logger LOGGER = Logger.getLogger(ContentConverter.class);

    private List<AlbumSummary> albumList = new ArrayList<>();

    @Override
    public List<AlbumSummary> convert(String s) {
        return null;
    }
}
