package com.socialtripper.restapi.dto.thumbnails;

import com.socialtripper.restapi.dto.entities.MultimediaDTO;
import java.util.UUID;


public record GroupThumbnailDTO(UUID uuid, String name, Integer numberOfMembers,
                                String description, String homePageUrl, MultimediaDTO iconDTO) {
}
