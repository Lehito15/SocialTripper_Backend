package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.CommentDTO;
import com.socialtripper.restapi.dto.messages.UserReactionToCommentMessageDTO;
import com.socialtripper.restapi.nodes.CommentNode;
import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentNode findCommentNodeByUUID(UUID uuid);
    CommentDTO findCommentByUUID(UUID uuid);
    CommentDTO createPostComment(UUID userUUID, UUID postUUID, CommentDTO commentDTO);
    UserReactionToCommentMessageDTO addUserReactionToComment(UUID userUUID, UUID commentUUID);
    UserReactionToCommentMessageDTO removeUserReactionToComment(UUID userUUID, UUID commentUUID);
    List<CommentDTO> findPostComments(UUID postUUID);
}
