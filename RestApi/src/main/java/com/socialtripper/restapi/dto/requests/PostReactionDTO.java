package com.socialtripper.restapi.dto.requests;

import java.util.UUID;

public record PostReactionDTO(UUID userUUID, UUID postUUID) {
}
