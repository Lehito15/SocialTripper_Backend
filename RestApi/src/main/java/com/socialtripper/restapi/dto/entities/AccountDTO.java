package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.dto.thumbnails.UserThumbnailDTO;

import java.time.LocalDate;
import java.util.UUID;

public record AccountDTO(UUID uuid, String nickname, String email, boolean isPublic, String salt,
                         String phone, String role, boolean isExpired, boolean isLocked, LocalDate createdAt,
                         String homePageUrl, String description, int followersNumber, int followingNumber,
                         int numberOfTrips, String profilePictureUrl, UserThumbnailDTO user
) {
}
