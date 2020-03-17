package com.spring.api.music_project.model;

import java.util.Objects;

public class PosterImage {

    private String text;
    private String size;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PosterImage{"
                + "text='" + text + '\''
                + ", size='" + size + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            PosterImage objectPoster = (PosterImage) o;
            return Objects.equals(text, objectPoster.text)
                    && Objects.equals(size, objectPoster.size);
        }
    }

    @Override
    public int hashCode() {
        int primaryNumber = 214;
        int result = primaryNumber * Objects.hash(text, size);
        return result;
    }
}
