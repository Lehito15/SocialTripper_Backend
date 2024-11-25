package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.CommentDTO;
import com.socialtripper.restapi.dto.entities.PostDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.nodes.CommentNode;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentDTO toDTO(CommentNode comment,
                            PostDTO post,
                            AccountThumbnailDTO account) {
        if (comment == null) return null;
        return new CommentDTO(
                comment.getUuid(),
                comment.getContent(),
                comment.getCommentDate(),
                comment.getReactionsNumber(),
                post,
                account
        );
    }
}
