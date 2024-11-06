package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventDTO;
import com.socialtripper.restapi.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EventMapper {
    private EventStatusMapper eventStatusMapper;
    private RelationMapper relationMapper;
    private AccountMapper accountMapper;
    private MultimediaMapper multimediaMapper;
    private EventActivityMapper eventActivityMapper;
    private EventLanguageMapper eventLanguageMapper;

    @Autowired
    public void setMultimediaMapper(MultimediaMapper multimediaMapper) {
        this.multimediaMapper = multimediaMapper;
    }

    @Autowired
    public void setEventActivityMapper(EventActivityMapper eventActivityMapper) {
        this.eventActivityMapper = eventActivityMapper;
    }

    @Autowired
    public void setEventLanguageMapper(EventLanguageMapper eventLanguageMapper) {
        this.eventLanguageMapper = eventLanguageMapper;
    }

    @Autowired
    public void setAccountMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Autowired
    public void setEventStatusMapper(EventStatusMapper eventStatusMapper) {
        this.eventStatusMapper = eventStatusMapper;
    }

    @Autowired
    public void setRelationMapper(RelationMapper relationMapper) {
        this.relationMapper = relationMapper;
    }

    public Event toEntity(EventDTO eventDTO) {
        if (eventDTO == null) return null;
        return new Event(
                eventDTO.uuid(),
                eventDTO.description(),
                eventDTO.rules(),
                eventDTO.isPublic(),
                eventDTO.dateOfCreation(),
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
                eventStatusMapper.toEntity(eventDTO.eventStatusDTO()),
                relationMapper.toEntity(eventDTO.relationDTO()),
                accountMapper.toEntity(eventDTO.ownerDTO()),
                multimediaMapper.toEntity(eventDTO.iconDTO()),
                eventDTO.eventActivitiesDTO().stream().map(eventActivityMapper::toEntity).collect(Collectors.toSet()),
                eventDTO.eventLanguagesDTO().stream().map(eventLanguageMapper::toEntity).collect(Collectors.toSet()));
    }

    public EventDTO toDTO(Event event) {
        if (event == null) return null;
        return new EventDTO(
                event.getUuid(),
                event.getDescription(),
                event.getRules(),
                event.getIsPublic(),
                event.getDateOfCreation(),
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
                accountMapper.toDTO(event.getOwner()),
                multimediaMapper.toDTO(event.getIcon()),
                event.getEventActivities().stream().map(eventActivityMapper::toDTO).collect(Collectors.toSet()),
                event.getEventLanguages().stream().map(eventLanguageMapper::toDTO).collect(Collectors.toSet())
        );
    }
}
