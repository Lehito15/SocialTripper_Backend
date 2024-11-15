package com.socialtripper.restapi.services;

import com.socialtripper.restapi.entities.EventStatus;


public interface EventStatusService {
    EventStatus getEventStatusReference(Long id);
}
