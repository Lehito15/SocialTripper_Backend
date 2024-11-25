package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.*;
import com.socialtripper.restapi.dto.messages.*;
import com.socialtripper.restapi.dto.requests.UserRequestGroupDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.dto.thumbnails.GroupThumbnailDTO;
import com.socialtripper.restapi.entities.Group;
import com.socialtripper.restapi.entities.GroupActivity;
import com.socialtripper.restapi.entities.GroupLanguage;
import com.socialtripper.restapi.nodes.GroupNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    List<GroupDTO> findAllGroups();
    GroupDTO findGroupByUUID(UUID uuid);
    Long getGroupIdByUUID(UUID uuid);
    Group getGroupReference(UUID uuid);
    Group getNewGroupWithReferences(GroupDTO groupDTO);
    GroupActivity setActivity(UUID uuid, ActivityDTO activityDTO);
    GroupLanguage setLanguage(UUID uuid, LanguageDTO languageDTO);
    GroupDTO createGroup(GroupDTO groupDTO, MultipartFile icon);
    List<GroupThumbnailDTO> findUserCurrentGroups(UUID uuid);
    UserJoinsGroupMessageDTO addUserToGroup(UUID userUUID, UUID groupUUID);
    UserLeavesGroupMessageDTO removeUserFromGroup(UUID userUUID, UUID groupUUID);
    GroupDTO updateGroup(UUID uuid, GroupDTO groupDTO);
    GroupNode findGroupNodeByUUID(UUID uuid);
    UserRequestGroupDTO addUserRequestGroup(UUID userUUID, UUID groupUUID);
    List<AccountThumbnailDTO> getGroupMembers(UUID groupUUID);
    Boolean isGroupRequestSent(UUID userUUID, UUID groupUUID);
    Boolean isUserInGroup(UUID userUUID, UUID groupUUID);
}
