package com.socialtripper.restapi.entities.enums;

import lombok.Getter;

import java.util.Optional;

/**
 * Enumerator z zasięgami grup w systemie
 */
@Getter
public enum LocationScopes {
    /**
     * Zasięg miasta dla grupy.
     */
    CITY(1L, "city"),
    /**
     * Zasięg krajowy dla grupy.
     */
    COUNTRY(2L, "country");

    /**
     * Identyfikator zasięgu pokrywający się z id z bazy danych.
     */
    private final Long id;
    /**
     * Nazwa typu zasięgu dla grupy.
     */
    private final String name;

    /**
     *
     * @param id identyfikator zasięgu dla grupy
     * @param name nazwa typu zasięgu grupy
     */
    LocationScopes(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Metoda zwracająca enumerator na podstawie nazwy zasięgu.
     *
     * @param scope nazwa typu zasięgu
     * @return Optional z enumeratorem lub pusty Optional
     */
    public static Optional<LocationScopes> fromScope(String scope) {
        for (LocationScopes locationScope : LocationScopes.values()) {
            if (locationScope.name.equalsIgnoreCase(scope)) return Optional.of(locationScope);
        }
        return Optional.empty();
    }
}
