package com.spring.api.music_project.model;

import java.util.Objects;

public class Artist {

    private String name;
    private Tracks tracksName;

    public Artist() {
    }

    public Artist(String name, Tracks tracksName) {
        this.name = name;
        this.tracksName = tracksName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tracks getTracksName() {
        return tracksName;
    }

    public void setTracksName(Tracks tracksName) {
        this.tracksName = tracksName;
    }

    @Override
    public String toString() {
        return "Artist{"
                + "name='" + name + '\''
                + ", tracksName=" + tracksName
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            Artist artist = (Artist) o;
            return Objects.equals(name, artist.name)
                    && Objects.equals(tracksName, artist.tracksName);
        }
    }

    @Override
    public int hashCode() {
        int primaryNumber = 109;
        int result = primaryNumber * Objects.hash(name, tracksName);
        return result;
    }
}
