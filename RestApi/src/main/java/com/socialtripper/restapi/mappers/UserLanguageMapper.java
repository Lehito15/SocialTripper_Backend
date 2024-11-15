package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.UserLanguageDTO;
import com.socialtripper.restapi.entities.UserLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserLanguageMapper {
    private final LanguageMapper languageMapper;

    @Autowired
    public UserLanguageMapper(LanguageMapper languageMapper) {
        this.languageMapper = languageMapper;
    }

    public UserLanguage toEntity(UserLanguageDTO userLanguageDTO) {
        if (userLanguageDTO == null) return null;
        return new UserLanguage(
                userLanguageDTO.level(),
                languageMapper.toEntity(userLanguageDTO.language())
        );
    }

    public UserLanguageDTO toDTO(UserLanguage userLanguage) {
        if (userLanguage == null) return null;
        return new UserLanguageDTO(
                userLanguage.getLevel(),
                languageMapper.toDTO(userLanguage.getLanguage())
        );
    }

}
