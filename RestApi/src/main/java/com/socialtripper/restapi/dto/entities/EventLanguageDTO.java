package com.socialtripper.restapi.dto.entities;

import java.math.BigDecimal;

/**
 * Data transfer object dla encji języka przypisanego do wydarzenia.
 *
 * @param requiredLevel wymagany poziom znajomości
 * @param language język - {@link LanguageDTO}
 */
public record EventLanguageDTO (BigDecimal requiredLevel,
                                LanguageDTO language) {
}
