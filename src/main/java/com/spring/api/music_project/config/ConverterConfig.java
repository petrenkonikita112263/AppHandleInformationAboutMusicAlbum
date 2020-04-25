package com.spring.api.music_project.config;

import com.spring.api.music_project.model.convert.ContentConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class defined as configuration and creates custom converter.
 */
@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ContentConverter());
    }
}
