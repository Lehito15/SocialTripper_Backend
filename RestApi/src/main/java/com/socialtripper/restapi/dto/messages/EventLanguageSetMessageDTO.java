package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

public record EventLanguageSetMessageDTO(UUID eventUUID, String language) {
}
