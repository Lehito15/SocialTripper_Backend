package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.GroupEventDTO;
import com.socialtripper.restapi.entities.GroupEvent;
import com.socialtripper.restapi.nodes.EventNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją wydarzenia grupowego {@link GroupEvent},
 * a data transfer object {@link GroupEventDTO}.
 */
@Component
public class GroupEventMapper {
    /**
     * Komponent odpowiedzialny za mapowanie wydarzenia.
     */
    private final EventMapper eventMapper;

    /**
     * Konstruktor wstrzykujący komponent mapujący.
     *
     * @param eventMapper komponent odpowiedzialny za mapowanie wydarzenia
     */
    @Autowired
    public GroupEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    /**
     * Metoda mapująca data transfer object wydarzenie grupowego do encji.
     *
     * @param groupEventDTO data transfer object wydarzenia grupowego
     * @return encja wydarzenia grupowego
     */
    public GroupEvent toEntity(GroupEventDTO groupEventDTO) {
        return new GroupEvent(
               eventMapper.toNewEntity(groupEventDTO.eventDTO())
        );
    }

    /**
     * Metoda mapująca encję wydarzenia grupowego do data transfer object.
     *
     * @param groupEvent encja wydarzenia grupowego
     * @param eventNode data transfer object wydarzenia grupowego
     * @return
     */
    public GroupEventDTO toDTO(GroupEvent groupEvent, EventNode eventNode) {
        return new GroupEventDTO(
                groupEvent.getEvent().getUuid(),
                eventMapper.toDTO(groupEvent.getEvent(), eventNode)
        );
    }
}
