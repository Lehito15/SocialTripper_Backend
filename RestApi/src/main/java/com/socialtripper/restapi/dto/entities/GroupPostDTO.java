package com.socialtripper.restapi.dto.entities;


import com.socialtripper.restapi.dto.thumbnails.GroupThumbnailDTO;

public record GroupPostDTO (PostDTO post, GroupThumbnailDTO group) {
}
