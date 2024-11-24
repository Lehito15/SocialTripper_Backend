package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.GroupDTO;
import com.socialtripper.restapi.entities.Group;
import com.socialtripper.restapi.nodes.GroupNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class GroupMapper {
    private AccountThumbnailMapper accountThumbnailMapper;
    private ActivityMapper activityMapper;
    private LanguageMapper languageMapper;
    private LocationScopeMapper locationScopeMapper;

    @Autowired
    public void setActivityMapper(ActivityMapper activityMapper) {
        this.activityMapper = activityMapper;
    }

    @Autowired
    public void setLanguageMapper(LanguageMapper languageMapper) {
        this.languageMapper = languageMapper;
    }

    @Autowired
    public void setAccountThumbnailMapper(AccountThumbnailMapper accountThumbnailMapper) {
        this.accountThumbnailMapper = accountThumbnailMapper;
    }

    @Autowired
    public void setLocationScopeMapper(LocationScopeMapper locationScopeMapper) {
        this.locationScopeMapper = locationScopeMapper;
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
                groupDTO.locationLatitude()
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
                locationScopeMapper.toDTO(group.getLocationScope()),
                accountThumbnailMapper.toDTO(group.getOwner()),
                group.getIconUrl(),
                group.getGroupActivities().stream().map(
                        groupActivity -> activityMapper.toDTO(groupActivity.getActivity())
                ).collect(Collectors.toSet()),
                group.getGroupLanguages().stream().map(
                        groupLanguage -> languageMapper.toDTO(groupLanguage.getLanguage())
                ).collect(Collectors.toSet())
        );
    }

    public GroupNode toNode(Group group) {
        if (group == null) return null;
        return new GroupNode(
                group.getUuid(),
                group.getName(),
                group.getIsPublic(),
                group.getHomePageUrl(),
                group.getIconUrl(),
                group.getGroupLanguages().stream().map(
                        groupLanguage -> groupLanguage.getLanguage().getName()).collect(Collectors.toSet()),
                group.getGroupActivities().stream().map(
                        groupActivity -> groupActivity.getActivity().getName()).collect(Collectors.toSet())
        );
    }

    public Group copyNonNullValues(Group group, GroupDTO groupDTO) {
        if (groupDTO == null) return null;
        if (groupDTO.uuid() != null) group.setUuid(groupDTO.uuid());
        if (groupDTO.name() != null) group.setName(groupDTO.name());
        group.setNumberOfMembers(groupDTO.numberOfMembers());
        if (groupDTO.isPublic() != null) group.setIsPublic(groupDTO.isPublic());
        if (groupDTO.description() != null) group.setDescription(groupDTO.description());
        if (groupDTO.rules() != null) group.setRules(groupDTO.rules());
        if (groupDTO.dateOfCreation() != null) group.setDateOfCreation(groupDTO.dateOfCreation());
        if (groupDTO.homePageUrl() != null) group.setHomePageUrl(groupDTO.homePageUrl());
        if (groupDTO.locationLongitude() != null) group.setLocationLongitude(groupDTO.locationLongitude());
        if (groupDTO.locationLatitude() != null) group.setLocationLatitude(groupDTO.locationLatitude());
        return group;
    }

}
