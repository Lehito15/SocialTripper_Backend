package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

/**
 * Data transfer object dla wiadomości ustawienia aktywności dla wydarzenia.
 *
 * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
 * @param activityName nazwa aktywności
 */
public record EventActivitySetMessageDTO(UUID eventUUID, String activityName) {
}
