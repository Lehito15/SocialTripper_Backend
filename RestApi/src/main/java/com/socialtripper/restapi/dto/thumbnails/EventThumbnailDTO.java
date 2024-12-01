package com.socialtripper.restapi.dto.thumbnails;

import com.socialtripper.restapi.dto.entities.EventActivityDTO;
import com.socialtripper.restapi.dto.entities.EventLanguageDTO;
import com.socialtripper.restapi.dto.entities.EventStatusDTO;
import java.util.Set;
import java.util.UUID;

public record EventThumbnailDTO(UUID uuid, String name, String description, int numberOfParticipants,
                                String homePageUrl, EventStatusDTO eventStatusDTO, String iconUrl,
                                Set<EventActivityDTO> eventActivitiesDTO, Set<EventLanguageDTO> eventLanguagesDTO) {
}
