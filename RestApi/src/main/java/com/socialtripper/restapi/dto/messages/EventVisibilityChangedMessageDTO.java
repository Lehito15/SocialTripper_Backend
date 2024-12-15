package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

/**
 * Data transfer object dla wiadomości zmiany widoczności wydarzenia.
 *
 * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
 * @param isPublic informacja o widoczności wydarzenia (publiczne / prywatne)
 */
public record EventVisibilityChangedMessageDTO(UUID uuid, boolean isPublic) {}
