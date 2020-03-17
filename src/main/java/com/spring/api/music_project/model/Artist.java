package com.spring.api.music_project.model;

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
}
