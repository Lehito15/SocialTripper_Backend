package com.socialtripper.restapi.dto.thumbnails;

import java.util.UUID;

public record AccountThumbnailDTO(UUID uuid, String nickname, String homePageUrl, String description,
                                  Integer followersNumber, Integer followingNumber, Integer numberOfTrips,
                                  Boolean isPublic, String profilePictureUrl, UserThumbnailDTO user) {
}
