package com.socialtripper.restapi.dto.messages;

import org.springframework.http.HttpStatus;

public record ErrorDTO(int code, HttpStatus status, String message) {
}
