package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Event with given uuid not found.")
public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(UUID uuid) {
        super("Event with given uuid not found: " + uuid);
    }
}
