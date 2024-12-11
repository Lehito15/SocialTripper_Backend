package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

/**
 * Data transfer object dla wiadomości ustawienia aktywności grupowej.
 *
 * @param groupUUID globalny, unikalny identyfikator grupy w systemie
 * @param activityName nazwa aktywności
 */
public record GroupActivitySetMessageDTO(UUID groupUUID, String activityName) {
}
