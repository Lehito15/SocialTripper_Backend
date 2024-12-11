package com.socialtripper.restapi.dto.requests;

import com.socialtripper.restapi.dto.entities.PointDTO;

import java.util.List;
import java.util.UUID;

/**
 * Data transfer object dla współrzędnych geograficznych odwiedzonych przez użytkownika
 * w ramach wydarzenia.
 * @param userUUID globalny, unikalny identyfikator użytkownika w systemie
 * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
 * @param pathPoints lista punktów geograficznych odwiedzonych przez użytkownika - {@link PointDTO}
 */
public record UserPathPointsDTO(UUID userUUID, UUID eventUUID, List<PointDTO> pathPoints) {
}
