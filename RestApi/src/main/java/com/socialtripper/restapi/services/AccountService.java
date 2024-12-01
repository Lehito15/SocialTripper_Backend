package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.AccountDTO;
import com.socialtripper.restapi.dto.entities.FollowDTO;
import com.socialtripper.restapi.dto.messages.UserEndsFollowingMessageDTO;
import com.socialtripper.restapi.dto.messages.UserStartsFollowingMessageDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.dto.thumbnails.MultimediaDTO;
import com.socialtripper.restapi.entities.Account;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    Account getAccountReference(UUID uuid);
    Account getNewAccountWithReferences(AccountDTO accountDTO);
    Long getAccountIdByUUID(UUID uuid);
    AccountThumbnailDTO findAccountThumbnailByUUID(UUID uuid);
    AccountDTO findAccountByUUID(UUID uuid);
    AccountThumbnailDTO updateAccount(UUID uuid, AccountDTO accountDTO);
    AccountThumbnailDTO createAccount(AccountDTO accountDTO, MultipartFile profilePicture);
    UserStartsFollowingMessageDTO followUser(FollowDTO followDTO);
    UserEndsFollowingMessageDTO unfollowUser(FollowDTO followDTO);
    List<AccountThumbnailDTO> getFollowedAccounts(UUID uuid);
    List<AccountThumbnailDTO> getFollowingAccounts(UUID uuid);
    MultimediaDTO updateProfilePicture(UUID uuid, MultipartFile profilePicture);
    AccountDTO findAccountByEmail(String email);
    List<AccountThumbnailDTO> findAccountsByNicknameSubstring(String nickname);
}
