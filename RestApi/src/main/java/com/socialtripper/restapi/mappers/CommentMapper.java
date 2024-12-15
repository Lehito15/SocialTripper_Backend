package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.CommentDTO;
import com.socialtripper.restapi.dto.entities.PostDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.nodes.CommentNode;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy {@link CommentNode} oraz {@link CommentDTO}.
 */
@Component
public class CommentMapper {
    /**
     * Metoda mapująca węzeł komentarza do data transfer object komentarza.
     * Ustawiana są data transfer object dla postu, którego dotyczy komentarz oraz jego autor.
     *
     * @param commentNode węzeł komentarza
     * @param post post, którego dotyczy komentarz
     * @param account konto autora komentarza
     * @return data transfer object komentarza
     */
    public CommentDTO toDTO(CommentNode commentNode,
                            PostDTO post,
                            AccountThumbnailDTO account) {
        if (commentNode == null) return null;
        return new CommentDTO(
                commentNode.getUuid(),
                commentNode.getContent(),
                commentNode.getCommentDate(),
                commentNode.getReactionsNumber(),
                post,
                account
        );
    }
}
