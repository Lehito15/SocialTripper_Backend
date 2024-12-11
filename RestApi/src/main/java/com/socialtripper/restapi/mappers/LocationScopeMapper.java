package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.LocationScopeDTO;
import com.socialtripper.restapi.entities.LocationScope;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją zasięgu grupy {@link LocationScope},
 * a data transfer object {@link LocationScopeDTO}.
 */
@Component
public class LocationScopeMapper {
    /**
     * Metoda mapująca encję zasięgu grupy do data transfer object.
     *
     * @param locationScope encja zasięgu grupy
     * @return data transfer object zasięgu grupy
     */
    public LocationScopeDTO toDTO(LocationScope locationScope) {
        if (locationScope == null) return null;
        return new LocationScopeDTO(
                locationScope.getName()
        );
    }
}
