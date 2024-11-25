package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountThumbnailMapper {
    public AccountThumbnailDTO toDTO(Account account) {
        if (account == null) return null;
        return new AccountThumbnailDTO(
                account.getUuid(),
                account.getNickname(),
                account.getHomePageUrl(),
                account.getDescription(),
                account.getFollowersNumber(),
                account.getFollowingNumber(),
                account.getNumberOfTrips(),
                account.isPublic(),
                account.getProfilePictureUrl()
        );
    }
}
