package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

public record GroupActivitySetMessageDTO(UUID groupUUID, String activityName) {
}
