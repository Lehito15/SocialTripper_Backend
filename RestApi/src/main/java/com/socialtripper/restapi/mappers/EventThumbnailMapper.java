package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventDTO;
import com.socialtripper.restapi.dto.thumbnails.EventThumbnailDTO;
import com.socialtripper.restapi.entities.Event;
import com.socialtripper.restapi.nodes.EventNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją wydarzenia {@link Event},
 * węzełem wydarzenia {@link EventNode}, data transfer object wydarzenia {@link EventDTO}
 * a data transfer object częściowej informacji o wydarzeniu {@link EventThumbnailDTO}.
 */
@Component
public class EventThumbnailMapper {
    /**
     * Komponent odpowiedzialny za mapowanie statusu wydarzenia.
     */
    private final EventStatusMapper eventStatusMapper;
    /**
     * Komponent odpowiedzialny za mapowanie aktywności w wydarzeniu.
     */
    private final EventActivityMapper eventActivityMapper;
    /**
     * Komponent odpowiedzialny za mapowanie języka w wydarzeniu.
     */
    private final EventLanguageMapper eventLanguageMapper;

    /**
     * Konstruktor wstrzykujący komponenty mapujące.
     *
     * @param eventStatusMapper komponent odpowiedzialny za mapowanie statusu wydarzenia
     * @param eventActivityMapper komponent odpowiedzialny za mapowanie aktywności w wydarzeniu
     * @param eventLanguageMapper komponent odpowiedzialny za mapowanie języka w wydarzeniu
     */
    @Autowired
    public EventThumbnailMapper(EventStatusMapper eventStatusMapper, EventActivityMapper eventActivityMapper,
                                EventLanguageMapper eventLanguageMapper) {
        this.eventStatusMapper = eventStatusMapper;
        this.eventActivityMapper = eventActivityMapper;
        this.eventLanguageMapper = eventLanguageMapper;
    }

    /**
     * Metoda mapująca encję oraz węzeł wydarzenia na data transfer object.
     *
     * @param event encja wydarzenia
     * @param eventNode węzeł wydarzenia
     * @return data transfer object częściowej informacji o wydarzeniu
     */
    public EventThumbnailDTO toDTO(Event event, EventNode eventNode) {
        if (event == null) return null;
        return new EventThumbnailDTO(
                event.getUuid(),
                event.getName(),
                event.getDescription(),
                eventNode.getMembers().size(),
                event.getHomePageUrl(),
                eventStatusMapper.toDTO(event.getEventStatus()),
                event.getIconUrl(),
                event.getEventActivities().stream().map(eventActivityMapper::toDTO).collect(Collectors.toSet()),
                event.getEventLanguages().stream().map(eventLanguageMapper::toDTO).collect(Collectors.toSet())
        );
    }

    /**
     * Metoda mapująca data transfer object wydarzenia na data transfer object
     * częściowej informacji o wydarzeniu.
     *
     * @param eventDTO data transfer object wydarzenia
     * @return data transfer object częściowej informacji o wydarzeniu
     */
    public EventThumbnailDTO toDTO(EventDTO eventDTO) {
        if (eventDTO == null) return null;
        return new EventThumbnailDTO(
                eventDTO.uuid(),
                eventDTO.name(),
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
