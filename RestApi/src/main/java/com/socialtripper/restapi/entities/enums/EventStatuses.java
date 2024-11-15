package com.socialtripper.restapi.entities.enums;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum EventStatuses {
    CREATED(1L, "created"),
    IN_PROGRESS(2L, "in progress"),
    FINISHED(3L, "finished"),
    CANCELLED(4L, "cancelled");

    private final Long code;
    private final String status;

    EventStatuses(Long code, String status) {
        this.code = code;
        this.status = status;
    }

    public static Optional<EventStatuses> fromStatus(String status) {
        for (EventStatuses eventStatus : EventStatuses.values()) {
            if (eventStatus.status.equalsIgnoreCase(status)) return Optional.of(eventStatus);
        }
        return Optional.empty();
    }
}
