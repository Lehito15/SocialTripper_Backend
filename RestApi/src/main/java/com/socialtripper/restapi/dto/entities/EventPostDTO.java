package com.socialtripper.restapi.dto.entities;


import com.socialtripper.restapi.dto.thumbnails.EventThumbnailDTO;


public record EventPostDTO(PostDTO post, EventThumbnailDTO event) {
}
