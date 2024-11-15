package com.socialtripper.restapi.dto.entities;

import java.math.BigDecimal;

public record UserActivityDTO(BigDecimal experience, ActivityDTO activity) {
}
