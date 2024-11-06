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
                account.getHomePageUrl(),account.getHomePageUrl(),
                account.getFollowersNumber(),
                account.getFollowingNumber(),
                account.getNumberOfTrips(),
                account.getProfilePicture()
        );
    }
}
