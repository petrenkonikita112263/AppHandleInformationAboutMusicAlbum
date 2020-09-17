package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.AlbumSummary;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * Class marked as service, that declare that this class represents a service -
 * a component of a service layer.
 * Also perform operations on user's requests. Caching is enabled.
 */
@Service
@Log4j2
@CacheConfig(cacheNames = {"summaryList"})
public class MusicService implements Musicable {

    /**
     * Private final field with instance that helps build URL in Spring.
     */
    private final UriComponentsBuilder uriComponentsBuilder;

    /**
     * Private field with instance of ConversionService that allows us
     * hook up our customer converter.
     */
    private final ConversionService conversionService;

    /**
     * Private integer field of quantity of threads.
     */
    private final int threadItem;

    /**
     * Constructor with arguments.
     *
     * @param key               holds apiKey from properties file
     * @param conversionService instance of ConversionService
     * @param threadItem        holds quantity of threads from properties file
     */
    public MusicService(@Value("${api_key}") String key,
                        ConversionService conversionService,
                        @Value("${quantityOfThreads}") int threadItem) {
        this.uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl("http://ws.audioscrobbler.com/2.0/?method=album.getinfo")
                .queryParam("api_key", key);
        this.conversionService = conversionService;
        this.threadItem = threadItem;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AlbumSummary> obtaineAsyncAlbumThroughName(String nameOfArtist, String titleOfAlbum)
            throws ExecutionException, InterruptedException {
        String urlLink = uriComponentsBuilder.cloneBuilder()
                .queryParam("format", "json")
                .queryParam("artist", nameOfArtist)
                .queryParam("album", titleOfAlbum)
                .build().toString();
        return performAsynchronicity(urlLink);
    }

    /**
     * Additional method that sends url after asynchoronization to converter.
     *
     * @param urlLink build url that based on user's request
     * @return after converting brings back list of album in json or xml response
     * @throws ExecutionException   got error during retrieve the result from ExecutorService
     * @throws InterruptedException got error when some of the threads were interrupted
     */
    private List<AlbumSummary> performAsynchronicity(String urlLink) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadItem);
        CompletionService<List<AlbumSummary>> completionService
                = new ExecutorCompletionService<>(executorService);
        Future<List<AlbumSummary>> listFuture = completionService
                .submit(() -> conversionService.convert(urlLink, List.class));
        if (listFuture.isCancelled()) {
            closeResource(listFuture, executorService);
        }
        while (!listFuture.isDone()) {
            log.info("The future isn't done at "
                    + LocalDateTime.now());
            sleep(1000);
        }
        log.info("The future has completed (done) at " + LocalDateTime.now());
        return listFuture.get();
    }

    /**
     * Additional method that stops Future and shutdown ExecutorService.
     *
     * @param listFuture      instance of Future
     * @param executorService instance of ExecutorService
     */
    private void closeResource(Future listFuture, ExecutorService executorService) {
        listFuture.cancel(true);
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(2000, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("One of the thread was interrupted while waiting, "
                    + "so all threads will close forcibly", e);
            executorService.shutdownNow();
        }
    }

}
