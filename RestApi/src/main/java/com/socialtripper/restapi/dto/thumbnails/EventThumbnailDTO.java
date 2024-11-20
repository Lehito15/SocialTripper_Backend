package com.socialtripper.restapi.dto.thumbnails;

import com.socialtripper.restapi.dto.entities.EventActivityDTO;
import com.socialtripper.restapi.dto.entities.EventLanguageDTO;
import com.socialtripper.restapi.dto.entities.EventStatusDTO;

import java.util.Set;
import java.util.UUID;

public record EventThumbnailDTO(UUID uuid, java.lang.String description, int numberOfParticipants,
                                java.lang.String homePageUrl, EventStatusDTO eventStatusDTO, String iconUrl,
                                Set<EventActivityDTO> eventActivitiesDTO, Set<EventLanguageDTO> eventLanguagesDTO) {
}
