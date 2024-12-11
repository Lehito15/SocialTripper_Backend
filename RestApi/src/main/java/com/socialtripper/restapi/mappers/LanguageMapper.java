package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.LanguageDTO;
import com.socialtripper.restapi.entities.Language;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją {@link Language},
 * a data transfer object {@link LanguageDTO}.
 */
@Component
public class LanguageMapper {

    /**
     * Metoda mapująca data transfer object języka do encji.
     *
     * @param languageDTO data transfer object języka
     * @return encja języka
     */
    public Language toEntity(LanguageDTO languageDTO) {
        if (languageDTO == null) return null;
        return new Language(
                languageDTO.id(),
                languageDTO.name()
        );
    }

    /**
     * Metoda mapująca encję języka do data transfer object.
     *
     * @param language encja języka
     * @return data transfer object języka
     */
    public LanguageDTO toDTO(Language language) {
        if (language == null) return null;
        return new LanguageDTO(
                language.getId(),
                language.getName()
        );
    }
}
