package com.socialtripper.restapi.dto.thumbnails;

import java.util.UUID;


public record GroupThumbnailDTO(UUID uuid, String name, Integer numberOfMembers,
                                String description, String homePageUrl, String iconUrl) {
}
