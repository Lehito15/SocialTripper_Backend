package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.entities.EventStatus;
import com.socialtripper.restapi.repositories.relational.EventStatusRepository;
import com.socialtripper.restapi.services.EventStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventStatusServiceImpl implements EventStatusService {
    private final EventStatusRepository eventStatusRepository;

    @Autowired
    public EventStatusServiceImpl(EventStatusRepository eventStatusRepository) {
        this.eventStatusRepository = eventStatusRepository;
    }

    @Override
    public EventStatus getEventStatusReference(Long id) {
        return eventStatusRepository.getReferenceById(id);
    }

}
