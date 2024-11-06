package com.socialtripper.restapi.dto.entities;


import java.util.UUID;

public record EventPostDTO(PostDTO postDTO, UUID eventUUID) {
}
