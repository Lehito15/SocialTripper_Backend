package com.socialtripper.restapi.dto.thumbnails;

import com.socialtripper.restapi.dto.entities.EventActivityDTO;
import com.socialtripper.restapi.dto.entities.EventLanguageDTO;
import com.socialtripper.restapi.dto.entities.EventStatusDTO;
import com.socialtripper.restapi.dto.entities.MultimediaDTO;

import java.util.Set;
import java.util.UUID;

public record EventThumbnailDTO(UUID uuid, String description, int numberOfParticipants,
                                String homePageUrl, EventStatusDTO eventStatusDTO, MultimediaDTO iconDTO,
                                Set<EventActivityDTO> eventActivitiesDTO, Set<EventLanguageDTO> eventLanguagesDTO) {
}
