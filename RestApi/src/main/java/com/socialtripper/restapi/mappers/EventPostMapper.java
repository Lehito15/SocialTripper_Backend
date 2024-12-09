package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventPostDTO;
import com.socialtripper.restapi.entities.EventPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventPostMapper {
    private final PostMapper postMapper;
    private final EventThumbnailMapper eventThumbnailMapper;

    @Autowired
    public EventPostMapper(PostMapper postMapper, EventThumbnailMapper eventThumbnailMapper) {
        this.postMapper = postMapper;
        this.eventThumbnailMapper = eventThumbnailMapper;
    }

    public EventPost toEntity(EventPostDTO eventPostDTO) {
        if (eventPostDTO == null) return null;
        return new EventPost(
                postMapper.toEntity(eventPostDTO.post())
        );
    }

    public EventPostDTO toDTO(EventPost eventPost) {
        if (eventPost == null) return null;
        return new EventPostDTO(
                postMapper.toDTO(eventPost.getPost()),
                eventThumbnailMapper.toDTO(eventPost.getEvent())
        );
    }
}
