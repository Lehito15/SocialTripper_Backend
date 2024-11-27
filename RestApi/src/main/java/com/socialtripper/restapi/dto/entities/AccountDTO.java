package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.dto.thumbnails.UserThumbnailDTO;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record AccountDTO(UUID uuid, String nickname, String email, Boolean isPublic, String salt,
                         String phone, String role, Boolean isExpired, Boolean isLocked, LocalDate createdAt,
                         String homePageUrl, String description, Integer followersNumber, Integer followingNumber,
                         Integer numberOfTrips, String profilePictureUrl, UserThumbnailDTO user
) {
}
