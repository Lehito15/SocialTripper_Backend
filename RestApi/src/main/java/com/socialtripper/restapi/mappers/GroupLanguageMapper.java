package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.GroupLanguageDTO;
import com.socialtripper.restapi.entities.GroupLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupLanguageMapper {
    private final LanguageMapper languageMapper;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupLanguageMapper(LanguageMapper languageMapper, GroupMapper groupMapper) {
        this.languageMapper = languageMapper;
        this.groupMapper = groupMapper;
    }

    public GroupLanguage toEntity(GroupLanguageDTO groupLanguageDTO) {
        if (groupLanguageDTO == null) return null;
        return new GroupLanguage(
                groupMapper.toEntity(groupLanguageDTO.group()),
                languageMapper.toEntity(groupLanguageDTO.language())
        );
    }

    public GroupLanguageDTO toDTO(GroupLanguage groupLanguage) {
        if (groupLanguage == null) return null;
        return new GroupLanguageDTO(
                groupMapper.toDTO(groupLanguage.getGroup()),
                languageMapper.toDTO(groupLanguage.getLanguage())
        );
    }
}
