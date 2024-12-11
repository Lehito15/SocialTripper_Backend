package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.dto.entities.CommentDTO;
import com.socialtripper.restapi.dto.entities.PostDTO;
import com.socialtripper.restapi.dto.messages.UserReactionToCommentMessageDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.exceptions.CommentNotFoundException;
import com.socialtripper.restapi.mappers.CommentMapper;
import com.socialtripper.restapi.nodes.CommentNode;
import com.socialtripper.restapi.repositories.graph.CommentNodeRepository;
import com.socialtripper.restapi.services.AccountService;
import com.socialtripper.restapi.services.CommentService;
import com.socialtripper.restapi.services.PostService;
import com.socialtripper.restapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementacja serwisu zarządzającego operacjami wykonywanymi na węzłach komentarzy.
 */
@Service
public class CommentServiceImpl implements CommentService {
    /**
     * Repozytorium zarządzające węzłami komentarzy.
     */
    private final CommentNodeRepository commentNodeRepository;
    /**
     * Serwis zarządzający operacjami wykonywanymi na kontach użytkowników.
     */
    private final AccountService accountService;
    /**
     * Serwis zarządzający operacjami wykonywanymi na użytkownikach.
     */
    private final UserService userService;
    /**
     * Serwis zarządzający postami.
     */
    private final PostService postService;
    /**
     * Komponent mapujący komentarze.
     */
    private final CommentMapper commentMapper;

    /**
     * Konstruktor wstrzykujący komponenty.
     *
     * @param commentNodeRepository repozytorium zarządzające węzłami komentarzy
     * @param userService serwis zarządzający operacjami wykonywanymi na użytkownikach
     * @param postService serwis zarządzający postami
     * @param accountService serwis zarządzający operacjami wykonywanymi na kontach użytkowników
     * @param commentMapper komponent mapujący komentarze
     */
    @Autowired
    public CommentServiceImpl(CommentNodeRepository commentNodeRepository,
                              UserService userService,
                              PostService postService,
                              AccountService accountService,
                              CommentMapper commentMapper) {
        this.commentNodeRepository = commentNodeRepository;
        this.userService = userService;
        this.postService = postService;
        this.commentMapper = commentMapper;
        this.accountService = accountService;
    }

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy komentarz nie istnieje w bazie rzucany jest wyjątek {@link CommentNotFoundException}.
     * </p>
     */
    @Override
    public CommentNode findCommentNodeByUUID(UUID uuid) {
        return commentNodeRepository.findCommentNodeByUuid(uuid).orElseThrow(
                () -> new CommentNotFoundException(uuid)
        );
    }

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy komentarz nie istnieje w bazie rzucany jest wyjątek {@link CommentNotFoundException}.
     * </p>
     */
    @Override
    public CommentDTO findCommentByUUID(UUID uuid) {
        CommentNode comment = findCommentNodeByUUID(uuid);
        PostDTO commentedPost = postService.findPostByUUID(
                comment.getCommentedPost().getUuid()
        );
        AccountThumbnailDTO account = accountService.findAccountThumbnailByUUID(
                comment.getCommentAuthor().getUuid()
        );

        return commentMapper.toDTO(findCommentNodeByUUID(uuid),
                commentedPost, account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommentDTO createPostComment(UUID userUUID, UUID postUUID, CommentDTO commentDTO) {
        CommentNode comment = commentNodeRepository.save(
                new CommentNode(
                        commentDTO.content(),
                        LocalDateTime.now(),
                        userService.findUserNodeByUUID(userUUID),
                        postService.findPostNodeByUUID(postUUID)
                )
        );
        postService.addCommentToPost(postUUID);
        return findCommentByUUID(comment.getUuid());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserReactionToCommentMessageDTO addUserReactionToComment(UUID userUUID, UUID commentUUID) {
        commentNodeRepository.addCommentReaction(
                userUUID.toString(),
                commentUUID.toString()
        );
        return new UserReactionToCommentMessageDTO(
                userUUID,
                commentUUID,
                "user reacted to comment"
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserReactionToCommentMessageDTO removeUserReactionToComment(UUID userUUID, UUID commentUUID) {
        commentNodeRepository.removeCommentReaction(
                userUUID.toString(),
                commentUUID.toString());
        return new UserReactionToCommentMessageDTO(
                userUUID,
                commentUUID,
                "user removed reaction to comment"
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CommentDTO> findPostComments(UUID postUUID) {
        List<CommentDTO> comments = new ArrayList<>();
        commentNodeRepository.findPostComments(
                postUUID.toString()).forEach(
                    comment -> comments.add(findCommentByUUID(comment.getUuid()))
        );
        return comments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean didUserReactToComment(UUID userUUID, UUID commentUUID) {
        return commentNodeRepository.didUserReact(
                userUUID.toString(),
                commentUUID.toString()
        );
    }
}
