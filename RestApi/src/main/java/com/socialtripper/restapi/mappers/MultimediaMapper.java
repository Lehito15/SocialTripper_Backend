package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.MultimediaDTO;
import com.socialtripper.restapi.entities.Multimedia;
import org.springframework.stereotype.Component;

@Component
public class MultimediaMapper {
    public Multimedia toEntity(MultimediaDTO multimediaDTO) {
        if (multimediaDTO == null) return null;
        return new Multimedia(
                multimediaDTO.uuid(),
                multimediaDTO.url(),
                multimediaDTO.mimeType(),
                multimediaDTO.mimeSubtype());
    }

    public MultimediaDTO toDTO(Multimedia multimedia) {
        if (multimedia == null) return null;
        return new MultimediaDTO(
                multimedia.getUuid(),
                multimedia.getUrl(),
                multimedia.getMimeType(),
                multimedia.getMimeSubtype());
    }

}
