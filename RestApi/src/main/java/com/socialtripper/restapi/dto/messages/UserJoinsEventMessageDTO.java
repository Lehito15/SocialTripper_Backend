package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

/**
 * Data transfer object dla wiadomości dołączenia użytkownika do wydarzenia.
 *
 * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
 * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
 * @param message wiadomość
 */
public record UserJoinsEventMessageDTO(UUID userUUID, UUID eventUUID, String message) {
}
