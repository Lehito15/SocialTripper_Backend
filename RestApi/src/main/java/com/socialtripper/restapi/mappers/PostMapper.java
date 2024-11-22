package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.PostDTO;
import com.socialtripper.restapi.entities.Post;
import com.socialtripper.restapi.nodes.PostNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PostMapper {
    private AccountThumbnailMapper accountThumbnailMapper;

    @Autowired
    public void setAccountThumbnailMapper(AccountThumbnailMapper accountThumbnailMapper) {
        this.accountThumbnailMapper = accountThumbnailMapper;
    }

    public Post toEntity(PostDTO postDTO) {
        if (postDTO == null) return null;
        return new Post(
                        postDTO.uuid(),
                        postDTO.content());
    }

    public PostDTO toDTO(Post post, Set<String> multimediaUrls) {
        if (post == null) return null;
        return new PostDTO(
                post.getUuid(),
                post.getContent(),
                post.getDateOfPost(),
                post.isExpired(),
                post.isLocked(),
                post.getCommentsNumber(),
                post.getReactionsNumber(),
                accountThumbnailMapper.toDTO(post.getAccount()),
                multimediaUrls
        );
    }

    public PostDTO toDTO(Post post) {
        if (post == null) return null;
        return new PostDTO(
                post.getUuid(),
                post.getContent(),
                post.getDateOfPost(),
                post.isExpired(),
                post.isLocked(),
                post.getCommentsNumber(),
                post.getReactionsNumber(),
                accountThumbnailMapper.toDTO(post.getAccount()),
                null
        );
    }

    public PostDTO toDTO(Post post, PostNode postNode) {
        if (postNode == null) return null;
        return new PostDTO(
                postNode.getUuid(),
                postNode.getContent(),
                postNode.getPostTime(),
                post.isExpired(),
                post.isLocked(),
                postNode.getCommentsNumber(),
                postNode.getReactionsNumber(),
                accountThumbnailMapper.toDTO(post.getAccount()),
                postNode.getMultimediaUrls()
        );
    }

    public PostNode toNode(Post post) {
        if (post == null) return null;
        return new PostNode(
                post.getUuid(),
                post.getContent(),
                post.getDateOfPost()
        );
    }

    public Post copyNonNullValues(Post post, PostDTO postDTO) {
        if (postDTO == null) return null;
        if (postDTO.uuid() != null) post.setUuid(postDTO.uuid());
        if (postDTO.content() != null) post.setContent(postDTO.content());
        if (postDTO.isExpired() != null)  post.setExpired(postDTO.isExpired());
        if (postDTO.isLocked() != null)  post.setLocked(postDTO.isLocked());
        if (postDTO.commentsNumber() != null) post.setCommentsNumber(postDTO.commentsNumber());
        if (postDTO.reactionsNumber() != null) post.setReactionsNumber(postDTO.reactionsNumber());
        return post;
    }
}
