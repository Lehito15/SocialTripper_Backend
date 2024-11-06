package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.GroupPostDTO;
import com.socialtripper.restapi.entities.GroupPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupPostMapper {
    private final PostMapper postMapper;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupPostMapper(PostMapper postMapper, GroupMapper groupMapper) {
        this.postMapper = postMapper;
        this.groupMapper = groupMapper;
    }

    public GroupPost toEntity(GroupPostDTO groupPostDTO) {
        if (groupPostDTO == null) return null;
        return new GroupPost(
                postMapper.toEntity(groupPostDTO.postDTO())
        );
    }

    public GroupPostDTO toDTO(GroupPost groupPost) {
        if (groupPost == null) return null;
        return new GroupPostDTO(
                postMapper.toDTO(groupPost.getPost()),
                groupPost.getGroup().getUuid()
        );
    }
}
