package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.LanguageDTO;
import com.socialtripper.restapi.entities.Language;
import org.springframework.stereotype.Component;

@Component
public class LanguageMapper {

    public Language toEntity(LanguageDTO languageDTO) {
        if (languageDTO == null) return null;
        return new Language(
                languageDTO.id(),
                languageDTO.name()
        );
    }

    public LanguageDTO toDTO(Language language) {
        if (language == null) return null;
        return new LanguageDTO(
                language.getId(),
                language.getName()
        );
    }
}
