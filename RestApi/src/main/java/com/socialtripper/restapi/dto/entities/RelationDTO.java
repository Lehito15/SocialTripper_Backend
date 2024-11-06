package com.socialtripper.restapi.dto.entities;

import java.util.Set;
import java.util.UUID;

public record RelationDTO (UUID uuid, int reactionsNumber, int commentsNumber,
                           Set<RelationMultimediaDTO> multimediaDTOs) {
}
