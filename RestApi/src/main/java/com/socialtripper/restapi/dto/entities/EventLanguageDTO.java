package com.socialtripper.restapi.dto.entities;

import java.math.BigDecimal;

public record EventLanguageDTO (Long id, BigDecimal requiredLevel, EventDTO eventDTO,
                                LanguageDTO languageDTO) {
}
