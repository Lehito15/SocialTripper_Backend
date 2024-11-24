package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.entities.LocationScope;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record GroupDTO (UUID uuid, String name, int numberOfMembers,
                        Boolean isPublic, String description, String rules,
                        LocalDate dateOfCreation, String homePageUrl, BigDecimal locationLongitude,
                        BigDecimal locationLatitude, LocationScopeDTO locationScope, AccountThumbnailDTO owner,
                        String iconUrl, Set<ActivityDTO> activities, Set<LanguageDTO> languages
) {
}
