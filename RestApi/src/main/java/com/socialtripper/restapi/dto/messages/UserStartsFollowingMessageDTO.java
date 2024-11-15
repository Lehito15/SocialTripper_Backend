package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

public record UserStartsFollowingMessageDTO (UUID followerUUID, UUID followedUUID, String message){
}
