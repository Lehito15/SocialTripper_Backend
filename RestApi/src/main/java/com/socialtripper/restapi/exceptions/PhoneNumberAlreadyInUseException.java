package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Phone number is already associated with another account.")
public class PhoneNumberAlreadyInUseException extends RuntimeException {
    public PhoneNumberAlreadyInUseException() {
        super("Phone number is already in use");
    }
}
