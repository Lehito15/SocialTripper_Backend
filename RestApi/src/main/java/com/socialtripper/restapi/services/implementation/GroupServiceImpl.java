package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.dto.entities.GroupDTO;
import com.socialtripper.restapi.dto.messages.GroupStatusChangeMessageDTO;
import com.socialtripper.restapi.dto.messages.UserJoinsGroupMessageDTO;
import com.socialtripper.restapi.dto.messages.UserLeavesGroupMessageDTO;
import com.socialtripper.restapi.entities.Group;
import com.socialtripper.restapi.exceptions.GroupNotFoundException;
import com.socialtripper.restapi.exceptions.UserNotFoundException;
import com.socialtripper.restapi.mappers.GroupMapper;
import com.socialtripper.restapi.repositories.GroupRepository;
import com.socialtripper.restapi.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @Override
    public List<GroupDTO> findAllGroups() {
        return groupRepository.findAll()
                                .stream()
                                .map(groupMapper::toDTO)
                                .toList();
    }

    @Override
    public GroupDTO findGroupByUUID(UUID uuid) {
        return groupMapper.toDTO(
                groupRepository.findByUuid(uuid)
                        .orElseThrow(() -> new GroupNotFoundException(uuid)));
    }

    @Override
    public Long getGroupIdByUUID(UUID uuid) {
        return groupRepository.findIdByUUID(uuid).orElseThrow(() -> new UserNotFoundException(uuid));
    }

    @Override
    public Group getGroupReference(UUID uuid) {
        return groupRepository.getReferenceById(getGroupIdByUUID(uuid));
    }

    @Override
    public List<GroupDTO> findGroupsByUserUUID(UUID uuid) {
        return List.of();
    }

    @Override
    public UserJoinsGroupMessageDTO addUserToGroup(UUID groupUUID, UUID userUUID) {
        return null;
    }

    @Override
    public UserLeavesGroupMessageDTO removeUserFromGroup(UUID groupUUID, UUID userUUID) {
        return null;
    }

    @Override
    public GroupStatusChangeMessageDTO changeGroupStatusByUUID(UUID uuid) {
        return null;
    }
}
