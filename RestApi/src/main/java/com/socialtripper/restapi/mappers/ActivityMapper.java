package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.ActivityDTO;
import com.socialtripper.restapi.entities.Activity;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie między encją aktywności {@link Activity},
 * a data transfer object {@link ActivityDTO}.
 */
@Component
public class ActivityMapper {

    /**
     * Metoda mapująca data transfer object aktywności do encji aktywności.
     *
     * @param activityDTO data transfer object aktywności
     * @return encja aktywności
     */
    public Activity toEntity(ActivityDTO activityDTO) {
        if (activityDTO == null) return null;
        return new Activity(
                activityDTO.id(),
                activityDTO.name()
        );
    }

    /**
     * Metoda mapująca encję aktywności do data transfer object aktwności.
     *
     * @param activity encja aktywności
     * @return data transfer object aktywności
     */
    public ActivityDTO toDTO(Activity activity) {
        if (activity == null) return null;
        return new ActivityDTO(
                activity.getId(),
                activity.getName()
        );
    }
}
