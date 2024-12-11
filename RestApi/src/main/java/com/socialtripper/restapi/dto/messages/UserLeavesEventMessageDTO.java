package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

/**
 * Data transfer object dla wiadomości opuszczenia wydarzenia przez użytkownika.
 *
 * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
 * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
 * @param message wiadomość
 */
public record UserLeavesEventMessageDTO(UUID userUUID, UUID eventUUID, String message) {
}
