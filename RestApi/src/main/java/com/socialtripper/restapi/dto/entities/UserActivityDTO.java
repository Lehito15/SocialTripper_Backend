package com.socialtripper.restapi.dto.entities;

import java.math.BigDecimal;

/**
 * Data transfer object dla encji aktywności użytkownika.
 *
 * @param experience doświadczenie użytkownika
 * @param activity aktywność - {@link ActivityDTO}
 */
public record UserActivityDTO(BigDecimal experience, ActivityDTO activity) {
}
