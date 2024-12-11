package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventLanguageDTO;
import com.socialtripper.restapi.entities.EventLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją aktywności w wydarzeniu {@link EventLanguage},
 * a data transfer object {@link EventLanguageDTO}.
 */
@Component
public class EventLanguageMapper {
    /**
     * Komponent odpowiedzialny za mapowanie języka.
     */
    private final LanguageMapper languageMapper;

    /**
     * Konstruktor wstrzykujący komponent mapowania języka.
     *
     * @param languageMapper komponent mapujący język
     */
    @Autowired
    public EventLanguageMapper(LanguageMapper languageMapper) {
        this.languageMapper = languageMapper;
    }

    /**
     * Metoda mapująca data transfer object języka w wydarzeniu do encji.
     *
     * @param eventLanguageDTO data transfer object języka w wydarzeniu
     * @return encja języka w wydarzeniu
     */
    public EventLanguage toEntity(EventLanguageDTO eventLanguageDTO) {
        if (eventLanguageDTO == null) return null;
        return new EventLanguage(
                languageMapper.toEntity(eventLanguageDTO.language()),
                eventLanguageDTO.requiredLevel()
        );
    }

    /**
     * Metoda mapująca encję języka w wydarzeniu do data transfer object.
     *
     * @param eventLanguage encja języka w wydarzeniu
     * @return data transfer object języka w wydarzeniu
     */
    public EventLanguageDTO toDTO(EventLanguage eventLanguage) {
        if (eventLanguage == null) return null;
        return new EventLanguageDTO(
                eventLanguage.getRequiredLevel(),
                languageMapper.toDTO(eventLanguage.getLanguage())
        );
    }
}
