package com.socialtripper.restapi.controllers;

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
import com.socialtripper.restapi.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.UUID;

@Controller
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.findAllEvents());
    }

    @GetMapping("/events/{uuid}")
    public ResponseEntity<EventDTO> getEventByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.findEventByUUID(uuid));
    }

    @PostMapping("/events")
    public ResponseEntity<EventDTO> createEvent(@RequestPart EventDTO event,
                                                @RequestPart(required = false) MultipartFile icon) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(eventService.createEvent(event, icon));
    }

    @PostMapping("/events/group-events")
    public ResponseEntity<GroupEventDTO> createGroupEvent(@RequestPart GroupEventDTO eventDTO,
                                                          @RequestPart(required = false) MultipartFile icon) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(eventService.createGroupEvent(eventDTO, icon));
    }

    @PatchMapping("/events/{uuid}/set-status")
    public ResponseEntity<EventStatusChangedMessageDTO> setEventStatus(@PathVariable UUID uuid,
                                                                       @RequestBody EventStatusDTO eventStatusDTO) {
        return ResponseEntity.ok(eventService.setStatus(uuid, eventStatusDTO.status()));
    }

    @PatchMapping("/events/{uuid}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable UUID uuid,
                                                @RequestBody EventDTO eventDTO)  {
        return ResponseEntity.ok(eventService.updateEvent(uuid, eventDTO));
    }

    @PostMapping("/events/add-member")
    public ResponseEntity<UserJoinsEventMessageDTO> addUserToEvent(@RequestBody UserRequestEventDTO userRequestEvent) {
        return ResponseEntity.ok(eventService.addUserToEvent(
                userRequestEvent.userUUID(),
                userRequestEvent.eventUUID()));
    }

    @DeleteMapping("/events/remove-member")
    public ResponseEntity<UserLeavesEventMessageDTO> removeUserFromEvent(@RequestBody UserRequestEventDTO userRequestEvent) {
        return ResponseEntity.ok(eventService.removeUserFromEvent(
                userRequestEvent.userUUID(),
                userRequestEvent.eventUUID()));
    }

    @GetMapping("/users/{uuid}/events")
    public ResponseEntity<List<EventThumbnailDTO>> getUserEvents(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.findUserEventsHistory(uuid));
    }

    @GetMapping("/users/{uuid}/events/upcoming")
    public ResponseEntity<List<EventThumbnailDTO>> getUpcomingEvents(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.findUserUpcomingEvents(uuid));
    }

    @PostMapping("/events/multimedia")
    public ResponseEntity<EventMultimediaMetadataDTO> uploadEventMultimedia(@RequestPart MultipartFile multimedia,
                                                                            @RequestPart EventMultimediaMetadataDTO eventMultimediaMetadataDTO) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(
                eventService.uploadEventMultimedia(eventMultimediaMetadataDTO, multimedia)
        );
    }

    @PostMapping("/events/path-points")
    public ResponseEntity<UserPathPointsDTO> addUserPathPoints(@RequestBody UserPathPointsDTO userPathPointsDTO) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(
                eventService.addUserPathPoints(userPathPointsDTO)
        );
    }

    @PostMapping("/events/request")
    public ResponseEntity<UserRequestEventDTO> userApplyForEvent(@RequestBody UserRequestEventDTO userRequestEventDTO) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(
                eventService.userAppliesForEvent(userRequestEventDTO)
        );
    }

    @GetMapping("/events/{uuid}/requests")
    public ResponseEntity<List<AccountThumbnailDTO>> getEventRequests(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.findEventRequest(uuid));
    }

    @GetMapping("/events/{event-uuid}/users/{user-uuid}")
    public ResponseEntity<UserJourneyInEventDTO> getUserJourneyInEvent(@PathVariable("event-uuid") UUID eventUUID,
                                                                       @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(eventService.getUserJourneyInEvent(userUUID, eventUUID));
    }

    @GetMapping("/events/{uuid}/multimedia")
    public ResponseEntity<List<EventMultimediaMetadataDTO>> getEventMultimedia(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.findEventMultimedia(uuid));
    }

    @GetMapping("/groups/{uuid}/events")
    public ResponseEntity<List<EventThumbnailDTO>> getGroupEvents(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.getGroupEvents(uuid));
    }

    @GetMapping("/events/{event-uuid}/users/{user-uuid}/is-member")
    public ResponseEntity<Boolean> isEventMember(@PathVariable("event-uuid") UUID eventUUID,
                                                 @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(eventService.isEventMember(userUUID, eventUUID));
    }

    @GetMapping("/events/{uuid}/members")
    public ResponseEntity<List<AccountThumbnailDTO>> getEventMembers(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.getEventMembers(uuid));
    }

    @GetMapping("/events/{event-uuid}/users/{user-uuid}/is-event-requested")
    public ResponseEntity<Boolean> isEventRequested(@PathVariable("event-uuid") UUID eventUUID,
                                                    @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(eventService.isEventRequestSent(userUUID, eventUUID));
    }

}
