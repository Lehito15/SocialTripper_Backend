package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.*;
import com.socialtripper.restapi.dto.messages.*;
import com.socialtripper.restapi.dto.thumbnails.GroupThumbnailDTO;
import com.socialtripper.restapi.entities.Group;
import com.socialtripper.restapi.entities.GroupActivity;
import com.socialtripper.restapi.entities.GroupLanguage;

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
    GroupDTO createGroup(GroupDTO groupDTO);
    List<GroupThumbnailDTO> findUserCurrentGroups(UUID uuid);
    UserJoinsGroupMessageDTO addUserToGroup(UUID groupUUID, UUID userUUID);
    UserLeavesGroupMessageDTO removeUserFromGroup(UUID groupUUID, UUID userUUID);
    GroupDTO updateGroup(UUID uuid, GroupDTO groupDTO);
}
