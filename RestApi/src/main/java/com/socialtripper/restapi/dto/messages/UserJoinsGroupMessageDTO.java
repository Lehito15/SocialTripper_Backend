package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

/**
 * Data transfer object dla wiadomości dołączenia użytkownika do grupy.
 *
 * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
 * @param groupUUID globalny, unikalny identyfikator grupy w systemie
 * @param message wiadomość
 */
public record UserJoinsGroupMessageDTO(UUID userUUID, UUID groupUUID, String message) {
}
