package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.dto.entities.EventDTO;
import com.socialtripper.restapi.entities.Event;
import com.socialtripper.restapi.exceptions.EventNotFoundException;
import com.socialtripper.restapi.mappers.EventMapper;
import com.socialtripper.restapi.repositories.EventRepository;
import com.socialtripper.restapi.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public List<EventDTO> findAllEvents() {
        return eventRepository.findAll().stream().map(eventMapper::toDTO).toList();
    }

    @Override
    public EventDTO findEventByUUID(UUID uuid) {
        return eventMapper.toDTO(
                eventRepository.findByUuid(uuid)
                        .orElseThrow(() -> new EventNotFoundException(uuid)));
    }

    @Override
    public Long getEventIdByUUID(UUID uuid) {
        return eventRepository.findIdByUUID(uuid).orElseThrow(() -> new EventNotFoundException(uuid));
    }

    @Override
    public Event getEventReference(UUID uuid) {
        return eventRepository.getReferenceById(getEventIdByUUID(uuid));
    }
}
