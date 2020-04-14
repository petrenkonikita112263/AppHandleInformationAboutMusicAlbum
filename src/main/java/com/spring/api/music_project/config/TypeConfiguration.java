package com.spring.api.music_project.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class that implements interface WebMvcConfigurer,
 * and implemented one method and helps REST Controller
 * return JSON or XML.
 */
@Configuration
@EnableWebMvc
@EnableCaching
public class TypeConfiguration implements WebMvcConfigurer {

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

}
