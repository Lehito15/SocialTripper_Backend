package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

public record UserReactionToCommentMessageDTO(UUID userUUID, UUID commentUUID, String message) {
}
