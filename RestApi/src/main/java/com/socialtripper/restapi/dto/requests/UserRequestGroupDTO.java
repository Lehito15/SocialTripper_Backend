package com.socialtripper.restapi.dto.requests;

import java.util.UUID;

public record UserRequestGroupDTO (UUID userUUID, UUID groupUUID, String message) {
}
