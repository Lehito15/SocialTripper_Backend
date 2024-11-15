package com.socialtripper.restapi.dto.entities;

import java.math.BigDecimal;

public record EventActivityDTO (BigDecimal requiredExperience,
                                ActivityDTO activity) {
}
