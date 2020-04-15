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

    private List<AlbumSummary> performAsynchronicity(String urlLink) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadItem);
        CompletionService<List<AlbumSummary>> completionService
                = new ExecutorCompletionService<>(executorService);
        Future<List<AlbumSummary>> listFuture = completionService
                .submit(() -> conversionService.convert(urlLink, List.class));
        if (listFuture.isCancelled()) {
            closeResource(listFuture, executorService);
        }
//        try {
//            return listFuture.get();
//            closeResource(listFuture, executorService);
//        } catch (InterruptedException e) {
//            LOGGER.error("One of the thread was interrupted while waiting", e);
//        } catch (ExecutionException e) {
//            LOGGER.error("Failure in executor service, can't retrieve the result", e);
//        }
        while (!listFuture.isDone()) {
            System.out.println(
                    String.format(
                            "The future is %s",
                            listFuture.isDone() ? "done" : "not done")
                            + " at " + LocalDateTime.now());
            Thread.sleep(1000);
        }
        System.out.println("The future has completed (done) at " + LocalDateTime.now());
        return listFuture.get();
    }

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
