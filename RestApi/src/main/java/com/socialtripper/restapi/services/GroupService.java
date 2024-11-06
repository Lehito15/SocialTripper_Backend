package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.GroupDTO;
import com.socialtripper.restapi.dto.messages.GroupStatusChangeMessageDTO;
import com.socialtripper.restapi.dto.messages.UserJoinsGroupMessageDTO;
import com.socialtripper.restapi.dto.messages.UserLeavesGroupMessageDTO;
import com.socialtripper.restapi.entities.Group;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    List<GroupDTO> findAllGroups();
    GroupDTO findGroupByUUID(UUID uuid);

    Long getGroupIdByUUID(UUID uuid);

    Group getGroupReference(UUID uuid);

    List<GroupDTO> findGroupsByUserUUID(UUID uuid);
    UserJoinsGroupMessageDTO addUserToGroup(UUID groupUUID, UUID userUUID);
    UserLeavesGroupMessageDTO removeUserFromGroup(UUID groupUUID, UUID userUUID);
    GroupStatusChangeMessageDTO changeGroupStatusByUUID(UUID uuid);
}
