package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.thumbnails.GroupThumbnailDTO;
import com.socialtripper.restapi.entities.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupThumbnailMapper {
    private final MultimediaMapper multimediaMapper;

    @Autowired
    public GroupThumbnailMapper(MultimediaMapper multimediaMapper) {
        this.multimediaMapper = multimediaMapper;
    }

    public GroupThumbnailDTO toDTO(Group group) {
        if (group == null) return null;
        return new GroupThumbnailDTO(
                group.getUuid(),
                group.getName(),
                group.getNumberOfMembers(),
                group.getDescription(),
                group.getHomePageUrl(),
                multimediaMapper.toDTO(group.getIcon())
        );
    }
}
