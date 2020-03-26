package com.spring.api.music_project.model;

import java.util.Objects;

public class Tracks {

    private String trackName;
    private String duration;

    public Tracks() {
    }

    public Tracks(String trackName, String duration) {
        this.trackName = trackName;
        this.duration = duration;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Tracks{"
                + "name='" + trackName + '\''
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
                Objects.equals(trackName, tracks.trackName);
        }
    }

    @Override
    public int hashCode() {
        int primaryNumber = 545;
        int result = primaryNumber * Objects.hash(trackName, duration);
        return result;
    }
}
