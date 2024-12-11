package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.GroupDTO;
import com.socialtripper.restapi.entities.Group;
import com.socialtripper.restapi.nodes.GroupNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją grupy {@link Group},
 * węzłem grupy {@link GroupNode},
 * a data transfer object {@link GroupDTO}.
 */
@Component
public class GroupMapper {
    /**
     * Komponent odpowiedzialny za mapowanie częściowych informacji o koncie użytkownika.
     */
    private AccountThumbnailMapper accountThumbnailMapper;
    /**
     * Komponent odpowiedzialny za mapowanie aktywności.
     */
    private ActivityMapper activityMapper;
    /**
     * Komponent odpowiedzialny za mapowanie języka.
     */
    private LanguageMapper languageMapper;
    /**
     * Komponent odpowiedzialny za mapowanie zasięg grupy.
     */
    private LocationScopeMapper locationScopeMapper;

    /**
     * Setter wstrzykujący komponent mapujący aktywności.
     * @param activityMapper komponent odpowiedzialny za mapowanie aktywności
     */
    @Autowired
    public void setActivityMapper(ActivityMapper activityMapper) {
        this.activityMapper = activityMapper;
    }

    /**
     * Setter wstrzykujący komponent mapujący języka.
     * @param languageMapper komponent odpowiedzialny za mapowanie języka
     */
    @Autowired
    public void setLanguageMapper(LanguageMapper languageMapper) {
        this.languageMapper = languageMapper;
    }

    /**
     * Setter wstrzykujący komponent mapujący częściowe informacje o koncie.
     * @param accountThumbnailMapper komponent odpowiedzialny za mapowanie częściowej informacji o koncie
     */
    @Autowired
    public void setAccountThumbnailMapper(AccountThumbnailMapper accountThumbnailMapper) {
        this.accountThumbnailMapper = accountThumbnailMapper;
    }

    /**
     * Setter wstrzykujący komponent mapujący zasięg grupy.
     * @param locationScopeMapper komponent odpowiedzialny za mapowanie zasięgu grupy
     */
    @Autowired
    public void setLocationScopeMapper(LocationScopeMapper locationScopeMapper) {
        this.locationScopeMapper = locationScopeMapper;
    }

    /**
     * Metoda mapująca data transfer object grupy do encji nowej grupy.
     *
     * @param groupDTO data transfer object grupy
     * @return encja grupy
     */
    public Group toNewEntity(GroupDTO groupDTO) {
        if (groupDTO == null) return null;
        return new Group(
                groupDTO.uuid(),
                groupDTO.name(),
                1,
                groupDTO.isPublic() != null,
                groupDTO.description(),
                groupDTO.rules(),
                groupDTO.dateOfCreation(),
                groupDTO.homePageUrl(),
                groupDTO.locationLongitude(),
                groupDTO.locationLatitude()
        );
    }

    /**
     * Metoda mapująca encję grupy do data transfer object.
     *
     * @param group encja grupy
     * @return data transfer object grupy
     */
    public GroupDTO toDTO(Group group) {
        if (group == null) return null;
        return new GroupDTO(
                group.getUuid(),
                group.getName(),
                group.getNumberOfMembers(),
                group.getIsPublic(),
                group.getDescription(),
                group.getRules(),
                group.getDateOfCreation(),
                group.getHomePageUrl(),
                group.getLocationLongitude(),
                group.getLocationLatitude(),
                locationScopeMapper.toDTO(group.getLocationScope()),
                accountThumbnailMapper.toDTO(group.getOwner()),
                group.getIconUrl(),
                group.getGroupActivities().stream().map(
                        groupActivity -> activityMapper.toDTO(groupActivity.getActivity())
                ).collect(Collectors.toSet()),
                group.getGroupLanguages().stream().map(
                        groupLanguage -> languageMapper.toDTO(groupLanguage.getLanguage())
                ).collect(Collectors.toSet())
        );
    }

    /**
     * Metoda mapująca encję grupy na węzeł grupy.
     *
     * @param group encja grupy
     * @return węzeł grupy
     */
    public GroupNode toNode(Group group) {
        if (group == null) return null;
        return new GroupNode(
                group.getUuid(),
                group.getName()
        );
    }

    /**
     * Metoda kopiująca wartości pól niezwiązanych z innymi encjami z data transfer object grupu
     * do obiektu encji.
     *
     * @param group obiekt encji, do którego mają zostać przekopiowane wartości pól
     * @param groupDTO dat transfer object, z którego wartości mają zostać przekopiowane
     * @return encja grupy
     */
    public Group copyNonNullValues(Group group, GroupDTO groupDTO) {
        if (groupDTO == null) return null;
        if (groupDTO.uuid() != null) group.setUuid(groupDTO.uuid());
        if (groupDTO.name() != null) group.setName(groupDTO.name());
        if (groupDTO.numberOfMembers() != null) group.setNumberOfMembers(groupDTO.numberOfMembers());
        if (groupDTO.isPublic() != null) group.setIsPublic(groupDTO.isPublic());
        if (groupDTO.description() != null) group.setDescription(groupDTO.description());
        if (groupDTO.rules() != null) group.setRules(groupDTO.rules());
        if (groupDTO.dateOfCreation() != null) group.setDateOfCreation(groupDTO.dateOfCreation());
        if (groupDTO.homePageUrl() != null) group.setHomePageUrl(groupDTO.homePageUrl());
        if (groupDTO.locationLongitude() != null) group.setLocationLongitude(groupDTO.locationLongitude());
        if (groupDTO.locationLatitude() != null) group.setLocationLatitude(groupDTO.locationLatitude());
        return group;
    }

}
