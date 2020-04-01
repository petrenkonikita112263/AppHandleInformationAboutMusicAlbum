package com.spring.api.music_project.model;

import java.util.Objects;

public class PosterImage {

    private String storageUrl;
    private String sizeFormat;

    public PosterImage() {
    }

    public PosterImage(String storageUrl, String sizeFormat) {
        this.storageUrl = storageUrl;
        this.sizeFormat = sizeFormat;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    public String getSizeFormat() {
        return sizeFormat;
    }

    public void setSizeFormat(String sizeFormat) {
        this.sizeFormat = sizeFormat;
    }

    @Override
    public String toString() {
        return "PosterImage{"
                + "text='" + storageUrl + '\''
                + ", size='" + sizeFormat + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if ((o == null) || (getClass() != o.getClass())) {
            return false;
        } else {
            PosterImage objectPoster = (PosterImage) o;
            return Objects.equals(storageUrl, objectPoster.storageUrl)
                    && Objects.equals(sizeFormat, objectPoster.sizeFormat);
        }
    }

    @Override
    public int hashCode() {
        int primaryNumber = 214;
        int result = primaryNumber * Objects.hash(storageUrl, sizeFormat);
        return result;
    }
}
