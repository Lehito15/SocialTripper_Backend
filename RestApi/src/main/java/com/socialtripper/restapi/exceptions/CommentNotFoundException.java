package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Comment with given uuid not found.")
public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(UUID uuid) {
        super("Comment with given uuid not found: " + uuid);
    }
}
