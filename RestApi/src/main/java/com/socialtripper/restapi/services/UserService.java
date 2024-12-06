package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.FollowDTO;
import com.socialtripper.restapi.dto.entities.UserActivityDTO;
import com.socialtripper.restapi.dto.entities.UserDTO;
import com.socialtripper.restapi.dto.entities.UserLanguageDTO;
import com.socialtripper.restapi.dto.messages.UserEndsFollowingMessageDTO;
import com.socialtripper.restapi.dto.messages.UserStartsFollowingMessageDTO;
import com.socialtripper.restapi.dto.requests.UserRequestFollowDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.entities.User;
import com.socialtripper.restapi.entities.UserActivity;
import com.socialtripper.restapi.entities.UserLanguage;
import com.socialtripper.restapi.nodes.UserNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDTO findUserByUUID(UUID uuid);
    UserDTO createUser(UserDTO userDTO, MultipartFile profilePicture);
    UserDTO updateUser(UUID uuid, UserDTO userDTO);
    User getUserReference(UUID uuid);
    User getNewUserWithReferences(UserDTO userDTO, MultipartFile profilePicture);
    UserActivity addActivity(UUID uuid, UserActivityDTO userActivityDTO);
    UserLanguage addLanguage(UUID uuid, UserLanguageDTO userLanguageDto);
    UserActivityDTO setUserActivity(UUID uuid, UserActivityDTO userActivityDTO);
    UserLanguageDTO setUserLanguage(UUID uuid, UserLanguageDTO userLanguageDTO);
    UserNode findUserNodeByUUID(UUID uuid);
    UserRequestFollowDTO addFollowRequest(FollowDTO followDTO);
    UserRequestFollowDTO removeFollowRequest(FollowDTO followDTO);
    UserStartsFollowingMessageDTO followUser(FollowDTO followDTO);
    UserEndsFollowingMessageDTO unfollowUser(FollowDTO followDTO);
    List<AccountThumbnailDTO> getFollowedAccounts(UUID uuid);
    List<AccountThumbnailDTO> getFollowingAccounts(UUID uuid);
    Boolean isFollowingUser(UUID followerUUID, UUID followedUUID);
    List<AccountThumbnailDTO> getUserFollowRequests(UUID uuid);
    Boolean isFollowRequestSent(UUID followerUUID, UUID followedUUID);
    List<AccountThumbnailDTO> getRecommendedUsers(UUID uuid);
    void saveUserInGraphDB(User user);
}
