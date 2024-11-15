package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

public record UserEndsFollowingMessageDTO(UUID followerUUID, UUID followedUUID, String messgae) {
}
