package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.dto.entities.EventActivityDTO;
import com.socialtripper.restapi.dto.entities.EventDTO;
import com.socialtripper.restapi.dto.entities.EventLanguageDTO;
import com.socialtripper.restapi.dto.entities.GroupEventDTO;
import com.socialtripper.restapi.dto.messages.EventStatusChangedMessageDTO;
import com.socialtripper.restapi.dto.messages.UserJoinsEventMessageDTO;
import com.socialtripper.restapi.dto.messages.UserLeavesEventMessageDTO;
import com.socialtripper.restapi.entities.*;
import com.socialtripper.restapi.entities.enums.EventStatuses;
import com.socialtripper.restapi.exceptions.EventNotFoundException;
import com.socialtripper.restapi.mappers.EventMapper;
import com.socialtripper.restapi.mappers.GroupEventMapper;
import com.socialtripper.restapi.repositories.*;
import com.socialtripper.restapi.services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final GroupEventRepository groupEventRepository;
    private final EventActivityRepository eventActivityRepository;
    private final EventLanguageRepository eventLanguageRepository;
    private final EventMapper eventMapper;
    private final GroupEventMapper groupEventMapper;
    private final AccountService accountService;
    private final GroupService groupService;
    private final EventStatusService eventStatusService;
    private final ActivityService activityService;
    private final LanguageService languageService;
    private final EventParticipantRepository eventParticipantRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper,
                            AccountService accountService, GroupService groupService,
                            GroupEventMapper groupEventMapper, GroupEventRepository groupEventRepository,
                            EventStatusService eventStatusService, ActivityService activityService,
                            LanguageService languageService, EventActivityRepository eventActivityRepository,
                            EventLanguageRepository eventLanguageRepository, EventParticipantRepository eventParticipantRepository) {
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
        event.setOwner(accountService.getAccountReference(userUUID));
        event.setEventStatus(eventStatusService.getEventStatusReference(EventStatuses.CREATED.getCode()));
        return event;
    }


    @Override
    @Transactional
    public EventDTO createEvent(EventDTO eventDTO) {
        Event eventToSave = getNewEventWithReferences(eventDTO, eventDTO.owner().uuid());
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
        return new UserJoinsEventMessageDTO(userUUID, eventUUID, "user has joined the event");
    }

    @Override
    public UserLeavesEventMessageDTO removeUserFromEvent(UUID userUUID, UUID eventUUID) {
        eventParticipantRepository.userLeftEvent(userUUID, eventUUID);
        return new UserLeavesEventMessageDTO(userUUID, eventUUID, "user has left the event");
    }
}
