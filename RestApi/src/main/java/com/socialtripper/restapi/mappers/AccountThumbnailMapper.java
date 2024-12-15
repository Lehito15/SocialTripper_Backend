package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie między encją konta,
 * a data transfer object dla częściowych informacji o koncie.
 */
@Component
public class AccountThumbnailMapper {
    /**
     * Komponent odpowiedzialny za mapowanie częściowych danych użytkownika.
     */
    private final UserThumbnailMapper userThumbnailMapper;

    /**
     * Konstruktor wstrzykujący komponent mapujący częściowe informacje o użytkowniku.
     * @param userThumbnailMapper komponent mapujacy częściowe informacje o użytkowniku
     */
    @Autowired
    public AccountThumbnailMapper(UserThumbnailMapper userThumbnailMapper) {
        this.userThumbnailMapper = userThumbnailMapper;
    }

    /**
     * Metoda mapująca encję konta do data transfer object
     * częściowych danych o koncie użytkownika.
     *
     * @param account encja konta
     * @return data transfer object konta użytkownika
     */
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
