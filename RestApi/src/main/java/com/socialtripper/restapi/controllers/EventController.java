package com.socialtripper.restapi.controllers;

import com.socialtripper.restapi.dto.entities.EventDTO;
import com.socialtripper.restapi.dto.entities.EventStatusDTO;
import com.socialtripper.restapi.dto.entities.GroupEventDTO;
import com.socialtripper.restapi.dto.messages.EventStatusChangedMessageDTO;
import com.socialtripper.restapi.dto.messages.UserJoinsEventMessageDTO;
import com.socialtripper.restapi.dto.messages.UserLeavesEventMessageDTO;
import com.socialtripper.restapi.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(eventService.createEvent(eventDTO));
    }

    @PostMapping("/events/group-events")
    public ResponseEntity<GroupEventDTO> createGroupEvent(@RequestBody GroupEventDTO eventDTO) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(eventService.createGroupEvent(eventDTO));
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

    @PostMapping("/users/{user-uuid}/events/{event-uuid}")
    public ResponseEntity<UserJoinsEventMessageDTO> addUserToEvent(@PathVariable("user-uuid") UUID userUUID,
                                                                   @PathVariable("event-uuid") UUID eventUUID) {
        return ResponseEntity.ok(eventService.addUserToEvent(userUUID, eventUUID));
    }

    @DeleteMapping("/users/{user-uuid}/events/{event-uuid}")
    public ResponseEntity<UserLeavesEventMessageDTO> removeUserFromEvent(@PathVariable("user-uuid") UUID userUUID,
                                                                         @PathVariable("event-uuid") UUID eventUUID) {
        return ResponseEntity.ok(eventService.removeUserFromEvent(userUUID, eventUUID));
    }

    @GetMapping("/users/{uuid}/events")
    public ResponseEntity<List<EventDTO>> getUserEvents(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.findUserEventsHistory(uuid));
    }

    @GetMapping("/users/{uuid}/events/upcoming")
    public ResponseEntity<List<EventDTO>> getUpcomingEvents(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.findUserUpcomingEvents(uuid));
    }
}
