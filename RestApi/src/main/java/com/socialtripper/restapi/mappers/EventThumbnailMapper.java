package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventDTO;
import com.socialtripper.restapi.dto.thumbnails.EventThumbnailDTO;
import com.socialtripper.restapi.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EventThumbnailMapper {
    private final EventStatusMapper eventStatusMapper;
    private final EventActivityMapper eventActivityMapper;
    private final EventLanguageMapper eventLanguageMapper;

    @Autowired
    public EventThumbnailMapper(EventStatusMapper eventStatusMapper, EventActivityMapper eventActivityMapper,
                                EventLanguageMapper eventLanguageMapper) {
        this.eventStatusMapper = eventStatusMapper;
        this.eventActivityMapper = eventActivityMapper;
        this.eventLanguageMapper = eventLanguageMapper;
    }

    public EventThumbnailDTO toDTO(Event event) {
        if (event == null) return null;
        return new EventThumbnailDTO(
                event.getUuid(),
                event.getDescription(),
                event.getNumberOfParticipants(),
                event.getHomePageUrl(),
                eventStatusMapper.toDTO(event.getEventStatus()),
                event.getIconUrl(),
                event.getEventActivities().stream().map(eventActivityMapper::toDTO).collect(Collectors.toSet()),
                event.getEventLanguages().stream().map(eventLanguageMapper::toDTO).collect(Collectors.toSet())
        );
    }

    public EventThumbnailDTO toDTO(EventDTO eventDTO) {
        if (eventDTO == null) return null;
        return new EventThumbnailDTO(
                eventDTO.uuid(),
                eventDTO.description(),
                eventDTO.numberOfParticipants(),
                eventDTO.homePageUrl(),
                eventDTO.eventStatus(),
                eventDTO.iconUrl(),
                eventDTO.activities(),
                eventDTO.languages()
        );
    }
}
