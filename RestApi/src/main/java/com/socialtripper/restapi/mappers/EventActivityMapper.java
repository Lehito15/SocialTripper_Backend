package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventActivityDTO;
import com.socialtripper.restapi.entities.EventActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventActivityMapper {
    private ActivityMapper activityMapper;

    @Autowired
    public void setActivityMapper(ActivityMapper activityMapper) {
        this.activityMapper = activityMapper;
    }


    public EventActivity toEntity(EventActivityDTO eventActivityDTO) {
        if (eventActivityDTO == null) return null;
        return new EventActivity(
                activityMapper.toEntity(eventActivityDTO.activity()
                ), eventActivityDTO.requiredExperience()
        );
    }

    public EventActivityDTO toDTO(EventActivity eventActivity) {
        if (eventActivity == null) return null;
        return new EventActivityDTO(
                eventActivity.getRequiredExperience(),
                activityMapper.toDTO(eventActivity.getActivity())
        );
    }
}
