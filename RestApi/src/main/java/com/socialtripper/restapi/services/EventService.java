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
import com.socialtripper.restapi.dto.thumbnails.MultimediaDTO;
import com.socialtripper.restapi.dto.thumbnails.UserJourneyInEventDTO;
import com.socialtripper.restapi.entities.Event;
import com.socialtripper.restapi.entities.EventActivity;
import com.socialtripper.restapi.entities.EventLanguage;
import com.socialtripper.restapi.nodes.EventNode;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

/**
 * Serwis zarządzający operacjami wykonywanymi na encjach wydarzeń.
 */
public interface EventService {
    /**
     * Metoda zwracająca wszystkie wydarzenia z bazy danych.
     *
     * @return lista data transfer object wydarzeń
     */
    List<EventDTO> findAllEvents();

    /**
     * Metoda zwracająca wydarzenie o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return data transfer object wydarzenia
     */
    EventDTO findEventByUUID(UUID uuid);

    /**
     * Metoda zwracająca częściowe informacje o wydarzeniu o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return data transfer object częściowej informacji o wydarzeniu
     */
    EventThumbnailDTO findEventThumbnailByUUID(UUID uuid);

    /**
     * Metoda zwracająca klucz główny wydarzenia na podstawie UUID.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return klucz główny wydarzenia
     */
    Long getEventIdByUUID(UUID uuid);

    /**
     * Metoda zwracająca referencję na encję wydarzenia z bazy danych.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return referencja na encję wydarzenia istniejącego w bazie danych
     */
    Event getEventReference(UUID uuid);

    /**
     * Metoda zwracająca encję dla nowo utworzonego wydarzenia wraz z referencjami na istniejące w bazie encje agregowane
     * przez wydarzenie.
     *
     * @param eventDTO data transfer object wydarzenia
     * @param userUUID globalny, unikalny identyfikator użytkownika w systemie
     * @return encja wydarzenia
     */
    Event getNewEventWithReferences(EventDTO eventDTO, UUID userUUID);

    /**
     * Metoda tworząca nowe wydarzenie.
     *
     * @param eventDTO data transfer object wydarzenia
     * @param icon plik ikony wydarznia
     * @return data transfer object wydarzenia
     */
    EventDTO createEvent(EventDTO eventDTO, MultipartFile icon);

    /**
     * Metoda tworząca wydarzenie grupowe.
     *
     * @param groupEventDTO data transfer object wydarznia grupowego
     * @param icon plik ikonty wydarzenia grupowego
     * @return data transfer object wydarzenia grupowego
     */
    GroupEventDTO createGroupEvent(GroupEventDTO groupEventDTO, MultipartFile icon);

    /**
     * Metoda zmieniająca status wydarzenia.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @param status nazwa statusu wydarzenia
     * @return wiadomość o zmianie statusu wydarzenia
     */
    EventStatusChangedMessageDTO setStatus(UUID uuid, String status);

    /**
     * Metoda aktualizująca informacje o wydarzeniu. Metoda aktualizuje wartości niezwiązane z encjami agregowanymi
     * przerz encję wydarzenia.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @param eventDTO data transfer object, wartości null są ignorowane przy aktualizacji danych
     * @return data transfer object wydarzenia
     */
    EventDTO updateEvent(UUID uuid, EventDTO eventDTO);

    /**
     * Metoda aktualizująca aktywności w wydarzeniu.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @param eventActivityDTO data transfer object aktywności w wydarzeniu
     * @return encja aktywności w wydarzeniu
     */
    EventActivity setActivity(UUID uuid, EventActivityDTO eventActivityDTO);

    /**
     * Metoda aktualizująca języki w wydarzeniu.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @param eventLanguageDTO data transfer object języka w wydarzeniu
     * @return encja języka w wydarzeniu
     */
    EventLanguage setLanguage(UUID uuid, EventLanguageDTO eventLanguageDTO);

    /**
     * Metoda zwracająca zakończone wydarzenia użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return lista data transfer object wydarzeń
     */
    List<EventDTO> findUserEventsHistory(UUID uuid);

    /**
     * Metoda zwracająca nadchodzące wydarzenia, których użytkownik jest członkiem.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return lista data transfer object wydarzeń
     */
    List<EventDTO> findUserUpcomingEvents(UUID uuid);

    /**
     * Metoda tworząca relację przynależności użytkownika do wydarzenia.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
     * @return wiadomość o dołączeniu do wydarzenia
     */
    UserJoinsEventMessageDTO addUserToEvent(UUID userUUID, UUID eventUUID);

    /**
     * Metoda usuwająca relację przynależności użytkownika do wydarzenia.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
     * @return wiadomość o opuszczeniu wydarzenia
     */
    UserLeavesEventMessageDTO removeUserFromEvent(UUID userUUID, UUID eventUUID);

    /**
     * Metoda zwracająca węzeł wydarzenia na podstawie identyfikatora UUID.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return węzeł wydarzenia
     */
    EventNode findEventNodeByUUID(UUID uuid);

    /**
     * Metoda zwracająca multimedia wygenerowane w trakcie trwania wydarzenia.
     *
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
     * @return lista data transfer object metadanych multimediów
     */
    List<EventMultimediaMetadataDTO> findEventMultimedia(UUID eventUUID);

    /**
     * Metoda dodająca nowe multimedium wraz z metadanymi.
     *
     * @param multimediaMetadata data transfer object metadanych multimedium
     * @param multimedia plik multimedialny
     * @return data transfer object metadanych multimedium
     */
    EventMultimediaMetadataDTO uploadEventMultimedia(EventMultimediaMetadataDTO multimediaMetadata, MultipartFile multimedia);

    /**
     * Metoda dodająca współrzędne geograficzne odwiedzone przez użytkownika w trakcie trwania wydarzenia.
     *
     * @param userPathPointsDTO data transfer object z punktami geograficznymi
     * @return data transfer object z punktami geograficznymi
     */
    UserPathPointsDTO addUserPathPoints(UserPathPointsDTO userPathPointsDTO);

    /**
     * Metoda tworząca relację zapytania o dołączenie do wydarzenia przez użytkownika.
     *
     * @param userRequestEventDTO data transfer object zapytania o dołączenie do wydarzenia
     * @return wiadomość o zapytaniu o dołączenie do wydarzenia
     */
    UserRequestEventDTO userAppliesForEvent(UserRequestEventDTO userRequestEventDTO);

    /**
     * Metoda usuwająca relację zapytania o dołączenie do wydarzenia przez użytkownika.
     * Wykonywana w momencie odrzucenia zapytania lub przyjęcia - użytkownika staje się wtedy członkiem wydarzenia.
     *
     * @param userRequestEventDTO data transfer object zapytania o dołączenie do wydarzenia
     * @return wiadomość o zapytaniu o dołączenie do wydarzenia
     */
    UserRequestEventDTO removeUserApplyForEvent(UserRequestEventDTO userRequestEventDTO);

    /**
     * Metoda zwracająca konta użytkowników, którzy wysłali zapytanie do dołączenie do wydarzenia.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return lista data transfer object częściowej informacji o koncie użytkownika
     */
    List<AccountThumbnailDTO> findEventRequests(UUID uuid);

    /**
     * Metoda zwracająca informacje o wydarzeniu w wykonaniu użytkownika.
     * Zwracane są odwiedzone współrzędne geograficzne oraz wygenerowane multimedia wraz z ich metadanymi.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
     * @return data transfer object podsumowania wydarzenia użytkownika
     */
    UserJourneyInEventDTO getUserJourneyInEvent(UUID userUUID, UUID eventUUID);

    /**
     * Metoda zwracająca wydarzenia w grupie.
     *
     * @param groupUUID globalny, unikalny identyfikator grupy w systemie
     * @return lista data transfer object wydarzeń
     */
    List<EventDTO> getGroupEvents(UUID groupUUID);

    /**
     * Metoda zwracająca konta użytkowników będących członkami wydarzenia.
     *
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
     * @return lista data transfer object częściowych informacji o koncie użytkownika
     */
    List<AccountThumbnailDTO> getEventMembers(UUID eventUUID);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownik wysłał zapytanie o dołącznie do wydarzenia.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
     * @return wartość logiczna informująca czy użytkowniak wysłał zapytanie o dołączenie do wydarzenia
     */
    Boolean isEventRequestSent(UUID userUUID, UUID eventUUID);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownika jest członkiem wydarzenia.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
     * @return wartość logiczna informująca czy użytkownik jest członkiem wydarzenia
     */
    Boolean isEventMember(UUID userUUID, UUID eventUUID);

    /**
     * Metoda zwracająca wydarzenia, których nazwy zawierają podany ciąg znaków.
     * Metoda wykorzystywana jest w momencie wyszukiwania przez użytkownika wydarzeń na pasku.
     *
     * @param eventNameSubstring ciąg znaków nazwy wydarzenia
     * @return lista data transfer object częściowej informacji o wydarzeniu
     */
    List<EventThumbnailDTO> getEventsByNameSubstring(String eventNameSubstring);

    /**
     * Metoda zwracająca wydarzenia ukończone przez użytkownika.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param numberOfEvents liczba pobieranych wydarzeń
     * @return lista data transfer object wydarzeń
     */
    List<EventDTO> getUserAccomplishedEvents(UUID userUUID, int numberOfEvents);

    /**
     * Metoda aktualizująca ikonę wydarzenia.
     *
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
     * @param icon plik nowej ikony wydarzenia
     * @return data transfer object multimedium
     */
    MultimediaDTO updateEventIcon(UUID eventUUID, MultipartFile icon);

    /**
     * Metoda pobierająca rekomendowane wydarzenia dla użytkownika.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @return lista data transfer object rekomendowanych wydarzeń
     */
    List<EventDTO> getUserRecommendedEvents(UUID userUUID);
}
