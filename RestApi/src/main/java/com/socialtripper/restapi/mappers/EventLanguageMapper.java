package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventLanguageDTO;
import com.socialtripper.restapi.entities.EventLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventLanguageMapper {
    private final LanguageMapper languageMapper;

    @Autowired
    public EventLanguageMapper(LanguageMapper languageMapper) {
        this.languageMapper = languageMapper;
    }

    public EventLanguage toEntity(EventLanguageDTO eventLanguageDTO) {
        if (eventLanguageDTO == null) return null;
        return new EventLanguage(
                languageMapper.toEntity(eventLanguageDTO.language()),
                eventLanguageDTO.requiredLevel()
        );
    }

    public EventLanguageDTO toDTO(EventLanguage eventLanguage) {
        if (eventLanguage == null) return null;
        return new EventLanguageDTO(
                eventLanguage.getRequiredLevel(),
                languageMapper.toDTO(eventLanguage.getLanguage())
        );
    }
}
