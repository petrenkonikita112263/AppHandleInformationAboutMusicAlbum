package com.spring.api.music_project.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(includeFieldNames = true)
public class Tags {

    private String name;

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
