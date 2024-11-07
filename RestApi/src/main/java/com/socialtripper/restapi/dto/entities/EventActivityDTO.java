package com.socialtripper.restapi.dto.entities;

import java.math.BigDecimal;

public record EventActivityDTO (Long id, BigDecimal requiredExperience, EventDTO eventDTO,
                                ActivityDTO activityDTO) {
}
