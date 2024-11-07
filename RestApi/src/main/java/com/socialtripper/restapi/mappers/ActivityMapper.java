package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.ActivityDTO;
import com.socialtripper.restapi.entities.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public Activity toEntity(ActivityDTO activityDTO) {
        if (activityDTO == null) return null;
        return new Activity(
                activityDTO.id(),
                activityDTO.name()
        );
    }

    public ActivityDTO toDTO(Activity activity) {
        if (activity == null) return null;
        return new ActivityDTO(
                activity.getId(),
                activity.getName()
        );
    }
}
