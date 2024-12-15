package com.socialtripper.restapi.dto.requests;

import java.util.UUID;

/**
 * Data transfer object dla relacji wysłania prośby o obserwajcę.
 *
 * @param followerUUID globalny, unikalny identyfikator użytkownika obserwującego w systemie
 * @param followedUUID globalny, unikalny identyfikator użytkownika obserwowanego w systemie
 * @param message wiadomość
 */
public record UserRequestFollowDTO(UUID followerUUID, UUID followedUUID, String message) {
}
