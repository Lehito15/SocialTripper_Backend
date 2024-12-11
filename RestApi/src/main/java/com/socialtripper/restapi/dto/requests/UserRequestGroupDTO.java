package com.socialtripper.restapi.dto.requests;

import java.util.UUID;

/**
 * Data transfer object dla relacji wysłania prośby o dołączenie do grupy.
 *
 * @param userUUID globalny, unikalny identyfikator użytkownika w systemie
 * @param groupUUID globalny, unikalny identyfikator grupy w systemie
 * @param message wiadomość
 */
public record UserRequestGroupDTO (UUID userUUID, UUID groupUUID, String message) {
}
