package com.socialtripper.restapi.entities.enums;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum LocationScopes {
    CITY(1L, "city"),
    COUNTRY(2L, "country");

    private final Long id;
    private final String name;

    LocationScopes(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Optional<LocationScopes> fromScope(String scope) {
        for (LocationScopes locationScope : LocationScopes.values()) {
            if (locationScope.name.equalsIgnoreCase(scope)) return Optional.of(locationScope);
        }
        return Optional.empty();
    }
}
