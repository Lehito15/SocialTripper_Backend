package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

/**
 * Data transfer object dla wiadomości dodania reakcji do komentarza.
 *
 * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
 * @param commentUUID globalny, unikalny identyfikator komentarza w systemie
 * @param message wiadomość
 */
public record UserReactionToCommentMessageDTO(UUID userUUID, UUID commentUUID, String message) {
}
