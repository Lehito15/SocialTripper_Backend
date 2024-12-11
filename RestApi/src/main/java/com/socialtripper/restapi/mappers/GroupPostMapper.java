package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.GroupPostDTO;
import com.socialtripper.restapi.entities.GroupPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie encji postu grupowego {@link GroupPost},
 * a data transfer object {@link GroupPostDTO}.
 */
@Component
public class GroupPostMapper {
    /**
     * Komponent odpowiedzialny za mapowanie postu.
     */
    private final PostMapper postMapper;
    /**
     * Komponent odpowiedzialny za mapowanie częściowej informacji o grupie.
     */
    private final GroupThumbnailMapper groupThumbnailMapper;

    /**
     * Konstruktor wstrzykujący komponenty mapujące.
     *
     * @param postMapper komponent odpowiedzialny za mapowanie postu
     * @param groupThumbnailMapper komponent odpowiedzialny za mapowanie częściowej informacji o grupie
     */
    @Autowired
    public GroupPostMapper(PostMapper postMapper, GroupThumbnailMapper groupThumbnailMapper) {
        this.postMapper = postMapper;
        this.groupThumbnailMapper = groupThumbnailMapper;
    }

    /**
     * Metoda mapująca data transfer object postu grupowego do data transfer object.
     *
     * @param groupPostDTO data transfer object postu grupowego
     * @return encja postu grupowego
     */
    public GroupPost toEntity(GroupPostDTO groupPostDTO) {
        if (groupPostDTO == null) return null;
        return new GroupPost(
                postMapper.toEntity(groupPostDTO.post())
        );
    }

    /**
     * Metoda mapująca encję postu grupowego do data transfer object.
     *
     * @param groupPost encja postu grupowego
     * @return data transfer object postu grupowego
     */
    public GroupPostDTO toDTO(GroupPost groupPost) {
        if (groupPost == null) return null;
        return new GroupPostDTO(
                postMapper.toDTO(groupPost.getPost()),
                groupThumbnailMapper.toDTO(groupPost.getGroup())
        );
    }
}
