package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

public record UserJoinsEventMessageDTO(UUID userUUID, UUID eventUUID, String message) {
}
