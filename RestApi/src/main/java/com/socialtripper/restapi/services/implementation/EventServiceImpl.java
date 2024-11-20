package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.dto.entities.*;
import com.socialtripper.restapi.dto.messages.EventStatusChangedMessageDTO;
import com.socialtripper.restapi.dto.messages.UserJoinsEventMessageDTO;
import com.socialtripper.restapi.dto.messages.UserLeavesEventMessageDTO;
import com.socialtripper.restapi.dto.requests.EventMultimediaMetadataDTO;
import com.socialtripper.restapi.dto.requests.UserPathPointsDTO;
import com.socialtripper.restapi.dto.requests.UserRequestEventDTO;
import com.socialtripper.restapi.dto.thumbnails.UserJourneyInEventDTO;
import com.socialtripper.restapi.entities.*;
import com.socialtripper.restapi.entities.enums.EventStatuses;
import com.socialtripper.restapi.exceptions.EventNotFoundException;
import com.socialtripper.restapi.mappers.EventMapper;
import com.socialtripper.restapi.mappers.EventMultimediaMapper;
import com.socialtripper.restapi.mappers.GroupEventMapper;
import com.socialtripper.restapi.nodes.EventMultimediaNode;
import com.socialtripper.restapi.nodes.EventNode;
import com.socialtripper.restapi.nodes.EventMembership;
import com.socialtripper.restapi.nodes.UserNode;
import com.socialtripper.restapi.repositories.graph.EventMembershipRepository;
import com.socialtripper.restapi.repositories.graph.EventMultimediaRepository;
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
    private final EventMultimediaRepository eventMultimediaRepository;
    private final EventMembershipRepository eventMembershipRepository;
    private final EventMapper eventMapper;
    private final GroupEventMapper groupEventMapper;
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
                            UserService userService, EventMultimediaRepository eventMultimediaRepository,
                            EventMultimediaMapper eventMultimediaMapper, EventMembershipRepository eventMembershipRepository) {
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
        this.eventMembershipRepository = eventMembershipRepository;
    }

    @Override
    public List<EventDTO> findAllEvents() {
        return eventRepository.findAll().stream().map(eventMapper::toDTO).toList();
    }

    @Override
    public EventDTO findEventByUUID(UUID uuid) {
        return eventMapper.toDTO(
                eventRepository.findByUuid(uuid)
                        .orElseThrow(() -> new EventNotFoundException(uuid)));
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
        Event event = eventMapper.toEntity(eventDTO);
        event.setUuid(UUID.randomUUID());
        event.setDateOfCreation(LocalDate.now());
        event.setHomePageUrl("http://events/" + event.getUuid());
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
        saveInGraphDB(savedEvent);
        addUserToEvent(savedEvent.getOwner().getUuid(), savedEvent.getUuid());
        return eventMapper.toDTO(eventToSave);
    }

    @Override
    public GroupEventDTO createGroupEvent(GroupEventDTO groupEventDTO) {
        GroupEvent eventToSave = new GroupEvent(getNewEventWithReferences(groupEventDTO.eventDTO(),
                                                    groupEventDTO.eventDTO().owner().uuid()),
                groupService.getGroupReference(groupEventDTO.groupUUID()));

        GroupEvent savedEvent = groupEventRepository.save(eventToSave);

        groupEventDTO.eventDTO().activities().forEach(
                (activity) -> setActivity(savedEvent.getEvent().getUuid(), activity)
        );

        groupEventDTO.eventDTO().languages().forEach(
                (language) -> setLanguage(savedEvent.getEvent().getUuid(), language)
        );

        return groupEventMapper.toDTO(savedEvent);
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
        return eventMapper.toDTO(eventRepository.save(event));
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
    public List<EventDTO> findUserEventsHistory(UUID uuid) {
        return eventParticipantRepository.findUserEvents(uuid).stream().map(eventMapper::toDTO).toList();
    }

    @Override
    public List<EventDTO> findUserUpcomingEvents(UUID uuid) {
        return eventParticipantRepository.findUserUpcomingEvents(uuid).stream().map(eventMapper::toDTO).toList();
    }

    @Override
    public UserJoinsEventMessageDTO addUserToEvent(UUID userUUID, UUID eventUUID) {
        eventParticipantRepository.save(
                new EventParticipant(getEventReference(eventUUID),
                        accountService.getAccountReference(userUUID),
                        LocalDate.now())
        );


        UserNode user = userService.findUserNodeByUUID(userUUID);
        user.getEvents().add(
                new EventMembership(
                        new ArrayList<>(),
                        findEventNodeByUUID(eventUUID)));
        user.getAppliedEvents().removeIf(event -> event.getUuid().equals(eventUUID));
        userService.saveUserInGraphDB(user);

        return new UserJoinsEventMessageDTO(userUUID, eventUUID, "user has joined the event");
    }

    @Override
    public UserLeavesEventMessageDTO removeUserFromEvent(UUID userUUID, UUID eventUUID) {
        eventParticipantRepository.userLeftEvent(userUUID, eventUUID);
        UserNode user = userService.findUserNodeByUUID(userUUID);
        user.getEvents().removeIf(event -> event.getEvent().getUuid().equals(eventUUID));
        userService.saveUserInGraphDB(user);

        return new UserLeavesEventMessageDTO(userUUID, eventUUID, "user has left the event");
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
            eventNodeRepository.updatePathPoints(userPathPointsDTO.userUUID(),
                    userPathPointsDTO.eventUUID(),
                    coordinate);
        }
        return userPathPointsDTO;
    }

    @Override
    public UserRequestEventDTO userAppliesForEvent(UserRequestEventDTO userRequestEventDTO) {
        UserNode applyingUser = userService.findUserNodeByUUID(userRequestEventDTO.userUUID());
        applyingUser.getAppliedEvents().add(findEventNodeByUUID(userRequestEventDTO.eventUUID()));
        userService.saveUserInGraphDB(applyingUser);
        return new UserRequestEventDTO(
                userRequestEventDTO.userUUID(),
                userRequestEventDTO.eventUUID(),
                "user has applied for the event");
    }

    @Override
    public List<Map<String, Object>> findEventRequest(UUID uuid) {
        return eventNodeRepository.findEventRequests(uuid);
    }

    @Override
    public void removeEventRequest(UUID userUUID, UUID eventUUID) {
        eventNodeRepository.removeEventRequest(userUUID, eventUUID);
    }

    @Override
    public List<EventMultimediaMetadataDTO> findEventMultimedia(UUID eventUUID) {
        return eventMultimediaRepository.findEventMultimediaByUUID(eventUUID)
                            .stream()
                            .map(eventMultimediaMapper::toDTO).toList();
    }

    @Override
    public EventMultimediaMetadataDTO uploadEventMultimedia(EventMultimediaMetadataDTO multimediaMetadata, MultipartFile multimedia) {
        try {
            String multimediaUrl =
                    multimediaService.uploadMultimedia(
                    multimedia,
                    "events/" + multimediaMetadata.eventUUID() +
                            "/users/" + multimediaMetadata.userUUID() +
                            "/" + UUID.randomUUID()
            );


            EventMultimediaNode eventMultimediaNode
                    = new EventMultimediaNode(
                    multimediaUrl,
                    multimediaMetadata.latitude(),
                    multimediaMetadata.longitude(),
                    multimediaMetadata.timestamp(),
                    findEventNodeByUUID(multimediaMetadata.eventUUID()),
                    userService.findUserNodeByUUID(multimediaMetadata.userUUID())
            );

            EventMultimediaNode savedMultimedia = eventMultimediaRepository.save(eventMultimediaNode);
            eventMultimediaRepository.attachUserToMultimedia(multimediaMetadata.userUUID(),savedMultimedia.getId());

            return eventMultimediaMapper.toDTO(eventMultimediaNode);
        } catch(IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public UserJourneyInEventDTO getUserJourneyInEvent(UUID userUUID, UUID eventUUID) {
        List<Double> pathPoints = eventMembershipRepository.getUserPathPointsInEvent(userUUID, eventUUID);

        List<Point> coordinates =
                IntStream.range(0, pathPoints.size() / 2)
                .mapToObj(i -> new Point(
                        pathPoints.get(2 * i).intValue(), // x
                        pathPoints.get(2 * i + 1).intValue() // y
                ))
                .toList();

        return new UserJourneyInEventDTO(
                coordinates,
                eventMultimediaRepository.findUserMultimediaInEvent(userUUID,eventUUID)
                        .stream()
                        .map(eventMultimediaMapper::toDTO)
                        .toList()
        );
    }


    private void saveInGraphDB(Event event) {
        EventNode eventToSave = eventMapper.toNode(event);
        eventToSave.setOwner(userService.findUserNodeByUUID(event.getOwner().getUuid()));
        eventNodeRepository.save(eventToSave);
    }
}
