package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.AccountDTO;
import com.socialtripper.restapi.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AccountMapper {
    private final UserThumbnailMapper userThumbnailMapper;

    @Autowired
    public AccountMapper(UserThumbnailMapper userThumbnailMapper) {
        this.userThumbnailMapper = userThumbnailMapper;
    }

    public Account toNewEntity(AccountDTO accountDTO) {
        if (accountDTO == null) return null;
        return new Account(
                accountDTO.uuid(),
                accountDTO.nickname(),
                accountDTO.email(),
                accountDTO.isPublic(),
                accountDTO.salt(),
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

    public AccountDTO toDTO(Account account) {
        if (account == null) return null;
        return new AccountDTO(
                account.getUuid(),
                account.getNickname(),
                account.getEmail(),
                account.isPublic(),
                account.getSalt(),
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

    public Account copyNonNullValues(Account account, AccountDTO accountDTO) {
        if (accountDTO == null) return null;
        if (accountDTO.uuid() != null) account.setUuid(accountDTO.uuid());
        if (accountDTO.nickname() != null) account.setNickname(accountDTO.nickname());
        if (accountDTO.email() != null) account.setEmail(accountDTO.email());
        if (accountDTO.salt() != null) account.setSalt(accountDTO.salt());
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
