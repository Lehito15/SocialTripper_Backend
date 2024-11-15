package com.socialtripper.restapi.dto.entities;

import java.math.BigDecimal;

public record UserLanguageDTO(BigDecimal level, LanguageDTO language) {
}
