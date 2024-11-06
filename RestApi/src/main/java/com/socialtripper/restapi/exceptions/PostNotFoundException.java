package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Post with given uuid not found.")
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(UUID uuid) {super("Post with given uuid not found: " + uuid);}
}
