package com.socialtripper.restapi.dto.thumbnails;

import com.socialtripper.restapi.dto.entities.MultimediaDTO;

import java.util.UUID;

public record AccountThumbnailDTO(UUID uuid, String nickname, String homePageUrl, String description,
                                  Integer followersNumber, Integer followingNumber, Integer numberOfTrips,
                                  Boolean isPublic, MultimediaDTO profilePicture) {
}
