package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

public record UserJoinsGroupMessageDTO(UUID userUUID, UUID groupUUID, String message) {
}
