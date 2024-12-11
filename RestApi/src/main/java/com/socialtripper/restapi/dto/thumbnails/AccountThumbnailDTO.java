package com.socialtripper.restapi.dto.thumbnails;

import java.util.UUID;

/**
 * Data transfer object dla częściowej informacji o koncie.
 *
 * @param uuid globalny, unikalny identyfikator konta w systemie
 * @param nickname nazwa użytkownika
 * @param homePageUrl dostęp do zasobu konta
 * @param description opis konta
 * @param followersNumber liczba użytkowników obserwujących konto
 * @param followingNumber liczba użytkowników obserwowanych
 * @param numberOfTrips liczba wydarzeń związanych z kontem
 * @param isPublic dostęp do konta (publiczny / prywatny)
 * @param profilePictureUrl link do zdjęcia profilowego
 * @param user użytkownik związany z kontem - {@link UserThumbnailDTO}
 */
public record AccountThumbnailDTO(UUID uuid, String nickname, String homePageUrl, String description,
                                  Integer followersNumber, Integer followingNumber, Integer numberOfTrips,
                                  Boolean isPublic, String profilePictureUrl, UserThumbnailDTO user) {
}
