package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

/**
 * Data transfer object dla wiadomości ustawienia języka dla wydarzenia.
 *
 * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
 * @param language nazwa języka
 */
public record EventLanguageSetMessageDTO(UUID eventUUID, String language) {
}
