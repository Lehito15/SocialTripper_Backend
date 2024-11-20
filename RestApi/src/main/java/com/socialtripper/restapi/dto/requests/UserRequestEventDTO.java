package com.socialtripper.restapi.dto.requests;

import java.util.UUID;

public record UserRequestEventDTO (UUID userUUID, UUID eventUUID, String message){
}
