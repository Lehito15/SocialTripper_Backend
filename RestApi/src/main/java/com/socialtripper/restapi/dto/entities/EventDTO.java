package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record EventDTO(UUID uuid, String name, String destination, String description, String rules,
                       Boolean isPublic, LocalDate dateOfCreation, LocalDateTime eventStartTime, LocalDateTime eventEndTime, Integer numberOfParticipants,
                       Integer actualNumberOfParticipants, Integer maxNumberOfParticipants, BigDecimal startLongitude,
                       BigDecimal startLatitude, BigDecimal stopLongitude, BigDecimal stopLatitude,
                       BigDecimal destinationLongitude, BigDecimal destinationLatitude, String homePageUrl,
                       EventStatusDTO eventStatus, AccountThumbnailDTO owner,
                       String iconUrl, Set<EventActivityDTO> activities, Set<EventLanguageDTO> languages) {
}
