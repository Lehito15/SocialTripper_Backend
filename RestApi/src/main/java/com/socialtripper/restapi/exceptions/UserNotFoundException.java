package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Account with given uuid not found.")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID uuid) {
        super("User with given uuid not found: " + uuid);
    }
}
