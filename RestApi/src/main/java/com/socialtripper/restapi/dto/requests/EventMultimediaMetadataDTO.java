package com.socialtripper.restapi.dto.requests;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventMultimediaMetadataDTO(String multimediaUrl, double latitude, double longitude,
                                         LocalDateTime timestamp, UUID userUUID, UUID eventUUID) {
}
