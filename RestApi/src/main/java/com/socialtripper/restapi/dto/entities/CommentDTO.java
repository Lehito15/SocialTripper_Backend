package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data trasfer object dla węzła komentarza.
 *
 * @param uuid globalny, unikalny identyfikator komentarza w systemie
 * @param content zawartość tekstowa komentarza
 * @param timestamp znacznik czasowy dodania komentarza
 * @param reactionsNumber liczba reakcji na komentarz
 * @param commentedPost skomentowany post - {@link PostDTO}
 * @param account autor komentarza - {@link AccountThumbnailDTO}
 */
public record CommentDTO(UUID uuid, String content, LocalDateTime timestamp,
                         int reactionsNumber, PostDTO commentedPost,
                         AccountThumbnailDTO account) {
}
