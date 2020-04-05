package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.convert.ContentConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {

    @Bean
    public ConversionService conversionService() {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new ContentConverter());
        return service;
    }
}
