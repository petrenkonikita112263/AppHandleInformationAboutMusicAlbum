package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.AlbumSummary;
import com.spring.api.music_project.model.convert.ContentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ConverterService {


    @Bean
    public ConversionService conversionService() {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new ContentConverter());
        return service;
    }


//    public void doConvert() {
//        AlbumSummary album = conversionService.convert("ok", AlbumSummary.class);
//    }
//
//    public ByteArrayOutputStream saveContentToWord(List<AlbumSummary> albumList) {
//        return null;
//    }
//
//    public ByteArrayInputStream saveAlbum(String urlLink) {
//        return null;
//    }





}
