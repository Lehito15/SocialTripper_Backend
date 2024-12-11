package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventActivityDTO;
import com.socialtripper.restapi.entities.EventActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją aktywności w wydarzeniu {@link EventActivity},
 * a data transfer object {@link EventActivityDTO}.
 */
@Component
public class EventActivityMapper {
    /**
     * Komponent odpowiedzialny za mapowanie aktywności.
     */
    private ActivityMapper activityMapper;

    /**
     * Konstruktor wstrzykujący komponent mapowania aktywności.
     *
     * @param activityMapper komponent mapujący aktywność
     */
    @Autowired
    public void setActivityMapper(ActivityMapper activityMapper) {
        this.activityMapper = activityMapper;
    }


    /**
     * Metoda mapująca data transfer object aktywności w wydarzeniu do encji.
     *
     * @param eventActivityDTO data transfer object aktywności w wydarzeniu
     * @return encja aktywności w wydarzeniu
     */
    public EventActivity toEntity(EventActivityDTO eventActivityDTO) {
        if (eventActivityDTO == null) return null;
        return new EventActivity(
                activityMapper.toEntity(eventActivityDTO.activity()
                ), eventActivityDTO.requiredExperience()
        );
    }

    /**
     * Metoda mapująca encję aktywności w wydarzeniu do data transfer object.
     *
     * @param eventActivity encja aktywności w wydarzeniu
     * @return data transfer object aktywności w wydarzeniu
     */
    public EventActivityDTO toDTO(EventActivity eventActivity) {
        if (eventActivity == null) return null;
        return new EventActivityDTO(
                eventActivity.getRequiredExperience(),
                activityMapper.toDTO(eventActivity.getActivity())
        );
    }
}
