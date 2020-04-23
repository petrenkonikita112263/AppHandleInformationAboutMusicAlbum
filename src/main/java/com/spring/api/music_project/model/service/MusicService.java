package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.AlbumSummary;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;

/**
 * This class is Service, that holds implementation of business logic application.
 * Perform operations on user's requests. Also has functionality of caching.
 */
@Service
@CacheConfig(cacheNames = {"summaryList"})
public class MusicService implements IMusicService {

    private static final Logger LOGGER = Logger.getLogger(MusicService.class);

    private final UriComponentsBuilder uriComponentsBuilder;

    private ConversionService conversionService;

    private int threadItem;

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
     * Implements method that builds url based on user's request and sends it to converter.
     * if the request repeats, it works with cache-copy.
     *
     * @param nameOfArtist - signer name or band bane
     * @param titleOfAlbum - name of the album
     * @return - after converting brings back list of album in json or xml response
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
     * Implements method that builds url based on user's request and sends it to converter.
     * if the request repeats, it works with cache-copy.
     *
     * @param nameOfArtist - signer name or band bane
     * @param titleOfAlbum - name of the album
     * @return - send build url to private method
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
     * @param urlLink - build url that based on user's request
     * @return - after converting brings back list of album in json or xml response
     * @throws ExecutionException   - got error during retrieve the result from ExecutorService
     * @throws InterruptedException - got error when some of the threads were interrupted
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
            LOGGER.info("The future isn't done at "
                    + LocalDateTime.now());
            Thread.sleep(1000);
        }
        LOGGER.info("The future has completed (done) at " + LocalDateTime.now());
        return listFuture.get();
    }

    /**
     * Additional method that stops Future and shutdown ExecutorService.
     *
     * @param listFuture      - instance of Future
     * @param executorService - instance of ExecutorService
     */
    private void closeResource(Future listFuture, ExecutorService executorService) {
        listFuture.cancel(true);
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(2000, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            LOGGER.error("One of the thread was interrupted while waiting, "
                    + "so all threads will close forcibly", e);
            executorService.shutdownNow();
        }
    }

}
