package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.GroupActivityDTO;
import com.socialtripper.restapi.entities.GroupActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupActivityMapper {
    private final ActivityMapper activityMapper;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupActivityMapper(ActivityMapper activityMapper, GroupMapper groupMapper) {
        this.activityMapper = activityMapper;
        this.groupMapper = groupMapper;
    }

    public GroupActivity toEntity(GroupActivityDTO groupActivityDTO) {
        if (groupActivityDTO == null) return null;
        return new GroupActivity(
                groupMapper.toEntity(groupActivityDTO.group()),
                activityMapper.toEntity(groupActivityDTO.activity())
        );
    }

    public GroupActivityDTO toDTO(GroupActivity groupActivity) {
        if (groupActivity == null) return null;
        return new GroupActivityDTO(
                groupMapper.toDTO(groupActivity.getGroup()),
                activityMapper.toDTO(groupActivity.getActivity())
        );
    }
}
