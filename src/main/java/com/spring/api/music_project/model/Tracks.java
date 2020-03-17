package com.spring.api.music_project.model;

public class Tracks {

    private String name;
    private int duration;

    public Tracks() {
    }

    public Tracks(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
