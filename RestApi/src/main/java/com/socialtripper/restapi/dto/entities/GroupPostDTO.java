package com.socialtripper.restapi.dto.entities;


import com.socialtripper.restapi.dto.thumbnails.GroupThumbnailDTO;

/**
 * Data transfer object dla encji postu grupowego.
 *
 * @param post post - {@link PostDTO}
 * @param group grupa - {@link GroupThumbnailDTO}
 */
public record GroupPostDTO (PostDTO post, GroupThumbnailDTO group) {
}
