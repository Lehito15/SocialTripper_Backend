package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Data transfer object dla encji postu.
 *
 * @param uuid globalny, unikalny identyfikator postu w systemie.
 * @param content treść tekstowa postu
 * @param isPublic informacja o dostępności postu (publiczny / prywatny)
 * @param dateOfPost data zamieszczenia postu
 * @param isExpired informacja czy post jest usunięty przez autora
 * @param isLocked informacja czy post jest zablokowany
 * @param commentsNumber aktualna liczba komentarzy
 * @param reactionsNumber aktualna liczba reakcji na post
 * @param account autor postu - {@link AccountThumbnailDTO}
 * @param postMultimediaUrls linki do multimediów związanych z postem
 */
public record PostDTO(UUID uuid, String content, Boolean isPublic, LocalDateTime dateOfPost,
                      Boolean isExpired, Boolean isLocked, Integer commentsNumber,
                      Integer reactionsNumber, AccountThumbnailDTO account,
                      Set<String> postMultimediaUrls) {
}
