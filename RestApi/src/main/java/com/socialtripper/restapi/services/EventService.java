package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.EventDTO;
import com.socialtripper.restapi.entities.Event;

import java.util.List;
import java.util.UUID;

public interface EventService {
    List<EventDTO> findAllEvents();
    EventDTO findEventByUUID(UUID uuid);
    Long getEventIdByUUID(UUID uuid);
    Event getEventReference(UUID uuid);
}
