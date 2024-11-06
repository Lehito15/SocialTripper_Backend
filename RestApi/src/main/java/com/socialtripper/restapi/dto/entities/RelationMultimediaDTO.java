package com.socialtripper.restapi.dto.entities;

import java.math.BigDecimal;

public record RelationMultimediaDTO (Long id, RelationDTO relationDTO, MultimediaDTO multimediaDTO,
                                     BigDecimal locationLongitude, BigDecimal locationLatitude) {
}
