package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

public record EventVisibilityChangedMessageDTO(UUID uuid, boolean isPublic) {}
