package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountThumbnailMapper {
    private final MultimediaMapper multimediaMapper;

    @Autowired
    public AccountThumbnailMapper(MultimediaMapper multimediaMapper) {
        this.multimediaMapper = multimediaMapper;
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
                multimediaMapper.toDTO(account.getProfilePicture())
        );
    }
}
