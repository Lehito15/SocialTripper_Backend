package com.socialtripper.restapi.dto.entities;

import java.math.BigDecimal;

/**
 * Data transfer object dla encji języka użytkownika.
 *
 * @param level poziom znajomości języka
 * @param language język - {@link LanguageDTO}
 */
public record UserLanguageDTO(BigDecimal level, LanguageDTO language) {
}
