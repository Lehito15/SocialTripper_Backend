package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountThumbnailMapper {
    private final UserThumbnailMapper userThumbnailMapper;

    @Autowired
    public AccountThumbnailMapper(UserThumbnailMapper userMapper) {
        this.userThumbnailMapper = userMapper;
    }

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
                account.getProfilePictureUrl(),
                userThumbnailMapper.toDTO(account.getUser())
        );
    }
}
