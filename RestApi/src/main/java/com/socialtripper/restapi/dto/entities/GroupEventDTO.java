package com.socialtripper.restapi.dto.entities;

import java.util.UUID;

public record GroupEventDTO (UUID groupUUID, EventDTO eventDTO) {
}
