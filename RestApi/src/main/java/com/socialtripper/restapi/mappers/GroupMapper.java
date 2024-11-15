package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.GroupDTO;
import com.socialtripper.restapi.entities.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class GroupMapper {
    private MultimediaMapper multimediaMapper;
    private AccountThumbnailMapper accountThumbnailMapper;
    private ActivityMapper activityMapper;
    private LanguageMapper languageMapper;

    @Autowired
    public void setActivityMapper(ActivityMapper activityMapper) {
        this.activityMapper = activityMapper;
    }

    @Autowired
    public void setLanguageMapper(LanguageMapper languageMapper) {
        this.languageMapper = languageMapper;
    }

    @Autowired
    public void setMultimediaMapper(MultimediaMapper multimediaMapper) {
        this.multimediaMapper = multimediaMapper;
    }

    @Autowired
    public void setAccountThumbnailMapper(AccountThumbnailMapper accountThumbnailMapper) {
        this.accountThumbnailMapper = accountThumbnailMapper;
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
                group.getLocationScope(),
                accountThumbnailMapper.toDTO(group.getOwner()),
                multimediaMapper.toDTO(group.getIcon()),
                group.getGroupActivities().stream().map(
                        groupActivity -> activityMapper.toDTO(groupActivity.getActivity())
                ).collect(Collectors.toSet()),
                group.getGroupLanguages().stream().map(
                        groupLanguage -> languageMapper.toDTO(groupLanguage.getLanguage())
                ).collect(Collectors.toSet())
        );
    }

    public Group copyNonNullValues(Group group, GroupDTO groupDTO) {
        if (groupDTO == null) return null;
        if (groupDTO.uuid() != null) group.setUuid(groupDTO.uuid());
        if (groupDTO.name() != null) group.setName(groupDTO.name());
        if (groupDTO.numberOfMembers() != null) group.setNumberOfMembers(groupDTO.numberOfMembers());
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
