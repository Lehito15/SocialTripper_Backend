package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

public record UserLeavesEventMessageDTO(UUID userUUID, UUID eventUUID, String message) {
}
