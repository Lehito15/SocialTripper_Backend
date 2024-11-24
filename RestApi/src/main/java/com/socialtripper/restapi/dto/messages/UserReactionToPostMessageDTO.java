package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

public record UserReactionToPostMessageDTO (UUID userUUID, UUID postUUID, String message) {
}
