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
import com.socialtripper.restapi.dto.thumbnails.MultimediaDTO;
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

/**
 * Kontroler zarządzający żądaniami HTTP związanymi z wydarzeniami.
 */
@Controller
public class EventController {
    /**
     * Serwis zarządzający operacjami związanymi z wydarzeniami.
     */
    private final EventService eventService;

    /**
     * Konstruktor wstrzykujący serwis zarządzający operacjami związanymi z wydarzeniami.
     *
     * @param eventService serwis zarządzający operacjami związanymi z wydarzeniami
     */
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Pobieranie wydarzeń.
     *
     * @return odpowiedź http z listą data transfer object wydarzeń
     */
    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.findAllEvents());
    }

    /**
     * Pobieranie wydarzenia o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return odpowiedź http z data transfer object wydarzenie lub błąd 404, gdy wydarzenie nie istnieje
     */
    @GetMapping("/events/{uuid}")
    public ResponseEntity<EventDTO> getEventByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.findEventByUUID(uuid));
    }

    /**
     * Tworzenie nowego wydarzenia.
     *
     * @param event data transfer object wydarzenia
     * @param icon plik ikony wydarzenia
     * @return odpowiedź http z data transfer object nowego wydarzenia
     */
    @PostMapping("/events")
    public ResponseEntity<EventDTO> createEvent(@RequestPart EventDTO event,
                                                @RequestPart(required = false) MultipartFile icon) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(eventService.createEvent(event, icon));
    }

    /**
     * Tworzenie wydarzenia grupowego.
     *
     * @param eventDTO data transfer object wydarzenia grupowego
     * @param icon plik ikony wydarzenia
     * @return odpowiedź http z data transfer object nowego wydarzenia grupowego
     */
    @PostMapping("/events/group-events")
    public ResponseEntity<GroupEventDTO> createGroupEvent(@RequestPart GroupEventDTO eventDTO,
                                                          @RequestPart(required = false) MultipartFile icon) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(eventService.createGroupEvent(eventDTO, icon));
    }

    /**
     * Zmiana statusu wydarzenia.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @param eventStatusDTO data transfer object statusu wydarzenia
     * @return odpowiedź http z wiadomością o zmianie statusu
     */
    @PatchMapping("/events/{uuid}/set-status")
    public ResponseEntity<EventStatusChangedMessageDTO> setEventStatus(@PathVariable UUID uuid,
                                                                       @RequestBody EventStatusDTO eventStatusDTO) {
        return ResponseEntity.ok(eventService.setStatus(uuid, eventStatusDTO.status()));
    }

    /**
     * Aktualizaja danych wydarzenia.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @param eventDTO data transfer object wydarzenia
     * @return odpowiedź http z data transfer object wydarzenia
     */
    @PatchMapping("/events/{uuid}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable UUID uuid,
                                                @RequestBody EventDTO eventDTO)  {
        return ResponseEntity.ok(eventService.updateEvent(uuid, eventDTO));
    }

    /**
     * Dodanie użytkownika do wydarzenia.
     *
     * @param userRequestEvent data transfer object zapytania o dołączenie do wydarzenia
     * @return odpowiedź http z wiadomością o dołączeniu do wydarzenia
     */
    @PostMapping("/events/add-member")
    public ResponseEntity<UserJoinsEventMessageDTO> addUserToEvent(@RequestBody UserRequestEventDTO userRequestEvent) {
        return ResponseEntity.ok(eventService.addUserToEvent(
                userRequestEvent.userUUID(),
                userRequestEvent.eventUUID()));
    }

    /**
     * Usunięcie użytkownika z wydarzenia.
     *
     * @param userRequestEvent data transfer object zapytania o dołączenie do wydarzenia
     * @return odpowiedź http z wiadomością o opuszczeniu wydarzenia
     */
    @DeleteMapping("/events/remove-member")
    public ResponseEntity<UserLeavesEventMessageDTO> removeUserFromEvent(@RequestBody UserRequestEventDTO userRequestEvent) {
        return ResponseEntity.ok(eventService.removeUserFromEvent(
                userRequestEvent.userUUID(),
                userRequestEvent.eventUUID()));
    }

    /**
     * Pobranie wydarzeń użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return odpowiedź http z listą data transfer object wydarzeń
     */
    @GetMapping("/users/{uuid}/events")
    public ResponseEntity<List<EventDTO>> getUserEvents(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.findUserEventsHistory(uuid));
    }

    /**
     * Pobranie nadchodzących wydarzeń użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return odpowiedź http z listą data transfer object wydarzeń
     */
    @GetMapping("/users/{uuid}/events/upcoming")
    public ResponseEntity<List<EventDTO>> getUpcomingEvents(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.findUserUpcomingEvents(uuid));
    }

    /**
     * Dodanie multimediów do trwającego wydarzenia.
     *
     * @param multimedia plik multimedialny
     * @param eventMultimediaMetadataDTO data transfer object metadanych multimedium
     * @return odpowiedź http z data transfer object metadanych multimediów
     */
    @PostMapping("/events/multimedia")
    public ResponseEntity<EventMultimediaMetadataDTO> uploadEventMultimedia(@RequestPart MultipartFile multimedia,
                                                                            @RequestPart EventMultimediaMetadataDTO eventMultimediaMetadataDTO) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(
                eventService.uploadEventMultimedia(eventMultimediaMetadataDTO, multimedia)
        );
    }

    /**
     * Dodanie współrzędnych geograficznych odwiedzonych przez użytkownika w wydarzeniu.
     *
     * @param userPathPointsDTO data transfer object odwiedzonych punktów geograficznych
     * @return odpowiedź http z odwiedzonymi punktami geograficznymi
     */
    @PostMapping("/events/path-points")
    public ResponseEntity<UserPathPointsDTO> addUserPathPoints(@RequestBody UserPathPointsDTO userPathPointsDTO) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(
                eventService.addUserPathPoints(userPathPointsDTO)
        );
    }

    /**
     * Dodanie zapytania użytkownika o dołączeniu do wydarzenia.
     *
     * @param userRequestEventDTO data transfer object zapytania o dołączenie do wydarzenia
     * @return odpowiedź http z wiadomością o wysłaniu zapytania o dołączenie do wydarzenia
     */
    @PostMapping("/events/request")
    public ResponseEntity<UserRequestEventDTO> userApplyForEvent(@RequestBody UserRequestEventDTO userRequestEventDTO) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(
                eventService.userAppliesForEvent(userRequestEventDTO)
        );
    }

    /**
     * Usunięcie zapytania użytkownika o dołączeniu do wydarzenia.
     *
     * @param userRequestEventDTO data transfer object zapytania o dołączenie do wydarzenia
     * @return odpowiedź http z wiadomością o usunięciu zapytania o dołączenie wydarzenia
     */
    @DeleteMapping("/events/request")
    public ResponseEntity<UserRequestEventDTO> removeUserApplyForEvent(@RequestBody UserRequestEventDTO userRequestEventDTO) {
        return ResponseEntity.ok(eventService.removeUserApplyForEvent(userRequestEventDTO));
    }

    /**
     * Pobranie zapytań o dołączenie do wydarzenia.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return odpowiedź http z listą data transfer object częściowej informacji o koncie użytkownika
     */
    @GetMapping("/events/{uuid}/requests")
    public ResponseEntity<List<AccountThumbnailDTO>> getEventRequests(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.findEventRequests(uuid));
    }

    /**
     * Pobranie danych wydarzenia w wykonaniu użytkownika.
     *
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z data transfer object podsumowania wydarzenia
     */
    @GetMapping("/events/{event-uuid}/users/{user-uuid}")
    public ResponseEntity<UserJourneyInEventDTO> getUserJourneyInEvent(@PathVariable("event-uuid") UUID eventUUID,
                                                                       @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(eventService.getUserJourneyInEvent(userUUID, eventUUID));
    }

    /**
     * Pobranie multimediów wygenerowanych podczas wydarzenia.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return odpowiedź http z data transfer object multimediów wraz z ich metadanymi
     */
    @GetMapping("/events/{uuid}/multimedia")
    public ResponseEntity<List<EventMultimediaMetadataDTO>> getEventMultimedia(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.findEventMultimedia(uuid));
    }

    /**
     * Pobranie wydarzeń w grupie.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @return odpwiedź http z listą data transfer object wydarzeń
     */
    @GetMapping("/groups/{uuid}/events")
    public ResponseEntity<List<EventDTO>> getGroupEvents(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.getGroupEvents(uuid));
    }

    /**
     * Pobranie informacji czy użytkownik jest członkiem wydarzenia.
     *
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z wartością logiczną
     */
    @GetMapping("/events/{event-uuid}/users/{user-uuid}/is-member")
    public ResponseEntity<Boolean> isEventMember(@PathVariable("event-uuid") UUID eventUUID,
                                                 @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(eventService.isEventMember(userUUID, eventUUID));
    }

    /**
     * Pobranie uczestników wydarzenia.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return odpowiedź http z listą data transfer object częściowej informacji o koncie
     */
    @GetMapping("/events/{uuid}/members")
    public ResponseEntity<List<AccountThumbnailDTO>> getEventMembers(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.getEventMembers(uuid));
    }

    /**
     * Pobranie informacji czy użytkownik, wysłał zapytanie o dołączenie do wydarzenia.
     *
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z wartością logiczną
     */
    @GetMapping("/events/{event-uuid}/users/{user-uuid}/is-event-requested")
    public ResponseEntity<Boolean> isEventRequested(@PathVariable("event-uuid") UUID eventUUID,
                                                    @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(eventService.isEventRequestSent(userUUID, eventUUID));
    }

    /**
     * Pobranie wydarzeń zawierających podany łańcuch.
     *
     * @param eventName łańcuch nazwy wydarzenia
     * @return odpowiedź http z listą data transfer object częściowej informacji o wydarzeniu
     */
    @GetMapping("/events/by-name")
    public ResponseEntity<List<EventThumbnailDTO>> getEventsByName(@RequestParam("name") String eventName) {
        return ResponseEntity.ok(eventService.getEventsByNameSubstring(eventName));
    }

    /**
     * Pobranie wydarzeń ukończonych przez użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @param numberOfEvents liczba wydarzeń do pobrania
     * @return odpowiedź http z listą data transfer object wydarzeń
     */
    @GetMapping("/users/{uuid}/accomplished-events")
    public ResponseEntity<List<EventDTO>> getAccomplishedEvents(@PathVariable UUID uuid,
                                                                @RequestParam int numberOfEvents) {
        return ResponseEntity.ok(eventService.getUserAccomplishedEvents(uuid, numberOfEvents));
    }

    /**
     * Aktualizacja ikony wydarzenia.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @param icon plik nowej ikony wydarzenia
     * @return odpowiedź http z data transfer object multimedium
     */
    @PatchMapping("/events/{uuid}/profile-picture")
    public ResponseEntity<MultimediaDTO> updateEventIcon(@PathVariable UUID uuid,
                                                         @RequestPart MultipartFile icon) {
        return ResponseEntity.ok(eventService.updateEventIcon(uuid, icon));
    }

    /**
     * Pobranie rekomendowanych wydarzeń dla użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z listą data transfer object wydarzeń
     */
    @GetMapping("/users/{uuid}/recommended-events")
    public ResponseEntity<List<EventDTO>> getRecommendedEvents(@PathVariable UUID uuid) {
        return ResponseEntity.ok(eventService.getUserRecommendedEvents(uuid));
    }

}
