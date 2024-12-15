package com.socialtripper.restapi.dto.messages;

import org.springframework.http.HttpStatus;

/**
 * Data transfer object dla błędu.
 *
 * @param code kod HTTP błędu
 * @param status status błędu
 * @param message wiadomość
 */
public record ErrorDTO(int code, HttpStatus status, String message) {
}
