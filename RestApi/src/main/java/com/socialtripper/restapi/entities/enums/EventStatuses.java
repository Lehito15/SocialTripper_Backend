package com.socialtripper.restapi.entities.enums;

import lombok.Getter;

import java.util.Optional;

/**
 * Enumerator z nazwami statusów w systemie.
 */
@Getter
public enum EventStatuses {
    /**
     * Utworzone wydarzenie.
     */
    CREATED(1L, "created"),
    /**
     * t
     * Trwające wydarzenie.
     */
    IN_PROGRESS(2L, "in progress"),
    /**
     * Zakończone wydarzenie.
     */
    FINISHED(3L, "finished"),
    /**
     * Odwołane wydarzenie.
     */
    CANCELLED(4L, "cancelled");

    /**
     * Kod statutsu wydarzenie pokrywający się z id z bazy danych.
     */
    private final Long code;
    /**
     * Nazwa statusu.
     */
    private final String status;

    /**
     *
     * @param code kod statusu wydarzenia
     * @param status nazwa statusu wydarzenia
     */
    EventStatuses(Long code, String status) {
        this.code = code;
        this.status = status;
    }

    /**
     * Metoda zwracająca enumerator na podstawie nazwy statusu.
     *
     * @param status nazwa statusu
     * @return Optional z enumeratorem lub pusty Optional
     */
    public static Optional<EventStatuses> fromStatus(String status) {
        for (EventStatuses eventStatus : EventStatuses.values()) {
            if (eventStatus.status.equalsIgnoreCase(status)) return Optional.of(eventStatus);
        }
        return Optional.empty();
    }
}
