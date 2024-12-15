package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;

/**
 * Data transfer object dla encji obserwacji konta.
 *
 * @param follower konto użytkownika obserwującego - {@link AccountThumbnailDTO}
 * @param followed konto użytkownika obserwowanego - {@link AccountThumbnailDTO}
 */
public record FollowDTO (AccountThumbnailDTO follower, AccountThumbnailDTO followed) {
}
