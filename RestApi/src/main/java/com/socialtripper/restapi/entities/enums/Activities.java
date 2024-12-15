package com.socialtripper.restapi.entities.enums;

import lombok.Getter;

import java.util.Optional;

/**
 * Enumerator typów aktywności w systemie.
 */
@Getter
public enum Activities {
    /**
     * Sporty wodne.
     */
    WATER(1L, "water"),
    /**
     * Aktywności związane z chodzeniem.
     */
    WALKING(2L, "walking"),
    /**
     * Wydarzenia sportowe.
     */
    SPORT(3L, "sport"),
    /**
     * Aktywności związane z bieganiem.
     */
    RUNNING(4L, "running"),
    /**
     * Aktywności związane z jazdą.
     */
    RIDE(5L, "ride"),
    /**
     * Aktywności górskie.
     */
    HIKING(6L, "hiking"),
    /**
     * Kemping.
     */
    CAMPING(7L, "camping");

    /**
     * Identyfikator aktywności pokrywający się z id w bazie danych.
     */
    private final Long id;
    /**
     * Nazwa aktywności.
     */
    private final String name;

    /**
     *
     * @param id identyfikator aktywności w bazie
     * @param name nazwa aktywności
     */
    Activities(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Metoda zwracająca enumerator na podstawie nazwy aktywności.
     *
     * @param name nazwa aktywności
     * @return Optional z enumeratorem lub pusty Optional.
     */
    public static Optional<Activities> fromActivityName(String name) {
        for (Activities activity : Activities.values()) {
            if (activity.name.equalsIgnoreCase(name)) return Optional.of(activity);
        }
        return Optional.empty();
    }
}
