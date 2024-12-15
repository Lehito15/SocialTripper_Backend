package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.GroupLanguageDTO;
import com.socialtripper.restapi.entities.GroupLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją języka w grupie {@link GroupLanguage},
 * a data transfer object {@link GroupLanguageDTO}.
 */
@Component
public class GroupLanguageMapper {
    /**
     * Komponent odpowiedzialny za mapowanie języka.
     */
    private final LanguageMapper languageMapper;
    /**
     * Komponent odpowiedzialny za mapowanie grupy.
     */
    private final GroupMapper groupMapper;

    /**
     * Konstruktor wstrzykujący komponenty mapujące.
     *
     * @param languageMapper komponent odpowiedzialny za mapowanie języka
     * @param groupMapper komponent odpowiedzialny za mapowanie grupy
     */
    @Autowired
    public GroupLanguageMapper(LanguageMapper languageMapper, GroupMapper groupMapper) {
        this.languageMapper = languageMapper;
        this.groupMapper = groupMapper;
    }

    /**
     * Metoda mapująca data transfer object języka w grupie do encji.
     *
     * @param groupLanguageDTO data transfer object języka w grupie
     * @return encja języka w grupie
     */
    public GroupLanguage toEntity(GroupLanguageDTO groupLanguageDTO) {
        if (groupLanguageDTO == null) return null;
        return new GroupLanguage(
                groupMapper.toNewEntity(groupLanguageDTO.group()),
                languageMapper.toEntity(groupLanguageDTO.language())
        );
    }

    /**
     * Metoda mapująca encję języka w grupie do data transfer object.
     *
     * @param groupLanguage encja języka w grupie
     * @return data transfer object języka w grupie
     */
    public GroupLanguageDTO toDTO(GroupLanguage groupLanguage) {
        if (groupLanguage == null) return null;
        return new GroupLanguageDTO(
                groupMapper.toDTO(groupLanguage.getGroup()),
                languageMapper.toDTO(groupLanguage.getLanguage())
        );
    }
}
