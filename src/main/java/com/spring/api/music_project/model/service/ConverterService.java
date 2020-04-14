package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.convert.ContentConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

/**
 * This class defined as service and creates custom converter.
 */
@Service
public class ConverterService {

    /**
     * Non-arg constructor marks as simple component.
     * Registry our custom converter with DefaultConversionService.
     *
     * @return - get object of service with added converter
     */
    @Bean
    public ConversionService conversionService() {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new ContentConverter());
        return service;
    }
}
