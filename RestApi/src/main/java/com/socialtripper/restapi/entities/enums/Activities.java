package com.socialtripper.restapi.entities.enums;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum Activities {
    WATER(1L, "water"),
    WALKING(2L, "walking"),
    SPORT(3L, "sport"),
    RUNNING(4L, "running"),
    RIDE(5L, "ride"),
    HIKING(6L, "hiking"),
    CYCLING(7L, "camping");

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
