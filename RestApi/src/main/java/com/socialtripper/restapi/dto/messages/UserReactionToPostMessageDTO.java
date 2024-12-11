package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

/**
 * Data transfer object dla wiadomości reakcji użytkownika na post.
 *
 * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
 * @param postUUID globalny, unikalny identyfikator postu w systemie
 * @param message wiadomość
 */
public record UserReactionToPostMessageDTO (UUID userUUID, UUID postUUID, String message) {
}
