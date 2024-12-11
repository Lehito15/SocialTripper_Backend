package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.thumbnails.GroupThumbnailDTO;
import com.socialtripper.restapi.entities.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją grupy {@link Group},
 * a data transfer object częściowej informacji o grupie {@link GroupThumbnailDTO}.
 */
@Component
public class GroupThumbnailMapper {
    /**
     * Metoda mapująca encję grupy na data transfer object częściowej informacji o grupie.
     *
     * @param group encja grupy
     * @return data transfer object częściowej informacji o grupie
     */
    public GroupThumbnailDTO toDTO(Group group) {
        if (group == null) return null;
        return new GroupThumbnailDTO(
                group.getUuid(),
                group.getName(),
                group.getNumberOfMembers(),
                group.getDescription(),
                group.getHomePageUrl(),
                group.getIconUrl()
        );
    }
}
