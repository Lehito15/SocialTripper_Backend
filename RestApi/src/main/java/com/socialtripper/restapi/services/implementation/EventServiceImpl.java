package com.socialtripper.restapi.services.implementation;

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
import com.socialtripper.restapi.entities.*;
import com.socialtripper.restapi.entities.enums.EventStatuses;
import com.socialtripper.restapi.exceptions.EventMembersLimitException;
import com.socialtripper.restapi.exceptions.EventNotFoundException;
import com.socialtripper.restapi.mappers.EventMapper;
import com.socialtripper.restapi.mappers.EventMultimediaMapper;
import com.socialtripper.restapi.mappers.EventThumbnailMapper;
import com.socialtripper.restapi.mappers.GroupEventMapper;
import com.socialtripper.restapi.nodes.EventMembership;
import com.socialtripper.restapi.nodes.EventMultimediaNode;
import com.socialtripper.restapi.nodes.EventNode;
import com.socialtripper.restapi.repositories.graph.EventMultimediaNodeRepository;
import com.socialtripper.restapi.repositories.graph.EventNodeRepository;
import com.socialtripper.restapi.repositories.relational.*;
import com.socialtripper.restapi.services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * Implementacja serwisu zarządzającego operacjami wykonywanymi na wydarzeniach.
 */
@Service
public class EventServiceImpl implements EventService {
    /**
     * Repozytorium zarządzające encjami wydarzeń.
     */
    private final EventRepository eventRepository;
    /**
     * Repozytorium zarządzające węzłami wydarzeń.
     */
    private final EventNodeRepository eventNodeRepository;
    /**
     * Repozytorium zarządzające encjami wydarzeń grupowych.
     */
    private final GroupEventRepository groupEventRepository;
    /**
     * Repozytorium zarządzające encjami aktywnościami wydarzeń.
     */
    private final EventActivityRepository eventActivityRepository;
    /**
     * Repozytorium zarządzające encjami językami wydarzeń.
     */
    private final EventLanguageRepository eventLanguageRepository;
    /**
     * Repozytorium zarządzające encjami multimediami wydarzeń.
     */
    private final EventMultimediaNodeRepository eventMultimediaRepository;
    /**
     * Komponent mapujący wydarzenia.
     */
    private final EventMapper eventMapper;
    /**
     * Komponent mapujący wydarzenia grupowe.
     */
    private final GroupEventMapper groupEventMapper;
    /**
     * Komponent mapujący częściowe dane na temat wydarzenia.
     */
    private final EventThumbnailMapper eventThumbnailMapper;
    /**
     * Komponent mapujący multimedia wydarzenia.
     */
    private final EventMultimediaMapper eventMultimediaMapper;
    /**
     * Serwis zarządzający operacjami na kontach użytkowników.
     */
    private final AccountService accountService;
    /**
     * Serwis zarządzający operacjami na użytkownikach.
     */
    private final UserService userService;
    /**
     * Serwis zarządzający operacjami na grupach.
     */
    private final GroupService groupService;
    /**
     * Serwis zarządzający operacjami na statusach wydarzeń.
     */
    private final EventStatusService eventStatusService;
    /**
     * Serwis zarządzający operacjami na aktywnościach.
     */
    private final ActivityService activityService;
    /**
     * Serwis zarządzający operacjami na językach.
     */
    private final LanguageService languageService;
    /**
     * Serwis zarządzający operacjami na członkach wydarzeń.
     */
    private final EventParticipantRepository eventParticipantRepository;
    /**
     * Repozytorium zarządzające encjami rekomendacji użytkowników.
     */
    private final UserRecommendationRepository userRecommendationRepository;
    /**
     * Serwis zarządzający operacjami na multimediach.
     */
    private final MultimediaService multimediaService;

    /**
     * Konstruktor wstrzykujący komponenty.
     *
     * @param eventRepository repozytorium zarządzające encjami wydarzeń
     * @param eventMapper komponent mapujący wydarzenia
     * @param accountService serwis zarządzający operacjami na kontach użytkowników
     * @param groupService serwis zarządzający operacjami na grupach
     * @param groupEventMapper komponent mapujący wydarzenia grupowe
     * @param groupEventRepository repozytorium zarządzające encjami wydarzeń grupowych
     * @param eventStatusService serwis zarządzający operacjami na statusach wydarzeń
     * @param activityService serwis zarządzający operacjami na aktywnościach
     * @param languageService serwis zarządzający operacjami na językach
     * @param eventActivityRepository repozytorium zarządzające encjami aktywności wydarzeń
     * @param eventLanguageRepository repozytorium zarządzające encjami języków wydarzeń
     * @param eventParticipantRepository repozytorium zarządzające encjami członków wydarzeń
     * @param eventNodeRepository repozytorium zarządzające węzłami wydarzeń
     * @param multimediaService serwis zarządzający operacjami na multimediach
     * @param userService serwis zarządzający operacjami na użytkownikach
     * @param eventMultimediaRepository repozytorium zarządzające encjami multimediów wydarzeń
     * @param eventMultimediaMapper komponent mapujący multimedia wydarzenia
     * @param eventThumbnailMapper komponent mapujący częściowe dane wydarzenia
     * @param userRecommendationRepository repozytorium zarządzające encjami rekomendacji użytkowników
     */
    @Autowired
    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper,
                            AccountService accountService, GroupService groupService,
                            GroupEventMapper groupEventMapper, GroupEventRepository groupEventRepository,
                            EventStatusService eventStatusService, ActivityService activityService,
                            LanguageService languageService, EventActivityRepository eventActivityRepository,
                            EventLanguageRepository eventLanguageRepository, EventParticipantRepository eventParticipantRepository,
                            EventNodeRepository eventNodeRepository, MultimediaService multimediaService,
                            UserService userService, EventMultimediaNodeRepository eventMultimediaRepository,
                            EventMultimediaMapper eventMultimediaMapper, EventThumbnailMapper eventThumbnailMapper,
                            UserRecommendationRepository userRecommendationRepository) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.accountService = accountService;
        this.groupService = groupService;
        this.groupEventMapper = groupEventMapper;
        this.groupEventRepository = groupEventRepository;
        this.eventStatusService = eventStatusService;
        this.activityService = activityService;
        this.languageService = languageService;
        this.eventActivityRepository = eventActivityRepository;
        this.eventLanguageRepository = eventLanguageRepository;
        this.eventParticipantRepository = eventParticipantRepository;
        this.eventNodeRepository = eventNodeRepository;
        this.multimediaService = multimediaService;
        this.userService = userService;
        this.eventMultimediaRepository = eventMultimediaRepository;
        this.eventMultimediaMapper = eventMultimediaMapper;
        this.eventThumbnailMapper = eventThumbnailMapper;
        this.userRecommendationRepository = userRecommendationRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventDTO> findAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(event ->
                        eventMapper.toDTO(
                                event,
                                findEventNodeByUUID(event.getUuid())
                        ))
                .toList();
    }

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy encja wydarzenia nie istnieje w bazie rzucany jest wyjątek {@link EventNotFoundException}.
     * </p>
     */
    @Override
    public EventDTO findEventByUUID(UUID uuid) {
        return eventMapper.toDTO(
                eventRepository.findByUuid(uuid)
                        .orElseThrow(() -> new EventNotFoundException(uuid)),
                findEventNodeByUUID(uuid));
    }

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy encja wydarzenia nie istnieje w bazie rzucany jest wyjątek {@link EventNotFoundException}.
     * </p>
     */
    @Override
    public EventThumbnailDTO findEventThumbnailByUUID(UUID uuid) {
        return eventThumbnailMapper.toDTO(
                findEventByUUID(uuid)
        );
    }

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy encja wydarzenia nie istnieje w bazie rzucany jest wyjątek {@link EventNotFoundException}.
     * </p>
     */
    @Override
    public Long getEventIdByUUID(UUID uuid) {
        return eventRepository.findIdByUUID(uuid).orElseThrow(() -> new EventNotFoundException(uuid));
    }

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy encja wydarzenia nie istnieje w bazie rzucany jest wyjątek {@link EventNotFoundException}.
     * </p>
     */
    @Override
    public Event getEventReference(UUID uuid) {
        return eventRepository.getReferenceById(getEventIdByUUID(uuid));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Event getNewEventWithReferences(EventDTO eventDTO, UUID userUUID) {
        Event event = eventMapper.toNewEntity(eventDTO);
        event.setUuid(UUID.randomUUID());
        event.setName(eventDTO.name());
        event.setDateOfCreation(LocalDate.now());
        event.setHomePageUrl("/events/" + event.getUuid());
        event.setOwner(accountService.getAccountReference(userUUID));
        event.setEventStatus(eventStatusService.getEventStatusReference(EventStatuses.CREATED.getCode()));
        return event;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public EventDTO createEvent(EventDTO eventDTO, MultipartFile icon) {
        Event eventToSave = getNewEventWithReferences(eventDTO, eventDTO.owner().uuid());

        if (icon != null) {
            try {
                eventToSave.setIconUrl(
                        multimediaService.uploadMultimedia(
                                icon,
                                "events/" + eventToSave.getUuid() + "/" + UUID.randomUUID()));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        Event savedEvent = eventRepository.save(eventToSave);
        eventDTO.activities().forEach(
                (activity) ->
                        savedEvent.getEventActivities()
                                    .add(setActivity(savedEvent.getUuid(), activity))
        );

        eventDTO.languages().forEach(
                (language) -> savedEvent.getEventLanguages()
                                            .add(setLanguage(savedEvent.getUuid(), language))
        );
        saveInGraphDB(savedEvent, null);
        addUserToEvent(
                savedEvent.getOwner().getUuid(),
                savedEvent.getUuid());

        return eventMapper.toDTO(
                eventToSave,
                findEventNodeByUUID(savedEvent.getUuid()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEventDTO createGroupEvent(GroupEventDTO groupEventDTO, MultipartFile icon) {
        GroupEvent eventToSave = new GroupEvent(getNewEventWithReferences(groupEventDTO.eventDTO(),
                                                    groupEventDTO.eventDTO().owner().uuid()),
                groupService.getGroupReference(groupEventDTO.groupUUID()));

        if (icon != null) {
            try {
                eventToSave.getEvent().setIconUrl(
                        multimediaService.uploadMultimedia(
                                icon,
                                "events/" + eventToSave.getEvent().getUuid() + "/" + UUID.randomUUID()));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        GroupEvent savedEvent = groupEventRepository.save(eventToSave);

        groupEventDTO.eventDTO().activities().forEach(
                (activity) -> setActivity(savedEvent.getEvent().getUuid(), activity)
        );

        groupEventDTO.eventDTO().languages().forEach(
                (language) -> setLanguage(savedEvent.getEvent().getUuid(), language)
        );

        saveInGraphDB(savedEvent.getEvent(), groupEventDTO.groupUUID());
        addUserToEvent(
                savedEvent.getEvent().getOwner().getUuid(),
                savedEvent.getEvent().getUuid());
        return groupEventMapper.toDTO(
                savedEvent,
                findEventNodeByUUID(savedEvent.getEvent().getUuid()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventStatusChangedMessageDTO setStatus(UUID uuid, String status) {
        Event event = getEventReference(uuid);
        event.setEventStatus(
                eventStatusService.getEventStatusReference(
                        EventStatuses.fromStatus(status).orElseThrow(IllegalArgumentException::new).getCode()));
        eventRepository.save(event);
        return new EventStatusChangedMessageDTO(uuid, status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventDTO updateEvent(UUID uuid, EventDTO eventDTO) {
        Event event = eventMapper.copyNonNullValues(getEventReference(uuid), eventDTO);
        return eventMapper.toDTO(
                eventRepository.save(event),
                findEventNodeByUUID(uuid));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventActivity setActivity(UUID uuid, EventActivityDTO eventActivityDTO) {
        EventActivity eventActivity = new EventActivity(
                getEventReference(uuid),
                activityService.getActivityReference(eventActivityDTO.activity().name()),
                eventActivityDTO.requiredExperience());
        return eventActivityRepository.save(eventActivity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventLanguage setLanguage(UUID uuid, EventLanguageDTO eventLanguageDTO) {
        EventLanguage eventLanguage = new EventLanguage(
                getEventReference(uuid),
                languageService.getLanguageReference(eventLanguageDTO.language().name()),
                eventLanguageDTO.requiredLevel()
        );
        return eventLanguageRepository.save(eventLanguage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventDTO> findUserEventsHistory(UUID uuid) {
        return eventParticipantRepository.findUserEvents(uuid)
                .stream()
                .map(event ->
                        eventMapper.toDTO(
                                event,
                                findEventNodeByUUID(event.getUuid())
                        ))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventDTO> findUserUpcomingEvents(UUID uuid) {
        return eventParticipantRepository.findUserUpcomingEvents(uuid)
                .stream()
                .map(event ->
                        eventMapper.toDTO(
                                event,
                                findEventNodeByUUID(event.getUuid())
                        ))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserJoinsEventMessageDTO addUserToEvent(UUID userUUID, UUID eventUUID) {
        if (!isEventMember(userUUID, eventUUID)) {
            Event event = eventRepository.findByUuid(eventUUID).orElseThrow(() ->
                    new EventNotFoundException(eventUUID));

            if (event.getMaxNumberOfParticipants() == -1 ||
                    event.getMaxNumberOfParticipants() >= event.getNumberOfParticipants() + 1) {
                event.setNumberOfParticipants(event.getNumberOfParticipants() + 1);
                eventRepository.save(event);

                eventParticipantRepository.save(
                        new EventParticipant(getEventReference(eventUUID),
                                accountService.getAccountReference(userUUID),
                                LocalDate.now())
                );

                eventNodeRepository.addUserToEvent(
                        userUUID.toString(),
                        eventUUID.toString()
                );

                eventNodeRepository.removeEventRequest(
                        userUUID.toString(),
                        eventUUID.toString()
                );

                AccountDTO newMember = accountService.findAccountByUUID(userUUID);
                accountService.updateAccount(
                        newMember.uuid(),
                        AccountDTO.builder()
                                .uuid(newMember.uuid())
                                .numberOfTrips(newMember.numberOfTrips() + 1)
                                .build()
                );
            } else throw new EventMembersLimitException();
        }

        return new UserJoinsEventMessageDTO(
                userUUID,
                eventUUID,
                "user has joined the event");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserLeavesEventMessageDTO removeUserFromEvent(UUID userUUID, UUID eventUUID) {
        eventParticipantRepository.userLeftEvent(
                userUUID,
                eventUUID);

        eventNodeRepository.removeUserFromEvent(
                userUUID.toString(),
                eventUUID.toString()
        );

        return new UserLeavesEventMessageDTO(
                userUUID,
                eventUUID,
                "user has left the event");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventNode findEventNodeByUUID(UUID uuid) {
        return eventNodeRepository.findEventNodeByUuid(uuid)
                .orElseThrow(
                () -> new EventNotFoundException(uuid)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPathPointsDTO addUserPathPoints(UserPathPointsDTO userPathPointsDTO) {
        List<Double> coordinates = userPathPointsDTO.pathPoints()
                .stream()
                .flatMap(point -> Stream.of(point.latitude(), point.longitude()))
                .toList();
        eventNodeRepository.updatePathPoints(
                userPathPointsDTO.userUUID().toString(),
                userPathPointsDTO.eventUUID().toString(),
                coordinates);
        return userPathPointsDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRequestEventDTO userAppliesForEvent(UserRequestEventDTO userRequestEventDTO) {
        eventNodeRepository.createEventRequest(
                userRequestEventDTO.userUUID().toString(),
                userRequestEventDTO.eventUUID().toString()
        );

        return new UserRequestEventDTO(
                userRequestEventDTO.userUUID(),
                userRequestEventDTO.eventUUID(),
                "user has applied for the event");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRequestEventDTO removeUserApplyForEvent(UserRequestEventDTO userRequestEventDTO) {
        eventNodeRepository.removeEventRequest(
                userRequestEventDTO.userUUID().toString(),
                userRequestEventDTO.eventUUID().toString()
        );
        return new UserRequestEventDTO(
                userRequestEventDTO.userUUID(),
                userRequestEventDTO.eventUUID(),
                "user refused to join the event"
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AccountThumbnailDTO> findEventRequests(UUID uuid) {
        return eventNodeRepository.findEventRequests(uuid.toString()).stream().map(user ->
                accountService.findAccountThumbnailByUUID(user.getUuid())).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventMultimediaMetadataDTO> findEventMultimedia(UUID eventUUID) {
        List<EventMultimediaMetadataDTO> eventMultimedia = new ArrayList<>();
        eventMultimediaRepository.findEventMultimediaByUUID(eventUUID).forEach(
                multimedia -> {
                    eventMultimedia.add(
                            new EventMultimediaMetadataDTO(
                                    multimedia.getMultimediaUrl(),
                                    multimedia.getLatitude(),
                                    multimedia.getLongitude(),
                                    multimedia.getTimestamp(),
                                    eventMultimediaRepository.findMultimediaProducer(
                                            multimedia.getUuid().toString()).getUuid(),
                                    eventUUID
                            ));
                }
        );
        return eventMultimedia;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public EventMultimediaMetadataDTO uploadEventMultimedia(EventMultimediaMetadataDTO multimediaMetadata, MultipartFile multimedia) {
        UUID multimediaUUID = UUID.randomUUID();
        try {
            String multimediaUrl =
                    multimediaService.uploadMultimedia(
                    multimedia,
                    "events/" + multimediaMetadata.eventUUID() +
                            "/users/" + multimediaMetadata.userUUID() +
                            "/" + multimediaUUID
            );

            EventMultimediaNode eventMultimediaNode
                    = new EventMultimediaNode(
                            multimediaUUID,
                            multimediaUrl,
                            multimediaMetadata.latitude(),
                            multimediaMetadata.longitude(),
                            multimediaMetadata.timestamp(),
                            userService.findUserNodeByUUID(multimediaMetadata.userUUID()),
                            findEventNodeByUUID(multimediaMetadata.eventUUID()));
            eventMultimediaRepository.save(eventMultimediaNode);
            return eventMultimediaMapper.toDTO(eventMultimediaNode);
        } catch(IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserJourneyInEventDTO getUserJourneyInEvent(UUID userUUID, UUID eventUUID) {
        EventMembership membership = userService.findUserNodeByUUID(
                        userUUID).getEvents()
                .stream()
                .filter(event ->
                        event.getEvent().getUuid().equals(eventUUID))
                .findFirst()
                .orElse(null);

        List<PointDTO> coordinates = null;
        if(membership != null) {
            coordinates =
                    IntStream.range(0, membership.getPathPoints().size() / 2)
                            .mapToObj(i -> new PointDTO(
                                    membership.getPathPoints().get(2 * i), // x
                                    membership.getPathPoints().get(2 * i + 1) // y
                            ))
                            .toList();
        }
        return new UserJourneyInEventDTO(
                coordinates,
                eventMultimediaRepository.findUserMultimediaInEvent(userUUID,eventUUID)
                        .stream()
                        .map(multimedia ->
                                eventMultimediaMapper.toDTO(multimedia, userUUID, eventUUID))
                        .toList()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventDTO> getGroupEvents(UUID groupUUID) {
        return eventNodeRepository.getGroupEvents(groupUUID.toString())
                .stream()
                .map(event -> findEventByUUID(
                        event.getUuid()
                )).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AccountThumbnailDTO> getEventMembers(UUID eventUUID) {
        return eventNodeRepository.findEventMembers(eventUUID.toString())
                .stream()
                .map(member ->
                        accountService.findAccountThumbnailByUUID(member.getUuid()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isEventRequestSent(UUID userUUID, UUID eventUUID) {
        return eventNodeRepository.isEventRequestSent(
                userUUID.toString(),
                eventUUID.toString()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isEventMember(UUID userUUID, UUID eventUUID) {
        return eventNodeRepository.isEventMember(
                userUUID.toString(),
                eventUUID.toString()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventThumbnailDTO> getEventsByNameSubstring(String eventNameSubstring) {
        if (eventNameSubstring.isEmpty()) return Collections.emptyList();
        return eventRepository.getEventsByNameSubstring(eventNameSubstring)
                .stream()
                .map(event ->
                        findEventThumbnailByUUID(event.getUuid()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventDTO> getUserAccomplishedEvents(UUID userUUID, int numberOfEvents) {
        return eventParticipantRepository.findUserAccomplishedEvents(
                userUUID,
                numberOfEvents)
                .stream()
                .map(event ->
                        findEventByUUID(event.getUuid()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultimediaDTO updateEventIcon(UUID eventUUID, MultipartFile icon) {
        Event event = eventRepository.findByUuid(eventUUID).orElseThrow(() ->
                new EventNotFoundException(eventUUID));

        if (icon != null) {
            try {
                event.setIconUrl(
                        multimediaService.uploadMultimedia(
                                icon,
                                "events/" + event.getUuid() + "/" + UUID.randomUUID()));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        eventRepository.save(event);
        return new MultimediaDTO(event.getIconUrl());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventDTO> getUserRecommendedEvents(UUID userUUID) {
        return userRecommendationRepository.findUserRecommendedEvents(userUUID)
                .stream()
                .map(event ->
                        findEventByUUID(event.getUuid()))
                .toList();
    }

    /**
     * Metoda zapisująca encję wydarzenia z bazy relacyjnej do węzła w bazie grafowej.
     *
     * @param event encja wydarzenia
     * @param groupUUID globalny, unikalny identyfikator wydarzenia w systemie
     */
    private void saveInGraphDB(Event event, UUID groupUUID) {
        EventNode eventToSave = eventMapper.toNode(event);
        eventToSave.setOwner(userService.findUserNodeByUUID(event.getOwner().getUuid()));

        if  (groupUUID != null) {
            eventToSave.setGroup(
                    groupService.findGroupNodeByUUID(groupUUID)
            );
        }
        eventNodeRepository.save(eventToSave);
    }
}
