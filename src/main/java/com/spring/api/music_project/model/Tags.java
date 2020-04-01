package com.spring.api.music_project.model;

import java.util.Objects;

public class Tags {

    private String name;

    public Tags() {
    }

    public Tags(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tags{" + "name='"
                + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if ((o == null) || (getClass() != o.getClass())) {
            return false;
        } else {
            Tags tags = (Tags) o;
            return Objects.equals(name, tags.name);
        }
    }

    @Override
    public int hashCode() {
        int primaryNumber = 80;
        return primaryNumber * Objects.hash(name);
    }
}
