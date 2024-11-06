package com.socialtripper.restapi.dto.entities;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record PostDTO(UUID uuid, String content, LocalDate dateOfPost,
                      boolean isExpired, boolean isLocked, int commentsNumber,
                      int reactionsNumber, UUID accountUUID, Set<PostMultimediaDTO> postMultimediaDTO) {
}
