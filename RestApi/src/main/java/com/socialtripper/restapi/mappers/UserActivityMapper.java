package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.UserActivityDTO;
import com.socialtripper.restapi.entities.UserActivity;
import org.springframework.stereotype.Component;

@Component
public class UserActivityMapper {
    private final ActivityMapper activityMapper;

    public UserActivityMapper(ActivityMapper activityMapper) {
        this.activityMapper = activityMapper;
    }

    public UserActivity toEntity(UserActivityDTO userActivityDTO) {
        if (userActivityDTO == null) return null;
        return new UserActivity(
                userActivityDTO.experience(),
                activityMapper.toEntity(userActivityDTO.activity())
        );
    }

    public UserActivityDTO toDTO(UserActivity userActivity) {
        if (userActivity == null) return null;
        return new UserActivityDTO(
                userActivity.getExperience(),
                activityMapper.toDTO(userActivity.getActivity())
        );
    }
}
