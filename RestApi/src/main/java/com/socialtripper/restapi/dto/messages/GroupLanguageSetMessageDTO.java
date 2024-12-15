package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

/**
 * Data transfer object dla wiadomości ustawienia języka dla grupy.
 *
 * @param groupUUID globalny, unikalny identyfikator grupy w systemie
 * @param language nazwa języka
 */
public record GroupLanguageSetMessageDTO(UUID groupUUID, String language) {
}
