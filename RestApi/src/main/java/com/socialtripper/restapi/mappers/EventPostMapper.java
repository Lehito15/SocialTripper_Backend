package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventPostDTO;
import com.socialtripper.restapi.entities.EventPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventPostMapper {
    private final PostMapper postMapper;

    @Autowired
    public EventPostMapper(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public EventPost toEntity(EventPostDTO eventPostDTO) {
        if (eventPostDTO == null) return null;
        return new EventPost(
                postMapper.toEntity(eventPostDTO.postDTO())
        );
    }

    public EventPostDTO toDTO(EventPost eventPost) {
        if (eventPost == null) return null;
        return new EventPostDTO(
                postMapper.toDTO(eventPost.getPost()),
                eventPost.getEvent().getUuid()
        );
    }
}
