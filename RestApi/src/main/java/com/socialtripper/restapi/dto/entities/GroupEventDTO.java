package com.socialtripper.restapi.dto.entities;

import java.util.UUID;

/**
 * Data transfer object dla encji wydarzenia grupowego.
 *
 * @param groupUUID globalny, unikalny identyfikator grupy w systemie
 * @param eventDTO wydarzenie - {@link EventDTO}
 */
public record GroupEventDTO (UUID groupUUID, EventDTO eventDTO) {
}
