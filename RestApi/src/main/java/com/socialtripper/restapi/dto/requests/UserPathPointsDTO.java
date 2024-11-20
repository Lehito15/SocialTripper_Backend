package com.socialtripper.restapi.dto.requests;

import java.util.List;
import java.util.UUID;

public record UserPathPointsDTO(UUID userUUID, UUID eventUUID, List<List<Double>> pathPoints) {
}
