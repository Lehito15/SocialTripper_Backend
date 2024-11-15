package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

public record GroupLanguageSetMessageDTO(UUID groupUUID, String language) {
}
