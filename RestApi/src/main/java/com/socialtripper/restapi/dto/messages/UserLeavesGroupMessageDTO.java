package com.socialtripper.restapi.dto.messages;


import java.util.UUID;

public record UserLeavesGroupMessageDTO (UUID userUUID, UUID groupUUID, String message){
}
