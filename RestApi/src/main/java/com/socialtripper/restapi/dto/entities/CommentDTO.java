package com.socialtripper.restapi.dto.entities;

import java.time.LocalDateTime;

public record CommentDTO(String content, LocalDateTime timestamp, int reactionsNumber,
                         PostDTO commentedPost) {
}
