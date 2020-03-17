package com.spring.api.music_project.model;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Tracks{"
                + "name='" + name + '\''
                + ", duration=" + duration
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        else if (o == null || getClass() != o.getClass()){
            return false;
        } else {
        Tracks tracks = (Tracks) o;
        return duration == tracks.duration &&
                Objects.equals(name, tracks.name);
        }
    }

    @Override
    public int hashCode() {
        int primaryNumber = 545;
        int result = primaryNumber * Objects.hash(name, duration);
        return result;
    }
}
