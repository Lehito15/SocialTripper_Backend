package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventLanguageDTO;
import com.socialtripper.restapi.entities.EventLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventLanguageMapper {
    private final LanguageMapper languageMapper;
    private final EventMapper eventMapper;

    @Autowired
    public EventLanguageMapper(LanguageMapper languageMapper, EventMapper eventMapper) {
        this.languageMapper = languageMapper;
        this.eventMapper = eventMapper;
    }

    public EventLanguage toEntity(EventLanguageDTO eventLanguageDTO) {
        if (eventLanguageDTO == null) return null;
        return new EventLanguage(
                eventLanguageDTO.id(),
                eventLanguageDTO.requiredLevel(),
                eventMapper.toEntity(eventLanguageDTO.eventDTO()),
                languageMapper.toEntity(eventLanguageDTO.languageDTO())
        );
    }

    public EventLanguageDTO toDTO(EventLanguage eventLanguage) {
        if (eventLanguage == null) return null;
        return new EventLanguageDTO(
                eventLanguage.getId(),
                eventLanguage.getRequiredLevel(),
                eventMapper.toDTO(eventLanguage.getEvent()),
                languageMapper.toDTO(eventLanguage.getLanguage())
        );
    }
}
