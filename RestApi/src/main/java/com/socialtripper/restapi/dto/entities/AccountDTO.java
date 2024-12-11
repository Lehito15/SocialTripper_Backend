package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.dto.thumbnails.UserThumbnailDTO;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Data transfer object dla encji konta.
 *
 * @param uuid globalny, unikalny identyfikator konta w systemie
 * @param nickname nazwa użytkownika
 * @param email adres email przypisany do konta
 * @param isPublic inforamacja o dostępie do konta (publiczne / prywatne)
 * @param phone numer telefonu powiązany z kontem
 * @param role rola użytkownika w systemie
 * @param isExpired informacja czy konto jest zawieszone
 * @param isLocked informacja czy konto jest zablokowane
 * @param createdAt data utworzenia konta
 * @param homePageUrl dostęp do zasobu konta
 * @param description opis konta
 * @param followersNumber liczba użytkowników obserwujących konto
 * @param followingNumber liczba użytkowników obserwowanych
 * @param numberOfTrips liczba wydarzeń związanych z kontem
 * @param profilePictureUrl link do zdjęcia profilowego
 * @param user informacje o użytkowniku - {@link UserThumbnailDTO}
 */
@Builder
public record AccountDTO(UUID uuid, String nickname, String email, Boolean isPublic,
                         String phone, String role, Boolean isExpired, Boolean isLocked, LocalDate createdAt,
                         String homePageUrl, String description, Integer followersNumber, Integer followingNumber,
                         Integer numberOfTrips, String profilePictureUrl, UserThumbnailDTO user
) {
}
