package com.spring.api.music_project.config;

import com.spring.api.music_project.model.convert.ContentConverter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class is one of the Configuration's classes, enable MVC and enable caching.
 * Also implements interface WebMvcConfigurer and implemented one method
 * that helps REST Controller return JSON or XML.
 */
@Configuration
@EnableWebMvc
@EnableCaching
public class AppConfig implements WebMvcConfigurer {

    /**
     * Implementing of method from interface, that set default content type
     * as JSON with favorParameter and parameterName.
     *
     * @param configurer - object of ContentNegotiationConfigurer class
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true).
                favorParameter(true).
                parameterName("format").
                ignoreAcceptHeader(true).
                useJaf(false).
                defaultContentType(MediaType.APPLICATION_JSON).
                mediaType("xml", MediaType.APPLICATION_XML).
                mediaType("json", MediaType.APPLICATION_JSON);
    }

    /**
     * Method that tells Spring about custom converter.
     * @param registry - object which wrapped the converter*/
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ContentConverter());
    }

}
