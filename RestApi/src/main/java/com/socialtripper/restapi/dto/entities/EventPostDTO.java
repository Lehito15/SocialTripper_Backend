package com.socialtripper.restapi.dto.entities;


import com.socialtripper.restapi.dto.thumbnails.EventThumbnailDTO;


/**
 * Data transfer object dla encji postu w wydarzeniu.
 *
 * @param post post - {@link PostDTO}
 * @param event wydarzenie - {@link EventThumbnailDTO}
 */
public record EventPostDTO(PostDTO post, EventThumbnailDTO event) {
}
