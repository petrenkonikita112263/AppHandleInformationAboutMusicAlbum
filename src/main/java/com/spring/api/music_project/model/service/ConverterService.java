package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.AlbumSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {

    @Autowired
    ConversionService conversionService;

    public void doConvert() {
        AlbumSummary album = conversionService.convert("ok", AlbumSummary.class);
    }


}
