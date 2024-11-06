package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventActivityDTO;
import com.socialtripper.restapi.entities.EventActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventActivityMapper {
    private ActivityMapper activityMapper;
    private EventMapper eventMapper;

    @Autowired
    public void setActivityMapper(ActivityMapper activityMapper) {
        this.activityMapper = activityMapper;
    }

    @Autowired
    public void setEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public EventActivity toEntity(EventActivityDTO eventActivityDTO) {
        if (eventActivityDTO == null) return null;
        return new EventActivity(
                eventActivityDTO.id(),
                eventActivityDTO.requiredExperience(),
                eventMapper.toEntity(eventActivityDTO.eventDTO()),
                activityMapper.toEntity(eventActivityDTO.activityDTO())
        );
    }

    public EventActivityDTO toDTO(EventActivity eventActivity) {
        if (eventActivity == null) return null;
        return new EventActivityDTO(
                eventActivity.getId(),
                eventActivity.getRequiredExperience(),
                eventMapper.toDTO(eventActivity.getEvent()),
                activityMapper.toDTO(eventActivity.getActivity())
        );
    }
}
