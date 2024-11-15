package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

public record EventActivitySetMessageDTO(UUID eventUUID, String activityName) {
}
