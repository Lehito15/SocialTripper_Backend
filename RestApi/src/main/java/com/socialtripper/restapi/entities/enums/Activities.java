package com.socialtripper.restapi.entities.enums;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum Activities {
    RUNNING(1L, "running"),
    HIKING(2L, "hiking"),
    WALKING(3L, "walking"),
    CYCLING(4L, "cycling");

    private final Long id;
    private final String name;

    Activities(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Optional<Activities> fromActivityName(String name) {
        for (Activities activity : Activities.values()) {
            if (activity.name.equalsIgnoreCase(name)) return Optional.of(activity);
        }
        return Optional.empty();
    }
}
