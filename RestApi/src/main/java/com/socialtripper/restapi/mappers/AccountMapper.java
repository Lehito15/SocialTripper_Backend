package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.AccountDTO;
import com.socialtripper.restapi.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toEntity(AccountDTO accountDTO) {
        if (accountDTO == null) return null;
        return new Account(
                accountDTO.uuid(),
                accountDTO.nickname(),
                accountDTO.email(),
                accountDTO.isPublic(),
                accountDTO.salt(),
                accountDTO.phone(),
                accountDTO.role(),
                accountDTO.isExpired(),
                accountDTO.isLocked(),
                accountDTO.createdAt(),
                accountDTO.homePageUrl(),
                accountDTO.description(),
                accountDTO.followersNumber(),
                accountDTO.followingNumber(),
                accountDTO.numberOfTrips(),
                accountDTO.profilePicture()
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
                account.getProfilePicture()
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
        account.setPublic(accountDTO.isPublic());
        account.setExpired(accountDTO.isExpired());
        account.setLocked(accountDTO.isLocked());
        if (accountDTO.createdAt() != null) account.setCreatedAt(accountDTO.createdAt());
        if (accountDTO.homePageUrl() != null) account.setHomePageUrl(accountDTO.homePageUrl());
        account.setFollowersNumber(accountDTO.followersNumber());
        account.setFollowingNumber(accountDTO.followingNumber());
        account.setNumberOfTrips(accountDTO.numberOfTrips());
        return account;
    }
}
