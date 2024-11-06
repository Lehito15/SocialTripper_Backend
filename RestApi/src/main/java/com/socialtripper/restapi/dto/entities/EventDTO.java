package com.socialtripper.restapi.dto.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record EventDTO(UUID uuid, String description, String rules,
                       Boolean isPublic, LocalDate dateOfCreation, int numberOfParticipants,
                       int actualNumberOfParticipants, int maxNumberOfParticipants, BigDecimal startLongitude,
                       BigDecimal startLatitude, BigDecimal stopLongitude, BigDecimal stopLatitude,
                       BigDecimal destinationLongitude, BigDecimal destinationLatitude, String homePageUrl,
                       EventStatusDTO eventStatusDTO, RelationDTO relationDTO, AccountDTO ownerDTO, MultimediaDTO iconDTO,
                       Set<EventActivityDTO> eventActivitiesDTO, Set<EventLanguageDTO> eventLanguagesDTO) {
}
