package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Data transfer object dla encji wydarzenia.
 *
 * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
 * @param name nazwa wydarzenia
 * @param destination celu wydarzenia słownie
 * @param description opis wydarzenia
 * @param rules zasady obowiązujące uczestników wydarzenia
 * @param isPublic informacja o dostępności konta (publiczne / prywatne)
 * @param dateOfCreation data utworzenie wydarzenia
 * @param eventStartTime czas rozpoczęcia wydarzenia
 * @param eventEndTime czas zakończenia wydarzenia
 * @param numberOfParticipants aktualna liczba uczestników wydarzenia
 * @param maxNumberOfParticipants maksymalna liczba uczestników wydarzenia
 * @param startLongitude długość geograficzna miejsca rozpoczęcia wydarzenia
 * @param startLatitude szerokośc geograficzna miejsca rozpoczęcia wydarzenia
 * @param stopLongitude długość geograficzna miejsca zakończenia wydarzenia
 * @param stopLatitude szerkość geograficzna miejsca zakończenia wydarzenia
 * @param homePageUrl dostęp do zasobu wydarzenia
 * @param eventStatus status wydarzenia - {@link EventStatusDTO}
 * @param owner właściciel wydarzenia - {@link AccountThumbnailDTO}
 * @param iconUrl link do ikony wydarzenia
 * @param activities aktywności przypisane do wydarzenia - {@link ActivityDTO}
 * @param languages języki przypisane do wydarzenia - {@link LanguageDTO}
 */
public record EventDTO(UUID uuid, String name, String destination, String description, String rules,
                       Boolean isPublic, LocalDate dateOfCreation, LocalDateTime eventStartTime, LocalDateTime eventEndTime,
                       Integer numberOfParticipants, Integer maxNumberOfParticipants, BigDecimal startLongitude,
                       BigDecimal startLatitude, BigDecimal stopLongitude, BigDecimal stopLatitude,
                       String homePageUrl, EventStatusDTO eventStatus, AccountThumbnailDTO owner,
                       String iconUrl, Set<EventActivityDTO> activities, Set<EventLanguageDTO> languages) {
}
