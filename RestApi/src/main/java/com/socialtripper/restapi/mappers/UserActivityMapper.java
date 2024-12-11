package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.UserActivityDTO;
import com.socialtripper.restapi.entities.UserActivity;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją aktywności użytkownika {@link UserActivity},
 * a data transfer object {@link UserActivityDTO}.
 */
@Component
public class UserActivityMapper {
    /**
     * Komponent odpowiedzialny za mapowanie aktywności.
     */
    private final ActivityMapper activityMapper;

    /**
     * Konstruktor wstrzykujący komponent mapujący.
     *
     * @param activityMapper komponent odpowiedzialny za mapowanie aktywności
     */
    public UserActivityMapper(ActivityMapper activityMapper) {
        this.activityMapper = activityMapper;
    }

    /**
     * Metoda mapująca data transfer object aktywności użytkownika do encji.
     *
     * @param userActivityDTO data transfer object aktywności użytkownika
     * @return encja aktywności użytkownika
     */
    public UserActivity toEntity(UserActivityDTO userActivityDTO) {
        if (userActivityDTO == null) return null;
        return new UserActivity(
                userActivityDTO.experience(),
                activityMapper.toEntity(userActivityDTO.activity())
        );
    }

    /**
     * Metoda mapująca encję aktywności użytkownika do data transfer object.
     *
     * @param userActivity encja aktywności użytkownika
     * @return data transfer object aktywności użytkownika
     */
    public UserActivityDTO toDTO(UserActivity userActivity) {
        if (userActivity == null) return null;
        return new UserActivityDTO(
                userActivity.getExperience(),
                activityMapper.toDTO(userActivity.getActivity())
        );
    }
}
