package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.EventActivityDTO;
import com.socialtripper.restapi.dto.entities.EventDTO;
import com.socialtripper.restapi.dto.entities.EventLanguageDTO;
import com.socialtripper.restapi.dto.entities.GroupEventDTO;
import com.socialtripper.restapi.dto.messages.EventStatusChangedMessageDTO;
import com.socialtripper.restapi.dto.messages.UserJoinsEventMessageDTO;
import com.socialtripper.restapi.dto.messages.UserLeavesEventMessageDTO;
import com.socialtripper.restapi.entities.Event;
import com.socialtripper.restapi.entities.EventActivity;
import com.socialtripper.restapi.entities.EventLanguage;

import java.util.List;
import java.util.UUID;

public interface EventService {
    List<EventDTO> findAllEvents();
    EventDTO findEventByUUID(UUID uuid);
    Long getEventIdByUUID(UUID uuid);
    Event getEventReference(UUID uuid);
    EventDTO createEvent(EventDTO eventDTO);
    GroupEventDTO createGroupEvent(GroupEventDTO groupEventDTO);
    EventStatusChangedMessageDTO setStatus(UUID uuid, String status);
    EventDTO updateEvent(UUID uuid, EventDTO eventDTO);
    EventActivity setActivity(UUID uuid, EventActivityDTO eventActivityDTO);
    EventLanguage setLanguage(UUID uuid, EventLanguageDTO eventLanguageDTO);
    List<EventDTO> findUserEventsHistory(UUID uuid);
    List<EventDTO> findUserUpcomingEvents(UUID uuid);
    UserJoinsEventMessageDTO addUserToEvent(UUID userUUID, UUID eventUUID);
    UserLeavesEventMessageDTO removeUserFromEvent(UUID userUUID, UUID eventUUID);
}
