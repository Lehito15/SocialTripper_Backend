package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventDTO;
import com.socialtripper.restapi.entities.Event;
import com.socialtripper.restapi.entities.Multimedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    private EventStatusMapper eventStatusMapper;
    private RelationMapper relationMapper;
    private AccountThumbnailMapper accountThumbnailMapper;
    private MultimediaMapper multimediaMapper;
    private EventActivityMapper eventActivityMapper;
    private EventLanguageMapper eventLanguageMapper;

    @Autowired
    public void setEventActivityMapper(EventActivityMapper eventActivityMapper) {
        this.eventActivityMapper = eventActivityMapper;
    }

    @Autowired
    public void setEventLanguageMapper(EventLanguageMapper eventLanguageMapper) {
        this.eventLanguageMapper = eventLanguageMapper;
    }

    @Autowired
    public void setEventStatusMapper(EventStatusMapper eventStatusMapper) {
        this.eventStatusMapper = eventStatusMapper;
    }

    @Autowired
    public void setRelationMapper(RelationMapper relationMapper) {
        this.relationMapper = relationMapper;
    }

    @Autowired
    public void setAccountThumbnailMapper(AccountThumbnailMapper accountThumbnailMapper) {
        this.accountThumbnailMapper = accountThumbnailMapper;
    }

    @Autowired
    public void setMultimediaMapper(MultimediaMapper multimediaMapper) {
        this.multimediaMapper = multimediaMapper;
    }

    public Event toEntity(EventDTO eventDTO) {
        if (eventDTO == null) return null;

        Multimedia eventIcon = eventDTO.icon() == null ? null : new Multimedia(UUID.randomUUID(),
                eventDTO.icon().url(),
                eventDTO.icon().mimeType(),
                eventDTO.icon().mimeSubtype());

        return new Event(
                eventDTO.uuid(),
                eventDTO.description(),
                eventDTO.rules(),
                eventDTO.isPublic(),
                eventDTO.dateOfCreation(),
                eventDTO.eventStartTime(),
                eventDTO.eventEndTime(),
                eventDTO.numberOfParticipants(),
                eventDTO.actualNumberOfParticipants(),
                eventDTO.maxNumberOfParticipants(),
                eventDTO.startLongitude(),
                eventDTO.startLatitude(),
                eventDTO.stopLongitude(),
                eventDTO.stopLatitude(),
                eventDTO.destinationLongitude(),
                eventDTO.destinationLatitude(),
                eventDTO.homePageUrl(),
                null,
                null,
                null,
                eventIcon,
                new HashSet<>(),
                new HashSet<>());
    }

    public EventDTO toDTO(Event event) {
        if (event == null) return null;
        return new EventDTO(
                event.getUuid(),
                event.getDescription(),
                event.getRules(),
                event.getIsPublic(),
                event.getDateOfCreation(),
                event.getEventStartTime(),
                event.getEventEndTime(),
                event.getNumberOfParticipants(),
                event.getActualNumberOfParticipants(),
                event.getMaxNumberOfParticipants(),
                event.getStartLongitude(),
                event.getStartLatitude(),
                event.getStopLongitude(),
                event.getStopLatitude(),
                event.getDestinationLongitude(),
                event.getDestinationLatitude(),
                event.getHomePageUrl(),
                eventStatusMapper.toDTO(event.getEventStatus()),
                relationMapper.toDTO(event.getRelation()),
                accountThumbnailMapper.toDTO(event.getOwner()),
                multimediaMapper.toDTO(event.getIcon()),
                event.getEventActivities().stream().map(eventActivityMapper::toDTO).collect(Collectors.toSet()),
                event.getEventLanguages().stream().map(eventLanguageMapper::toDTO).collect(Collectors.toSet())
        );
    }

    public Event copyNonNullValues(Event event, EventDTO eventDTO) {
        if (eventDTO == null) return null;
        if (eventDTO.uuid() != null) event.setUuid(eventDTO.uuid());
        if (eventDTO.description() != null) event.setDescription(eventDTO.description());
        if (eventDTO.rules() != null) event.setRules(eventDTO.rules());
        if (eventDTO.isPublic() != null) event.setIsPublic(eventDTO.isPublic());
        if (eventDTO.eventStartTime() != null) event.setEventStartTime(eventDTO.eventStartTime());
        if (eventDTO.eventEndTime() != null) event.setEventEndTime(eventDTO.eventEndTime());
        if (eventDTO.numberOfParticipants() != null) event.setNumberOfParticipants(eventDTO.numberOfParticipants());
        if (eventDTO.actualNumberOfParticipants() != null) event.setActualNumberOfParticipants(eventDTO.actualNumberOfParticipants());
        if (eventDTO.maxNumberOfParticipants() != null) event.setMaxNumberOfParticipants(eventDTO.maxNumberOfParticipants());
        if (eventDTO.startLongitude() != null) event.setStartLatitude(eventDTO.startLongitude());
        if (eventDTO.startLatitude() != null) event.setStartLatitude(eventDTO.startLatitude());
        if (eventDTO.stopLongitude() != null) event.setStopLatitude(eventDTO.stopLongitude());
        if (eventDTO.stopLatitude() != null) event.setStopLatitude(eventDTO.stopLatitude());
        if (eventDTO.destinationLongitude() != null) event.setDestinationLatitude(eventDTO.destinationLongitude());
        if (eventDTO.destinationLatitude() != null) event.setDestinationLatitude(eventDTO.destinationLatitude());
        if (eventDTO.homePageUrl() != null) event.setHomePageUrl(eventDTO.homePageUrl());
        return event;
    }
}
