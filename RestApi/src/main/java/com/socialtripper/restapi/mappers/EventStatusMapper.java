package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventStatusDTO;
import com.socialtripper.restapi.entities.EventStatus;
import org.springframework.stereotype.Component;

@Component
public class EventStatusMapper {
    public EventStatus toEntity(EventStatusDTO eventStatusDTO) {
        if (eventStatusDTO == null) return null;
        return new EventStatus(
                eventStatusDTO.id(),
                eventStatusDTO.name()
        );
    }

    public EventStatusDTO toDTO(EventStatus eventStatus) {
        if (eventStatus == null) return null;
        return new EventStatusDTO(
                eventStatus.getId(),
                eventStatus.getName()
        );
    }
}
