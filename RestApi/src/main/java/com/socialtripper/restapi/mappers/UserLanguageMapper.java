package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.UserLanguageDTO;
import com.socialtripper.restapi.entities.UserLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją języka użytkownika {@link UserLanguage},
 * a data transfer object {@link UserLanguageDTO}.
 */
@Component
public class UserLanguageMapper {
    /**
     * Komponent odpowiedzialny za mapowanie języka.
     */
    private final LanguageMapper languageMapper;

    /**
     * Konstruktor wstrzykujacy komponent mapujący.
     *
     * @param languageMapper komponent odpowiedzialny za mapowanie języka
     */
    @Autowired
    public UserLanguageMapper(LanguageMapper languageMapper) {
        this.languageMapper = languageMapper;
    }

    /**
     * Metoda mapująca data transfer object języka użytkownika do encji.
     *
     * @param userLanguageDTO data transfer object języka użytkownika
     * @return encja języka użytkownika
     */
    public UserLanguage toEntity(UserLanguageDTO userLanguageDTO) {
        if (userLanguageDTO == null) return null;
        return new UserLanguage(
                userLanguageDTO.level(),
                languageMapper.toEntity(userLanguageDTO.language())
        );
    }

    /**
     * Metoda mapująca encję języka użytkownika do data transfer object
     * @param userLanguage encja języka użytkownika
     * @return data transfer object języka użytkownika
     */
    public UserLanguageDTO toDTO(UserLanguage userLanguage) {
        if (userLanguage == null) return null;
        return new UserLanguageDTO(
                userLanguage.getLevel(),
                languageMapper.toDTO(userLanguage.getLanguage())
        );
    }

}
