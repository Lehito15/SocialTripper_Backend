package com.socialtripper.restapi.dto.requests;

import java.util.UUID;

/**
 * Data transfer object dla relacji polubienia postu przez użytkownika.
 *
 * @param userUUID globalny, unikalny identyfikator użytkownika w systemie
 * @param postUUID globalny, unikalny identyfikator postu w systemie
 */
public record PostReactionDTO(UUID userUUID, UUID postUUID) {
}
