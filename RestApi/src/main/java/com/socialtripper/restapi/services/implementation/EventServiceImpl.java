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
import com.socialtripper.restapi.dto.thumbnails.UserJourneyInEventDTO;
import com.socialtripper.restapi.entities.*;
import com.socialtripper.restapi.entities.enums.EventStatuses;
import com.socialtripper.restapi.exceptions.EventNotFoundException;
import com.socialtripper.restapi.mappers.EventMapper;
import com.socialtripper.restapi.mappers.EventMultimediaMapper;
import com.socialtripper.restapi.mappers.EventThumbnailMapper;
import com.socialtripper.restapi.mappers.GroupEventMapper;
import com.socialtripper.restapi.nodes.EventMultimediaNode;
import com.socialtripper.restapi.nodes.EventNode;
import com.socialtripper.restapi.nodes.EventMembership;
import com.socialtripper.restapi.repositories.graph.EventMultimediaNodeRepository;
import com.socialtripper.restapi.repositories.graph.EventNodeRepository;
import com.socialtripper.restapi.repositories.relational.*;
import com.socialtripper.restapi.services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;


@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventNodeRepository eventNodeRepository;
    private final GroupEventRepository groupEventRepository;
    private final EventActivityRepository eventActivityRepository;
    private final EventLanguageRepository eventLanguageRepository;
    private final EventMultimediaNodeRepository eventMultimediaRepository;
    private final EventMapper eventMapper;
    private final GroupEventMapper groupEventMapper;
    private final EventThumbnailMapper eventThumbnailMapper;
    private final EventMultimediaMapper eventMultimediaMapper;
    private final AccountService accountService;
    private final UserService userService;
    private final GroupService groupService;
    private final EventStatusService eventStatusService;
    private final ActivityService activityService;
    private final LanguageService languageService;
    private final EventParticipantRepository eventParticipantRepository;
    private final MultimediaService multimediaService;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper,
                            AccountService accountService, GroupService groupService,
                            GroupEventMapper groupEventMapper, GroupEventRepository groupEventRepository,
                            EventStatusService eventStatusService, ActivityService activityService,
                            LanguageService languageService, EventActivityRepository eventActivityRepository,
                            EventLanguageRepository eventLanguageRepository, EventParticipantRepository eventParticipantRepository,
                            EventNodeRepository eventNodeRepository, MultimediaService multimediaService,
                            UserService userService, EventMultimediaNodeRepository eventMultimediaRepository,
                            EventMultimediaMapper eventMultimediaMapper, EventThumbnailMapper eventThumbnailMapper) {
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
    }

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

    @Override
    public EventDTO findEventByUUID(UUID uuid) {
        return eventMapper.toDTO(
                eventRepository.findByUuid(uuid)
                        .orElseThrow(() -> new EventNotFoundException(uuid)),
                findEventNodeByUUID(uuid));
    }

    @Override
    public EventThumbnailDTO findEventThumbnailByUUID(UUID uuid) {
        return eventThumbnailMapper.toDTO(
                findEventByUUID(uuid)
        );
    }

    @Override
    public Long getEventIdByUUID(UUID uuid) {
        return eventRepository.findIdByUUID(uuid).orElseThrow(() -> new EventNotFoundException(uuid));
    }

    @Override
    public Event getEventReference(UUID uuid) {
        return eventRepository.getReferenceById(getEventIdByUUID(uuid));
    }


    private Event getNewEventWithReferences(EventDTO eventDTO, UUID userUUID) {
        Event event = eventMapper.toNewEntity(eventDTO);
        event.setUuid(UUID.randomUUID());
        event.setName(eventDTO.name());
        event.setDateOfCreation(LocalDate.now());
        event.setHomePageUrl("/events/" + event.getUuid());
        event.setOwner(accountService.getAccountReference(userUUID));
        event.setEventStatus(eventStatusService.getEventStatusReference(EventStatuses.CREATED.getCode()));
        return event;
    }


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

    @Override
    public EventStatusChangedMessageDTO setStatus(UUID uuid, String status) {
        Event event = getEventReference(uuid);
        event.setEventStatus(
                eventStatusService.getEventStatusReference(
                        EventStatuses.fromStatus(status).orElseThrow(IllegalArgumentException::new).getCode()));
        eventRepository.save(event);
        return new EventStatusChangedMessageDTO(uuid, status);
    }

    @Override
    public EventDTO updateEvent(UUID uuid, EventDTO eventDTO) {
        Event event = eventMapper.copyNonNullValues(getEventReference(uuid), eventDTO);
        return eventMapper.toDTO(
                eventRepository.save(event),
                findEventNodeByUUID(uuid));
    }

    @Override
    public EventActivity setActivity(UUID uuid, EventActivityDTO eventActivityDTO) {
        EventActivity eventActivity = new EventActivity(
                getEventReference(uuid),
                activityService.getActivityReference(eventActivityDTO.activity().name()),
                eventActivityDTO.requiredExperience());
        return eventActivityRepository.save(eventActivity);
    }


    @Override
    public EventLanguage setLanguage(UUID uuid, EventLanguageDTO eventLanguageDTO) {
        EventLanguage eventLanguage = new EventLanguage(
                getEventReference(uuid),
                languageService.getLanguageReference(eventLanguageDTO.language().name()),
                eventLanguageDTO.requiredLevel()
        );
        return eventLanguageRepository.save(eventLanguage);
    }

    @Override
    public List<EventThumbnailDTO> findUserEventsHistory(UUID uuid) {
        return eventParticipantRepository.findUserEvents(uuid)
                .stream()
                .map(event ->
                        eventThumbnailMapper.toDTO(
                                event,
                                findEventNodeByUUID(event.getUuid())
                        ))
                .toList();
    }

    @Override
    public List<EventThumbnailDTO> findUserUpcomingEvents(UUID uuid) {
        return eventParticipantRepository.findUserUpcomingEvents(uuid)
                .stream()
                .map(event ->
                        eventThumbnailMapper.toDTO(
                                event,
                                findEventNodeByUUID(uuid)
                        ))
                .toList();
    }

    @Override
    public UserJoinsEventMessageDTO addUserToEvent(UUID userUUID, UUID eventUUID) {
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

        return new UserJoinsEventMessageDTO(
                userUUID,
                eventUUID,
                "user has joined the event");
    }

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

    @Override
    public EventNode findEventNodeByUUID(UUID uuid) {
        return eventNodeRepository.findEventNodeByUuid(uuid)
                .orElseThrow(
                () -> new EventNotFoundException(uuid)
        );
    }

    @Override
    public UserPathPointsDTO addUserPathPoints(UserPathPointsDTO userPathPointsDTO) {
        for(List<Double> coordinate: userPathPointsDTO.pathPoints()) {
            eventNodeRepository.updatePathPoints(
                    userPathPointsDTO.userUUID().toString(),
                    userPathPointsDTO.eventUUID().toString(),
                    coordinate);
        }
        return userPathPointsDTO;
    }

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

    @Override
    public List<AccountThumbnailDTO> findEventRequest(UUID uuid) {
        return eventNodeRepository.findEventRequests(uuid.toString()).stream().map(user ->
                accountService.findAccountThumbnailByUUID(user.getUuid())).toList();
    }

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

    @Override
    public UserJourneyInEventDTO getUserJourneyInEvent(UUID userUUID, UUID eventUUID) {
        EventMembership membership = userService.findUserNodeByUUID(
                userUUID).getEvents()
                .stream()
                .filter(event ->
                        event.getEvent().getUuid().equals(eventUUID))
                .findFirst()
                .orElse(null);

        List<Point> coordinates = null;
        if(membership != null) {
            coordinates =
                    IntStream.range(0, membership.getPathPoints().size() / 2)
                            .mapToObj(i -> new Point(
                                    membership.getPathPoints().get(2 * i).intValue(), // x
                                    membership.getPathPoints().get(2 * i + 1).intValue() // y
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

    @Override
    public List<EventThumbnailDTO> getGroupEvents(UUID groupUUID) {
        return eventNodeRepository.getGroupEvents(groupUUID.toString())
                .stream()
                .map(event -> findEventThumbnailByUUID(
                        event.getUuid()
                )).toList();
    }

    @Override
    public List<AccountThumbnailDTO> getEventMembers(UUID eventUUID) {
        return eventNodeRepository.findEventMembers(eventUUID.toString())
                .stream()
                .map(member ->
                        accountService.findAccountThumbnailByUUID(member.getUuid()))
                .toList();
    }

    @Override
    public Boolean isEventRequestSent(UUID userUUID, UUID eventUUID) {
        return eventNodeRepository.isEventRequestSent(
                userUUID.toString(),
                eventUUID.toString()
        );
    }

    @Override
    public Boolean isEventMember(UUID userUUID, UUID eventUUID) {
        return eventNodeRepository.isEventMember(
                userUUID.toString(),
                eventUUID.toString()
        );
    }

    @Override
    public List<EventThumbnailDTO> getEventsByNameSubstring(String eventNameSubstring) {
        if (eventNameSubstring.isEmpty()) return Collections.emptyList();
        return eventRepository.getEventsByNameSubstring(eventNameSubstring)
                .stream()
                .map(event ->
                        findEventThumbnailByUUID(event.getUuid()))
                .toList();
    }

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
