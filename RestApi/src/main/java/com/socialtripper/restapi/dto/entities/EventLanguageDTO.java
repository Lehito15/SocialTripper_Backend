package com.socialtripper.restapi.dto.entities;

import java.math.BigDecimal;

public record EventLanguageDTO (BigDecimal requiredLevel,
                                LanguageDTO language) {
}
