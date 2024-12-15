package com.socialtripper.restapi.dto.entities;

import java.math.BigDecimal;

/**
 * Data transfer object dla encji aktywności w wydarzeniu.
 *
 * @param requiredExperience wymagana doświadczenie
 * @param activity aktywność - {@link ActivityDTO}
 */
public record EventActivityDTO (BigDecimal requiredExperience,
                                ActivityDTO activity) {
}
