package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.LocationScopeDTO;
import com.socialtripper.restapi.entities.LocationScope;
import org.springframework.stereotype.Component;

@Component
public class LocationScopeMapper {

    public LocationScope toEntity(LocationScopeDTO locationScopeDTO) {
        if (locationScopeDTO == null) return null;
        return new LocationScope(
                locationScopeDTO.id(),
                locationScopeDTO.name()
        );
    }

    public LocationScopeDTO toDTO(LocationScope locationScope) {
        if (locationScope == null) return null;
        return new LocationScopeDTO(
                locationScope.getId(),
                locationScope.getName()
        );
    }
}
