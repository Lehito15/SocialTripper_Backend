package com.socialtripper.restapi.dto.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * Data transfer object dla encji użytkownika.
 *
 * @param uuid globalny, unikalny identyfikator użytkownika w systemie
 * @param name imię
 * @param surname nazwisko
 * @param gender płeć
 * @param dateOfBirth data urodzenia
 * @param weight waga
 * @param height wzrost
 * @param bmi bmi
 * @param physicality ocena poziomu fizyczności
 * @param account konto przypisane do użytkownika - {@link AccountDTO}
 * @param country kraj pochodzenia - {@link CountryDTO}
 * @param languages języki użytkownika - {@link UserLanguageDTO}
 * @param activities aktywności użytkownika - {@link UserActivityDTO}
 */
public record UserDTO(UUID uuid, String name, String surname,
                      Character gender, LocalDate dateOfBirth, BigDecimal weight,
                      BigDecimal height, BigDecimal bmi, BigDecimal physicality, AccountDTO account,
                      CountryDTO country, Set<UserLanguageDTO> languages, Set<UserActivityDTO> activities
) {
}
