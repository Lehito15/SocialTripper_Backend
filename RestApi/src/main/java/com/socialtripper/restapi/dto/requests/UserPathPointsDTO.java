package com.socialtripper.restapi.dto.requests;

import com.socialtripper.restapi.dto.entities.PointDTO;

import java.util.List;
import java.util.UUID;

public record UserPathPointsDTO(UUID userUUID, UUID eventUUID, List<PointDTO> pathPoints) {
}
