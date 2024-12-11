package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.GroupActivityDTO;
import com.socialtripper.restapi.entities.GroupActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją aktywności w grupie {@link GroupActivity},
 * a data transfer object {@link GroupActivityDTO}.
 */
@Component
public class GroupActivityMapper {
    /**
     * Komponent odpowiedzialny za mapowanie aktywności.
     */
    private final ActivityMapper activityMapper;
    /**
     * Komponent odpowiedzialny za mapowanie grupy.
     */
    private final GroupMapper groupMapper;

    /**
     * Konstruktor wstrzykujący komponenty mapujące.
     *
     * @param activityMapper komponent odpowiedzialny za mapowanie aktwyności
     * @param groupMapper komponent odpowiedzialny za mapowanie grupy
     */
    @Autowired
    public GroupActivityMapper(ActivityMapper activityMapper, GroupMapper groupMapper) {
        this.activityMapper = activityMapper;
        this.groupMapper = groupMapper;
    }

    /**
     * Metoda mapująca data transfer object aktywności w grupie do encji.
     *
     * @param groupActivityDTO data transfer object aktywności w grupie
     * @return encja aktywności w grupie
     */
    public GroupActivity toEntity(GroupActivityDTO groupActivityDTO) {
        if (groupActivityDTO == null) return null;
        return new GroupActivity(
                groupMapper.toNewEntity(groupActivityDTO.group()),
                activityMapper.toEntity(groupActivityDTO.activity())
        );
    }

    /**
     * Metoda mapująca encję aktywności w grupie do data transfer object.
     *
     * @param groupActivity encja aktywności w grupie
     * @return data transfer object aktywności w grupie
     */
    public GroupActivityDTO toDTO(GroupActivity groupActivity) {
        if (groupActivity == null) return null;
        return new GroupActivityDTO(
                groupMapper.toDTO(groupActivity.getGroup()),
                activityMapper.toDTO(groupActivity.getActivity())
        );
    }
}
