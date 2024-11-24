package com.socialtripper.restapi.dto.requests;

import java.util.UUID;

public record UserRequestFollowDTO(UUID followerUUID, UUID followedUUID, String message) {
}
