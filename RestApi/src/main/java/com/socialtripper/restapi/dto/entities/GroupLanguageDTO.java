package com.socialtripper.restapi.dto.entities;

/**
 * Data transfer object dla encji języka w grupie.
 *
 * @param group grupa - {@link GroupDTO}
 * @param language język - {@link LanguageDTO}
 */
public record GroupLanguageDTO(GroupDTO group, LanguageDTO language) {
}
