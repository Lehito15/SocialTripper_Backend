package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.MultimediaCategoryDTO;
import com.socialtripper.restapi.entities.MultimediaCategory;
import org.springframework.stereotype.Component;

@Component
public class MultimediaCategoryMapper {
    public MultimediaCategory toEntity(MultimediaCategoryDTO multimediaCategoryDTO) {
        if (multimediaCategoryDTO == null) return null;
        return new MultimediaCategory(
                multimediaCategoryDTO.id(),
                multimediaCategoryDTO.name()
        );
    }

    public MultimediaCategoryDTO toDTO(MultimediaCategory multimediaCategory) {
        if (multimediaCategory == null) return null;
        return new MultimediaCategoryDTO(
                multimediaCategory.getId(),
                multimediaCategory.getName()
        );
    }
}
