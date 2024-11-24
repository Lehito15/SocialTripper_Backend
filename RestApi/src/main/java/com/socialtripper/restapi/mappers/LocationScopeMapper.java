package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.LocationScopeDTO;
import com.socialtripper.restapi.entities.LocationScope;
import org.springframework.stereotype.Component;

@Component
public class LocationScopeMapper {
    public LocationScopeDTO toDTO(LocationScope locationScope) {
        if (locationScope == null) return null;
        return new LocationScopeDTO(
                locationScope.getName()
        );
    }
}
