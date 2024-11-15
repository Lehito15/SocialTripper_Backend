package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventStatusDTO;
import com.socialtripper.restapi.entities.EventStatus;
import org.springframework.stereotype.Component;

@Component
public class EventStatusMapper {
    public EventStatusDTO toDTO(EventStatus eventStatus) {
        if (eventStatus == null) return null;
        return new EventStatusDTO(
                eventStatus.getName()
        );
    }
}
