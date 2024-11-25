package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentDTO(UUID uuid, String content, LocalDateTime timestamp,
                         int reactionsNumber, PostDTO commentedPost,
                         AccountThumbnailDTO account) {
}
