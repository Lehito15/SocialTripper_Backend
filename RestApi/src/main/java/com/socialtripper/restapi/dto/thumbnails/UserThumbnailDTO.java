package com.socialtripper.restapi.dto.thumbnails;

import com.socialtripper.restapi.dto.entities.CountryDTO;
import com.socialtripper.restapi.dto.entities.UserActivityDTO;
import com.socialtripper.restapi.dto.entities.UserLanguageDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record UserThumbnailDTO (UUID uuid, String name, String surname,
                                Character gender, LocalDate dateOfBirth, BigDecimal weight,
                                BigDecimal height, BigDecimal physicality,
                                CountryDTO country, Set<UserLanguageDTO> languages, Set<UserActivityDTO> activities
){
}
