package com.socialtripper.restapi.dto.entities;

import java.util.UUID;

public record MultimediaDTO(UUID uuid, String url, String mimeType,
                            String mimeSubtype, MultimediaCategoryDTO multimediaCategoryDTO) {
}
