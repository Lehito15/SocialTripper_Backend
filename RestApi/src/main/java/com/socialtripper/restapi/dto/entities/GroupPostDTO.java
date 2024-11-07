package com.socialtripper.restapi.dto.entities;


import java.util.UUID;

public record GroupPostDTO (PostDTO postDTO, UUID groupUUID) {
}
