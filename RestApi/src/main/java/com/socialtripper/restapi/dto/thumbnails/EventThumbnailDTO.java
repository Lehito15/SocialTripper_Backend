package com.socialtripper.restapi.dto.thumbnails;

import com.socialtripper.restapi.dto.entities.EventActivityDTO;
import com.socialtripper.restapi.dto.entities.EventLanguageDTO;
import com.socialtripper.restapi.dto.entities.EventStatusDTO;
import java.util.Set;
import java.util.UUID;

/**
 * Data transfer object dla częściowej informacji o wydarzeniu.
 *
 * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
 * @param name nazwa wydarzenia
 * @param description opis wydarzenia
 * @param numberOfParticipants aktualna liczba uczestników wydarzenia
 * @param homePageUrl dostęp do zasobu wydarzenia
 * @param eventStatusDTO status wydarzenia - {@link EventStatusDTO}
 * @param iconUrl link do ikony wydarzenia
 * @param eventActivitiesDTO aktywności związane z wydarzeniem - {@link EventActivityDTO}
 * @param eventLanguagesDTO języki związane z wydarzeniem - {@link EventLanguageDTO}
 */
public record EventThumbnailDTO(UUID uuid, String name, String description, int numberOfParticipants,
                                String homePageUrl, EventStatusDTO eventStatusDTO, String iconUrl,
                                Set<EventActivityDTO> eventActivitiesDTO, Set<EventLanguageDTO> eventLanguagesDTO) {
}
