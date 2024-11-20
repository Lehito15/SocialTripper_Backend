package com.socialtripper.restapi.dto.entities;

import java.time.LocalDate;
import java.util.UUID;

public record AccountDTO(UUID uuid, String nickname, String email, boolean isPublic, String salt,
                         String phone, String role, boolean isExpired, boolean isLocked, LocalDate createdAt,
                         String homePageUrl, String description, int followersNumber, int followingNumber,
                         int numberOfTrips, String profilePictureUrl
) {
}
