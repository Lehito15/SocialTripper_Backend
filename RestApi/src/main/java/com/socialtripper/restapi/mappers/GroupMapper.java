package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.GroupDTO;
import com.socialtripper.restapi.entities.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {
    private final MultimediaMapper multimediaMapper;
    private final AccountMapper accountMapper;

    @Autowired
    public GroupMapper(MultimediaMapper multimediaMapper, AccountMapper accountMapper) {
        this.multimediaMapper = multimediaMapper;
        this.accountMapper = accountMapper;
    }

    public Group toEntity(GroupDTO groupDTO) {
        if (groupDTO == null) return null;
        return new Group(
                groupDTO.uuid(),
                groupDTO.name(),
                groupDTO.numberOfMembers(),
                groupDTO.isPublic(),
                groupDTO.description(),
                groupDTO.rules(),
                groupDTO.dateOfCreation(),
                groupDTO.homePageUrl(),
                groupDTO.locationLongitude(),
                groupDTO.locationLatitude(),
                groupDTO.locationScope(),
                accountMapper.toEntity(groupDTO.ownerDTO()),
                multimediaMapper.toEntity(groupDTO.iconDTO())
        );
    }

    public GroupDTO toDTO(Group group) {
        if (group == null) return null;
        return new GroupDTO(
                group.getUuid(),
                group.getName(),
                group.getNumberOfMembers(),
                group.getIsPublic(),
                group.getDescription(),
                group.getRules(),
                group.getDateOfCreation(),
                group.getHomePageUrl(),
                group.getLocationLongitude(),
                group.getLocationLatitude(),
                group.getLocationScope(),
                accountMapper.toDTO(group.getOwner()),
                multimediaMapper.toDTO(group.getIcon())
        );
    }
}
