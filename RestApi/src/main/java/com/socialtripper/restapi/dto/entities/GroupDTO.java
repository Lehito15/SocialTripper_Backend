package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.entities.LocationScope;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record GroupDTO (UUID uuid, String name, Integer numberOfMembers,
                        Boolean isPublic, String description, String rules,
                        LocalDate dateOfCreation, String homePageUrl, BigDecimal locationLongitude,
                        BigDecimal locationLatitude, LocationScope locationScope, AccountThumbnailDTO owner,
                        MultimediaDTO icon, Set<ActivityDTO> activities, Set<LanguageDTO> languages
) {
}
