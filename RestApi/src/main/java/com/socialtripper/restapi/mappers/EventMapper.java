package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventDTO;
import com.socialtripper.restapi.entities.Event;
import com.socialtripper.restapi.nodes.EventNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją wydarzenia {@link Event},
 * data transfer object wydarzenia {@link EventDTO} oraz węzłem {@link EventNode}.
 */
@Component
public class EventMapper {
    /**
     * Komponent odpowiedzialny za mapowanie statusu wydarzenia.
     */
    private EventStatusMapper eventStatusMapper;
    /**
     * Komponent odpowiedzialny za mapowanie częściowych informacji o koncie użytkownika.
     */
    private AccountThumbnailMapper accountThumbnailMapper;
    /**
     * Komponent odpowiedzialny za mapowanie aktywności w wydarzeniu.
     */
    private EventActivityMapper eventActivityMapper;
    /**
     * Komponent odpowiedzialny za mapowanie języka w wydarzeniu.
     */
    private EventLanguageMapper eventLanguageMapper;

    /**
     * Setter wstrzykujący komponent mapujący aktywność w wydarzeniu.
     * @param eventActivityMapper komponent mapujący aktywność w wydarzeniu
     */
    @Autowired
    public void setEventActivityMapper(EventActivityMapper eventActivityMapper) {
        this.eventActivityMapper = eventActivityMapper;
    }

    /**
     * Setter wstrzykujący komponent mapujący język w wydarzeniu.
     * @param eventLanguageMapper komponent mapujący język w wydarzeniu
     */
    @Autowired
    public void setEventLanguageMapper(EventLanguageMapper eventLanguageMapper) {
        this.eventLanguageMapper = eventLanguageMapper;
    }

    /**
     * Setter wstrzykujący komponent mapujący status wydarzenia.
     * @param eventStatusMapper komponent mapujący status wydarzenia
     */
    @Autowired
    public void setEventStatusMapper(EventStatusMapper eventStatusMapper) {
        this.eventStatusMapper = eventStatusMapper;
    }

    /**
     * Setter wstrzykujący komponent mapujący częściowe informacje o koncie użytkownika.
     * @param accountThumbnailMapper komponent mapujący częściowe informacje o koncie użytkownika
     */
    @Autowired
    public void setAccountThumbnailMapper(AccountThumbnailMapper accountThumbnailMapper) {
        this.accountThumbnailMapper = accountThumbnailMapper;
    }

    /**
     * Konstruktor tworzący encję dla nowego wydarzenia.
     *
     * @param eventDTO data transfer object wydarzenia
     * @return encja wydarzenia
     */
    public Event toNewEntity(EventDTO eventDTO) {
        if (eventDTO == null) return null;
        return new Event(
                eventDTO.uuid(),
                eventDTO.name(),
                eventDTO.destination(),
                eventDTO.description(),
                eventDTO.rules(),
                eventDTO.isPublic(),
                eventDTO.dateOfCreation(),
                eventDTO.eventStartTime(),
                eventDTO.eventEndTime(),
                0,
                eventDTO.maxNumberOfParticipants(),
                eventDTO.startLongitude(),
                eventDTO.startLatitude(),
                eventDTO.stopLongitude(),
                eventDTO.stopLatitude(),
                null,
                null,
                null,
                null,
                new HashSet<>(),
                new HashSet<>());
    }

    /**
     * Metoda mapująca encję oraz węzeł wydarzenia na data transfer object.
     *
     * @param event encja wydarzenia
     * @param eventNode węzeł wydarzenia
     * @return data transfer object wydarzenia
     */
    public EventDTO toDTO(Event event, EventNode eventNode) {
        if (event == null) return null;
        return new EventDTO(
                event.getUuid(),
                event.getName(),
                event.getDestination(),
                event.getDescription(),
                event.getRules(),
                event.getIsPublic(),
                event.getDateOfCreation(),
                event.getEventStartTime(),
                event.getEventEndTime(),
                eventNode.getMembers().size(),
                event.getMaxNumberOfParticipants(),
                event.getStartLongitude(),
                event.getStartLatitude(),
                event.getStopLongitude(),
                event.getStopLatitude(),
                event.getHomePageUrl(),
                eventStatusMapper.toDTO(event.getEventStatus()),
                accountThumbnailMapper.toDTO(event.getOwner()),
                event.getIconUrl(),
                event.getEventActivities().stream().map(eventActivityMapper::toDTO).collect(Collectors.toSet()),
                event.getEventLanguages().stream().map(eventLanguageMapper::toDTO).collect(Collectors.toSet())
        );
    }

    /**
     * Metoda mapująca encję wydarzenia na węzeł.
     *
     * @param event encja wydarzenia
     * @return węzeł wydarzenia
     */
    public EventNode toNode(Event event) {
        if (event == null) return null;
        return new EventNode(
                event.getUuid(),
                event.getName()
        );
    }

    /**
     * Metoda kopiująca pola data transfer object niezwiązane z innymi encjami do obiektu encji.
     *
     * @param event obiekt encji, do której mają zostać przekopiowane wartości pól
     * @param eventDTO data transfer object, z którego kopiowane są wartości do encji
     * @return encja wydarzenia
     */
    public Event copyNonNullValues(Event event, EventDTO eventDTO) {
        if (eventDTO == null) return null;
        if (eventDTO.uuid() != null) event.setUuid(eventDTO.uuid());
        if (eventDTO.description() != null) event.setDescription(eventDTO.description());
        if (eventDTO.rules() != null) event.setRules(eventDTO.rules());
        if (eventDTO.isPublic() != null) event.setIsPublic(eventDTO.isPublic());
        if (eventDTO.eventStartTime() != null) event.setEventStartTime(eventDTO.eventStartTime());
        if (eventDTO.eventEndTime() != null) event.setEventEndTime(eventDTO.eventEndTime());
        if (eventDTO.numberOfParticipants() != null) event.setNumberOfParticipants(eventDTO.numberOfParticipants());
        if (eventDTO.maxNumberOfParticipants() != null) event.setMaxNumberOfParticipants(eventDTO.maxNumberOfParticipants());
        if (eventDTO.startLongitude() != null) event.setStartLongitude(eventDTO.startLongitude());
        if (eventDTO.startLatitude() != null) event.setStartLatitude(eventDTO.startLatitude());
        if (eventDTO.stopLongitude() != null) event.setStopLongitude(eventDTO.stopLongitude());
        if (eventDTO.stopLatitude() != null) event.setStopLatitude(eventDTO.stopLatitude());
        if (eventDTO.homePageUrl() != null) event.setHomePageUrl(eventDTO.homePageUrl());
        return event;
    }
}
