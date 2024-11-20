package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.*;
import com.socialtripper.restapi.dto.messages.EventStatusChangedMessageDTO;
import com.socialtripper.restapi.dto.messages.UserJoinsEventMessageDTO;
import com.socialtripper.restapi.dto.messages.UserLeavesEventMessageDTO;
import com.socialtripper.restapi.dto.requests.EventMultimediaMetadataDTO;
import com.socialtripper.restapi.dto.requests.UserPathPointsDTO;
import com.socialtripper.restapi.dto.requests.UserRequestEventDTO;
import com.socialtripper.restapi.dto.thumbnails.UserJourneyInEventDTO;
import com.socialtripper.restapi.entities.Event;
import com.socialtripper.restapi.entities.EventActivity;
import com.socialtripper.restapi.entities.EventLanguage;
import com.socialtripper.restapi.nodes.EventNode;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface EventService {
    List<EventDTO> findAllEvents();
    EventDTO findEventByUUID(UUID uuid);
    Long getEventIdByUUID(UUID uuid);
    Event getEventReference(UUID uuid);
    EventDTO createEvent(EventDTO eventDTO, MultipartFile icon);
    GroupEventDTO createGroupEvent(GroupEventDTO groupEventDTO);
    EventStatusChangedMessageDTO setStatus(UUID uuid, String status);
    EventDTO updateEvent(UUID uuid, EventDTO eventDTO);
    EventActivity setActivity(UUID uuid, EventActivityDTO eventActivityDTO);
    EventLanguage setLanguage(UUID uuid, EventLanguageDTO eventLanguageDTO);
    List<EventDTO> findUserEventsHistory(UUID uuid);
    List<EventDTO> findUserUpcomingEvents(UUID uuid);
    UserJoinsEventMessageDTO addUserToEvent(UUID userUUID, UUID eventUUID);
    UserLeavesEventMessageDTO removeUserFromEvent(UUID userUUID, UUID eventUUID);
    EventNode findEventNodeByUUID(UUID uuid);
    List<EventMultimediaMetadataDTO> findEventMultimedia(UUID eventUUID);
    EventMultimediaMetadataDTO uploadEventMultimedia(EventMultimediaMetadataDTO multimediaMetadata, MultipartFile multimedia);
    UserPathPointsDTO addUserPathPoints(UserPathPointsDTO userPathPointsDTO);
    UserRequestEventDTO userAppliesForEvent(UserRequestEventDTO userRequestEventDTO);
    List<Map<String, Object>> findEventRequest(UUID uuid);
    UserJourneyInEventDTO getUserJourneyInEvent(UUID userUUID, UUID eventUUID);
    void removeEventRequest(UUID userUUID, UUID eventUUID);

}
