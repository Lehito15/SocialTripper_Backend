package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.AccountDTO;
import com.socialtripper.restapi.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Komponent odpowiedzialny za mapowanie między obiektami typu {@link Account} oraz {@link AccountDTO}.
 */
@Component
public class AccountMapper {
    /**
     * Komponent odpowiedzialny za mapowanie częściowych danych użytkownika.
     */
    private final UserThumbnailMapper userThumbnailMapper;

    /**
     * Konstruktor wstrzykujący komponent mapujący częściowe dane użytkownika.
     * @param userThumbnailMapper komponent mapujący częściowe informacje o użytkowniku
     */
    @Autowired
    public AccountMapper(UserThumbnailMapper userThumbnailMapper) {
        this.userThumbnailMapper = userThumbnailMapper;
    }

    /**
     * Metoda mapująca data transfer obejct konta do encji nowo zakładanego konta.
     *
     * @param accountDTO data transfer object dla konta
     * @return encja konta
     */
    public Account toNewEntity(AccountDTO accountDTO) {
        if (accountDTO == null) return null;
        return new Account(
                accountDTO.uuid(),
                accountDTO.nickname(),
                accountDTO.email(),
                accountDTO.isPublic(),
                accountDTO.phone(),
                accountDTO.role(),
                false,
                false,
                LocalDate.now(),
                accountDTO.homePageUrl(),
                accountDTO.description(),
                0,
                0,
                0,
                accountDTO.profilePictureUrl()
        );
    }

    /**
     * Metoda mapująca encję konta do data transfer object konta.
     *
     * @param account encja konta
     * @return data transfer object konta
     */
    public AccountDTO toDTO(Account account) {
        if (account == null) return null;
        return new AccountDTO(
                account.getUuid(),
                account.getNickname(),
                account.getEmail(),
                account.isPublic(),
                account.getPhone(),
                account.getRole(),
                account.isExpired(),
                account.isLocked(),
                account.getCreatedAt(),
                account.getHomePageUrl(),
                account.getDescription(),
                account.getFollowersNumber(),
                account.getFollowingNumber(),
                account.getNumberOfTrips(),
                account.getProfilePictureUrl(),
                userThumbnailMapper.toDTO(account.getUser())
        );
    }

    /**
     * Metoda kopiująca pola dto, niezwiązane z innymi encjami do encji konta użytkownika.
     *
     * @param account encja konta, do której mają zostać przekopiowane wartości
     * @param accountDTO data transfer object, z którego kopiowane są pola
     * @return encja konta z przekopiowanymi wartościami, podana jako parametr metody
     */
    public Account copyNonNullValues(Account account, AccountDTO accountDTO) {
        if (accountDTO == null) return null;
        if (accountDTO.uuid() != null) account.setUuid(accountDTO.uuid());
        if (accountDTO.nickname() != null) account.setNickname(accountDTO.nickname());
        if (accountDTO.description() != null) account.setDescription(accountDTO.description());
        if (accountDTO.email() != null) account.setEmail(accountDTO.email());
        if (accountDTO.phone() != null) account.setPhone(accountDTO.phone());
        if (accountDTO.role() != null) account.setRole(accountDTO.role());
        if (accountDTO.isPublic() != null) account.setPublic(accountDTO.isPublic());
        if (accountDTO.isExpired() != null) account.setExpired(accountDTO.isExpired());
        if (accountDTO.isLocked() != null) account.setLocked(accountDTO.isLocked());
        if (accountDTO.createdAt() != null) account.setCreatedAt(accountDTO.createdAt());
        if (accountDTO.homePageUrl() != null) account.setHomePageUrl(accountDTO.homePageUrl());
        if (accountDTO.followersNumber() != null) account.setFollowersNumber(accountDTO.followersNumber());
        if (accountDTO.followingNumber() != null) account.setFollowingNumber(accountDTO.followingNumber());
        if (accountDTO.numberOfTrips() != null) account.setNumberOfTrips(accountDTO.numberOfTrips());
        return account;
    }
}
