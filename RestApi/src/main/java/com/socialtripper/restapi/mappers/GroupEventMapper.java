package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.GroupEventDTO;
import com.socialtripper.restapi.entities.GroupEvent;
import com.socialtripper.restapi.nodes.EventNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupEventMapper {
    private final EventMapper eventMapper;

    @Autowired
    public GroupEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public GroupEvent toEntity(GroupEventDTO groupEventDTO) {
        return new GroupEvent(
               eventMapper.toNewEntity(groupEventDTO.eventDTO())
        );
    }

    public GroupEventDTO toDTO(GroupEvent groupEvent, EventNode eventNode) {
        return new GroupEventDTO(
                groupEvent.getEvent().getUuid(),
                eventMapper.toDTO(groupEvent.getEvent(), eventNode)
        );
    }
}
