package com.socialtripper.restapi.dto.thumbnails;

import com.socialtripper.restapi.dto.entities.CountryDTO;
import com.socialtripper.restapi.dto.entities.UserActivityDTO;
import com.socialtripper.restapi.dto.entities.UserLanguageDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * Data transfer object dla częściowej informacji o użytkowniku.
 *
 * @param uuid globalny, unikalny identyfikator użytkownika w systemie
 * @param name imię
 * @param surname nazwisko
 * @param gender płeć
 * @param dateOfBirth data urodzenia
 * @param weight waga
 * @param height wzrost
 * @param physicality ocena fizyczności użytkownika
 * @param country kraj pochodzenia - {@link CountryDTO}
 * @param languages języki użytkownika - {@link UserLanguageDTO}
 * @param activities aktywności użytkownika - {@link UserActivityDTO}
 */
public record UserThumbnailDTO (UUID uuid, String name, String surname,
                                Character gender, LocalDate dateOfBirth, BigDecimal weight,
                                BigDecimal height, BigDecimal physicality,
                                CountryDTO country, Set<UserLanguageDTO> languages, Set<UserActivityDTO> activities
){
}
