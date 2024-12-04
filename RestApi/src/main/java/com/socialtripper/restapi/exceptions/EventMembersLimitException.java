package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Limit of members in event has been reached.")
public class EventMembersLimitException extends RuntimeException {
    public EventMembersLimitException() {super("Limit of members in event has been reached.");}
}
