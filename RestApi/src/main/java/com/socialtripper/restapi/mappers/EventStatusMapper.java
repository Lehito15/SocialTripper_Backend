package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventStatusDTO;
import com.socialtripper.restapi.entities.EventStatus;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją statusu wydarzenia {@link EventStatus},
 * a data transfer object {@link EventStatusDTO}
 */
@Component
public class EventStatusMapper {
    /**
     * Metoda mapująca encję statusu wydarzenia do data transfer object.
     *
     * @param eventStatus encja statusu wydarzenia
     * @return data transfer object statusu wydarzenia
     */
    public EventStatusDTO toDTO(EventStatus eventStatus) {
        if (eventStatus == null) return null;
        return new EventStatusDTO(
                eventStatus.getName()
        );
    }
}
