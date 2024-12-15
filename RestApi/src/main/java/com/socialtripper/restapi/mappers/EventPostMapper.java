package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.EventPostDTO;
import com.socialtripper.restapi.entities.EventPost;
import com.socialtripper.restapi.nodes.EventNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją postu w wydarzeniu {@link EventPost}, węzłem postu {@link EventNode},
 * a data transfer object {@link EventPostDTO}.
 */
@Component
public class EventPostMapper {
    /**
     * Komponent odpowiedzialny za mapowanie postu.
     */
    private final PostMapper postMapper;
    /**
     * Komponent odpowiedzialny za mapowanie częściowej informacji o wydarzeniu.
     */
    private final EventThumbnailMapper eventThumbnailMapper;

    /**
     * Konstruktor wstrzykujący komponenty odpowiedzialne za mapowania.
     *
     * @param postMapper komponent odpowiedzialny za mapowanie postu
     * @param eventThumbnailMapper komponent odpowiedzialny za mapowanie częściowej informacji o wydarzeniu
     */
    @Autowired
    public EventPostMapper(PostMapper postMapper, EventThumbnailMapper eventThumbnailMapper) {
        this.postMapper = postMapper;
        this.eventThumbnailMapper = eventThumbnailMapper;
    }

    /**
     * Metoda mapująca data transfer object postu w wydarzeniu do encji.
     *
     * @param eventPostDTO data transfer object postu w wydarzeniu
     * @return encja postu w wydarzeniu
     */
    public EventPost toEntity(EventPostDTO eventPostDTO) {
        if (eventPostDTO == null) return null;
        return new EventPost(
                postMapper.toEntity(eventPostDTO.post())
        );
    }

    /**
     * Metoda mapująca encję wydarzenia oraz węzeł wydarznia na data transfer object.
     *
     * @param eventPost encja wydarzenia
     * @param eventNode węzeł wydarzenia
     * @return data transfer object wydarzenia
     */
    public EventPostDTO toDTO(EventPost eventPost, EventNode eventNode) {
        if (eventPost == null) return null;
        return new EventPostDTO(
                postMapper.toDTO(eventPost.getPost()),
                eventThumbnailMapper.toDTO(eventPost.getEvent(), eventNode)
        );
    }
}
