package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.entities.LocationScope;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record GroupDTO (UUID uuid, String name, int numberOfMembers,
                        boolean isPublic, String description, String rules,
                        LocalDate dateOfCreation, String homePageUrl, BigDecimal locationLongitude,
                        BigDecimal locationLatitude, LocationScope locationScope, AccountDTO ownerDTO,
                        MultimediaDTO iconDTO
) {
}
