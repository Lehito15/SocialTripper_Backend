package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.*;
import com.socialtripper.restapi.dto.messages.EventStatusChangedMessageDTO;
import com.socialtripper.restapi.dto.messages.UserJoinsEventMessageDTO;
import com.socialtripper.restapi.dto.messages.UserLeavesEventMessageDTO;
import com.socialtripper.restapi.dto.requests.EventMultimediaMetadataDTO;
import com.socialtripper.restapi.dto.requests.UserPathPointsDTO;
import com.socialtripper.restapi.dto.requests.UserRequestEventDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.dto.thumbnails.EventThumbnailDTO;
import com.socialtripper.restapi.dto.thumbnails.UserJourneyInEventDTO;
import com.socialtripper.restapi.entities.Event;
import com.socialtripper.restapi.entities.EventActivity;
import com.socialtripper.restapi.entities.EventLanguage;
import com.socialtripper.restapi.nodes.EventNode;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

public interface EventService {
    List<EventDTO> findAllEvents();
    EventDTO findEventByUUID(UUID uuid);
    EventThumbnailDTO findEventThumbnailByUUID(UUID uuid);
    Long getEventIdByUUID(UUID uuid);
    Event getEventReference(UUID uuid);
    EventDTO createEvent(EventDTO eventDTO, MultipartFile icon);
    GroupEventDTO createGroupEvent(GroupEventDTO groupEventDTO, MultipartFile icon);
    EventStatusChangedMessageDTO setStatus(UUID uuid, String status);
    EventDTO updateEvent(UUID uuid, EventDTO eventDTO);
    EventActivity setActivity(UUID uuid, EventActivityDTO eventActivityDTO);
    EventLanguage setLanguage(UUID uuid, EventLanguageDTO eventLanguageDTO);
    List<EventThumbnailDTO> findUserEventsHistory(UUID uuid);
    List<EventThumbnailDTO> findUserUpcomingEvents(UUID uuid);
    UserJoinsEventMessageDTO addUserToEvent(UUID userUUID, UUID eventUUID);
    UserLeavesEventMessageDTO removeUserFromEvent(UUID userUUID, UUID eventUUID);
    EventNode findEventNodeByUUID(UUID uuid);
    List<EventMultimediaMetadataDTO> findEventMultimedia(UUID eventUUID);
    EventMultimediaMetadataDTO uploadEventMultimedia(EventMultimediaMetadataDTO multimediaMetadata, MultipartFile multimedia);
    UserPathPointsDTO addUserPathPoints(UserPathPointsDTO userPathPointsDTO);
    UserRequestEventDTO userAppliesForEvent(UserRequestEventDTO userRequestEventDTO);
    List<AccountThumbnailDTO> findEventRequest(UUID uuid);
    UserJourneyInEventDTO getUserJourneyInEvent(UUID userUUID, UUID eventUUID);
    List<EventThumbnailDTO> getGroupEvents(UUID groupUUID);
    List<AccountThumbnailDTO> getEventMembers(UUID eventUUID);
    Boolean isEventRequestSent(UUID userUUID, UUID eventUUID);
    Boolean isEventMember(UUID userUUID, UUID eventUUID);
    List<EventThumbnailDTO> getEventsByNameSubstring(String eventNameSubstring);

}
