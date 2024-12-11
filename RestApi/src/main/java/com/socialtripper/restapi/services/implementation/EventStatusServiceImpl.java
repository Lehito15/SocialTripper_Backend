package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.entities.EventStatus;
import com.socialtripper.restapi.repositories.relational.EventStatusRepository;
import com.socialtripper.restapi.services.EventStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementacja serwisu zarządzającego operacjami wykonywanymi na encjach statusu wydarzenia.
 */
@Service
public class EventStatusServiceImpl implements EventStatusService {
    /**
     * Repozytorium zarządzające encjami statusu wydarzenia.
     */
    private final EventStatusRepository eventStatusRepository;

    /**
     * Konstruktor wstrzykujący repozytorium zarządzające encjami statusu wydarzenia.
     *
     * @param eventStatusRepository repozytorium zarządzające encjami statusu wydarzenia
     */
    @Autowired
    public EventStatusServiceImpl(EventStatusRepository eventStatusRepository) {
        this.eventStatusRepository = eventStatusRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventStatus getEventStatusReference(Long id) {
        return eventStatusRepository.getReferenceById(id);
    }

}
