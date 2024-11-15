package com.socialtripper.restapi.dto.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record UserDTO(UUID uuid, String name, String surname,
                      Character gender, LocalDate dateOfBirth, BigDecimal weight,
                      BigDecimal height, BigDecimal physicality, AccountDTO account,
                      CountryDTO country, Set<UserLanguageDTO> languages, Set<UserActivityDTO> activities
) {
}
