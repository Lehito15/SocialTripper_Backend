package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record PostDTO(UUID uuid, String content, LocalDateTime dateOfPost,
                      Boolean isExpired, Boolean isLocked, Integer commentsNumber,
                      Integer reactionsNumber, AccountThumbnailDTO account,
                      Set<PostMultimediaDTO> postMultimedia) {
}
